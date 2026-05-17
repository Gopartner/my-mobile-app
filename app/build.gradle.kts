plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
}

android {
  namespace = "com.example.my_mobile_app"
  compileSdk = 35

  defaultConfig {
    applicationId = "com.example.my_mobile_app"
    minSdk = 24
    targetSdk = 35
    versionCode = 1
    versionName = "1.0"
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  signingConfigs {
    create("release") {
      storeFile = file(rootProject.file("keystore.jks"))
      storePassword = System.getenv("KEYSTORE_PASSWORD") ?: ""
      keyAlias = System.getenv("KEY_ALIAS") ?: ""
      keyPassword = System.getenv("KEY_PASSWORD") ?: ""
    }
  }

  buildTypes {
    release {
      isMinifyEnabled = true
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
      )
      signingConfig = signingConfigs.getByName("release")
    }
    debug {
      signingConfig = signingConfigs.getByName("debug")
    }
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  kotlinOptions {
    jvmTarget = "17"
  }

  buildFeatures {
    viewBinding = true
  }
}

dependencies {
  implementation(libs.core.ktx)
  implementation(libs.appcompat)
  implementation(libs.activity.ktx)
  implementation(libs.material)
  implementation(libs.constraintlayout)

  testImplementation(libs.junit)
  androidTestImplementation(libs.ext.junit)
  androidTestImplementation(libs.espresso)
}
