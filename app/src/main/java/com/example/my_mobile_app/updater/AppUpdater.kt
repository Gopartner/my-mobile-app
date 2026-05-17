package com.example.my_mobile_app.updater

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.content.FileProvider
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

data class ReleaseInfo(
  val tagName: String,
  val apkUrl: String,
  val remoteVersionCode: Long,
)

class AppUpdater(private val context: Context) {

  companion object {
    private const val GITHUB_API = "https://api.github.com/repos/Gopartner/my-mobile-app/releases/latest"
    private const val TAG = "AppUpdater"
  }

  fun check(onResult: (ReleaseInfo?) -> Unit) {
    Thread {
      try {
        val result = fetchLatestRelease()
        Handler(Looper.getMainLooper()).post { onResult(result) }
      } catch (e: Exception) {
        Log.w(TAG, "Update check failed", e)
        Handler(Looper.getMainLooper()).post { onResult(null) }
      }
    }.start()
  }

  private fun fetchLatestRelease(): ReleaseInfo? {
    val conn = URL(GITHUB_API).openConnection() as HttpURLConnection
    conn.setRequestProperty("Accept", "application/vnd.github.v3+json")
    conn.connectTimeout = 10000
    conn.readTimeout = 10000

    val body = conn.inputStream.bufferedReader().readText()
    val json = JSONObject(body)
    val tagName = json.getString("tag_name")
    val remoteCode = tagName.filter { it.isDigit() }.toLongOrNull() ?: return null

    val assets = json.getJSONArray("assets")
    var apkUrl: String? = null
    for (i in 0 until assets.length()) {
      val asset = assets.getJSONObject(i)
      if (asset.getString("name").endsWith(".apk")) {
        apkUrl = asset.getString("browser_download_url")
        break
      }
    }

    if (apkUrl == null) return null

    val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
    val currentCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
      pInfo.longVersionCode
    } else {
      pInfo.versionCode.toLong()
    }
    return if (remoteCode > currentCode) ReleaseInfo(tagName, apkUrl, remoteCode) else null
  }

  fun showUpdateDialog(release: ReleaseInfo) {
    AlertDialog.Builder(context)
      .setTitle("Update Tersedia")
      .setMessage("Versi ${release.tagName} tersedia.\nDownload dan install sekarang?")
      .setPositiveButton("Update") { _, _ -> downloadAndInstall(release) }
      .setNegativeButton("Nanti") { _, _ -> }
      .show()
  }

  private fun downloadAndInstall(release: ReleaseInfo) {
    Toast.makeText(context, "Mendownload update…", Toast.LENGTH_SHORT).show()

    Thread {
      try {
        val dir = File(context.cacheDir, "updates")
        dir.mkdirs()
        val file = File(dir, "app-release.apk")
        file.delete()

        val conn = URL(release.apkUrl).openConnection()
        conn.connect()
        conn.inputStream.use { input ->
          FileOutputStream(file).use { output ->
            input.copyTo(output)
          }
        }

        Handler(Looper.getMainLooper()).post { installApk(file) }
      } catch (e: Exception) {
        Log.e(TAG, "Download failed", e)
        Handler(Looper.getMainLooper()).post {
          Toast.makeText(context, "Gagal download update", Toast.LENGTH_LONG).show()
        }
      }
    }.start()
  }

  private fun installApk(file: File) {
    val uri: Uri = FileProvider.getUriForFile(
      context,
      "${context.packageName}.fileprovider",
      file
    )
    val intent = Intent(Intent.ACTION_VIEW).apply {
      setDataAndType(uri, "application/vnd.android.package-archive")
      flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_GRANT_READ_URI_PERMISSION
    }
    context.startActivity(intent)
  }
}
