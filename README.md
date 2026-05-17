# Pythagoras Calculator

<p align="center">
  <b>Hitung sisi miring segitiga siku-siku secara real-time.</b><br>
  Input dalam cm, hasil langsung dalam mm, cm, dan m.
</p>

<p align="center">
  <a href="https://github.com/Gopartner/my-mobile-app/releases/latest">
    <img src="https://img.shields.io/github/v/release/Gopartner/my-mobile-app?color=1A73E8&label=Release" alt="GitHub Release">
  </a>
  <a href="https://github.com/Gopartner/my-mobile-app/actions/workflows/android.yml">
    <img src="https://img.shields.io/github/actions/workflow/status/Gopartner/my-mobile-app/android.yml?branch=main&label=Android%20CI" alt="Android CI">
  </a>
  <a href="https://github.com/Gopartner/my-mobile-app/actions/workflows/release.yml">
    <img src="https://img.shields.io/github/actions/workflow/status/Gopartner/my-mobile-app/release.yml?label=Release%20Build" alt="Release Build">
  </a>
  <img src="https://img.shields.io/badge/Kotlin-2.1.0-7F52FF?logo=kotlin" alt="Kotlin">
  <img src="https://img.shields.io/badge/API-24%2B-success" alt="Min SDK">
  <img src="https://img.shields.io/badge/License-MIT-blue" alt="License">
</p>

---

## ✨ Fitur

- **⚡ Real-time calculation** — Hasil langsung muncul saat mengetik
- **📐 Pythagoras theorem** — C = √(A² + B²)
- **📏 Multi-unit output** — Milimeter (mm), Sentimeter (cm), Meter (m)
- **🎨 Custom Canvas triangle** — Dynamic labels update real-time
- **🧩 Modular architecture** — domain/utils/ui layers separated
- **🌙 Dark mode support** — Otomatis mengikuti sistem
- **📱 Minimal UI** — Fokus pada fungsi utama
- **🔄 In-app update** — Notifikasi update otomatis dari GitHub Releases

---

## 🧰 Tech Stack

| Technology | Version |
|-----------|---------|
| Kotlin | 2.1.0 |
| Android Gradle Plugin | 8.7.3 |
| Gradle | 8.11 |
| Min SDK | 24 (Android 7.0) |
| Target SDK | 35 (Android 15) |
| ViewBinding | ✅ |
| Material 3 | ✅ |
| ConstraintLayout | ✅ |
| Custom View (Canvas) | ✅ |
| Design Token System | ✅ |

---

## 🏗️ Architecture

```
app/
├── src/main/
│   ├── java/com/example/my_mobile_app/
│   │   ├── MainActivity.kt          # UI orchestration
│   │   ├── ui/
│   │   │   └── TriangleView.kt      # Custom Canvas triangle
│   │   ├── domain/
│   │   │   └── PythagorasCalculator.kt  # Pure math
│   │   ├── utils/
│   │   │   └── NumberFormatter.kt   # Locale formatting
│   │   └── updater/
│   │       └── AppUpdater.kt        # GitHub update checker
│   ├── res/
│   │   ├── drawable/                 # Vector icons
│   │   ├── layout/
│   │   │   └── activity_main.xml    # UI utama
│   │   ├── values/                   # Tema, warna, string, dimens, typography, shapes, attrs
│   │   └── values-night/            # Dark theme
│   └── AndroidManifest.xml
├── build.gradle.kts
└── gradle/libs.versions.toml        # Version catalog
```

---

## 🚀 CI/CD Pipeline

| Workflow | Trigger | Output |
|----------|---------|--------|
| **Android CI** | Push / PR ke `main` | Debug APK + unit test |
| **Release Build** | Workflow dispatch / tag `v*` | Signed APK + AAB + GitHub Release |

### Build di Cloud, Beban Nol di Laptop

```text
Laptop Anda                    GitHub Runner
┌──────────────┐              ┌───────────────────┐
│  Edit kode   │  ──push──>   │  Android CI       │
│  git commit  │              │  ├─ assembleDebug  │
│  git push    │              │  ├─ test           │
└──────────────┘              │  └─ upload APK     │
                              │                   │
                              │  Release Build    │
                              │  ├─ generate key  │
                              │  ├─ sign APK/AAB  │
                              │  └─ publish       │
                              └───────────────────┘
```

### Status Workflow

| Workflow | Badge |
|----------|-------|
| Android CI | ![Android CI](https://img.shields.io/github/actions/workflow/status/Gopartner/my-mobile-app/android.yml?branch=main) |
| Release Build | ![Release Build](https://img.shields.io/github/actions/workflow/status/Gopartner/my-mobile-app/release.yml) |

---

## 📲 Download & Install

Unduh APK terbaru dari [GitHub Releases](https://github.com/Gopartner/my-mobile-app/releases/latest).

```bash
# Install via ADB
adb install app-release.apk
```

Atau scan QR Code dari halaman release untuk download langsung di HP.

---

## 🔄 Update Mechanism

Aplikasi otomatis mengecek versi baru saat startup:

1. **Cek** → GitHub Releases API
2. **Bandingkan** → versionCode
3. **Popup** → "Update Tersedia"
4. **Download** → langsung dari GitHub
5. **Install** → via FileProvider + PackageInstaller

Tidak perlu Google Play Store.

---

## 🛠️ Local Development

```bash
# Clone
git clone https://github.com/Gopartner/my-mobile-app.git

# Build debug APK
./gradlew assembleDebug

# Run unit tests
./gradlew testDebugUnitTest

# Build release (butuh keystore)
./gradlew assembleRelease
```

### Prerequisites

- JDK 17+
- Android SDK 35
- Gradle 8.11 (wrapper included)

---

## 📦 Starter Script

Project ini dibuat menggunakan [android-starter](https://github.com/Gopartner/android-starter) — tool untuk scaffold project Android + CI/CD dalam satu perintah:

```bash
./init.sh MyApp
```

---

## 📄 License

Distributed under the MIT License.

---

<p align="center">
  Dibuat dengan ❤️ oleh <a href="https://github.com/Gopartner">yudibilly</a><br>
  <sub>Surabaya, Jawa Timur, Indonesia</sub>
</p>
