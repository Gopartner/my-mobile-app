# Keep view binding classes
-keep class com.example.my_mobile_app.databinding.** { *; }

# Keep Kotlin metadata
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt

# Keep Gson/Reflection if used
-keepclassmembers class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

# Default Android ProGuard rules
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
