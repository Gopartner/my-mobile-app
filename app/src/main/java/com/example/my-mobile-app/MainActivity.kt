package com.example.my-mobile-app

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val tv = TextView(this).apply {
      text = "Hello from my-mobile-app"
      textSize = 24f
      setPadding(32, 32, 32, 32)
    }
    setContentView(tv)
  }
}
