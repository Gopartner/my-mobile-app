# AGENTS — Project Context

## Project Identity

- **App:** Pythagoras Calculator
- **Repo:** github.com/Gopartner/my-mobile-app
- **Role:** Android app developer
- **Signer:** yudibilly (Surabaya, Jawa Timur, ID)
- **Distribution:** GitHub Releases (not Play Store)

---

## Tech Stack

| Layer | Choice |
|-------|--------|
| Language | Kotlin 2.1.0 |
| UI | ViewBinding + XML Layout (ConstraintLayout) |
| Theme | Material 3 (DayNight — auto dark mode) |
| Build | Gradle 8.11 + AGP 8.7.3 |
| Min SDK | 24 (Android 7.0) |
| Target | 35 (Android 15) |
| Version Catalog | `gradle/libs.versions.toml` |
| DI | None (manual) |

---

## Code Conventions

- **Package:** `com.example.my_mobile_app`
- **Indentation:** 2 spaces
- **ViewBinding:** always `private lateinit var binding: ActivityMainBinding`
- **Naming:** camelCase for vars/funs, PascalCase for classes
- **No comments** in code unless explaining complex logic
- **No emojis** in code or commit messages
- **No RxJava** — use Thread + Handler for background work
- **No Hilt/Dagger** — keep dependencies minimal

---

## Architecture

```
MainActivity.kt
  ├─ onCreate()        → setup binding + TextWatcher + checkUpdate()
  ├─ calculate()       → sqrt(A² + B²), update 3 TextViews
  ├─ clearResults()    → reset to "-"
  └─ formatNumber()    → locale-aware number formatting

AppUpdater.kt
  ├─ check(callback)   → Thread: GET GitHub API, parse JSON, compare versionCode
  ├─ showUpdateDialog() → AlertDialog with download button
  ├─ downloadAndInstall() → Thread: download APK to cache, FileProvider install
```

---

## Workflow Rules

### CI Triggers

| Workflow | When |
|----------|------|
| `Android CI` | Push / PR to `main` or `develop` |
| `Emulator Tests` | After Android CI completes |
| `Release Build` | Manual `workflow_dispatch` or tag `v*` push |

### Release Process

1. Trigger `Release Build` workflow manually via:
   ```
   gh workflow run 278098630 --ref main
   ```
2. Workflow:
   - Generates keystore (`CN=yudibilly, L=Surabaya, ST=Jawa Timur, C=ID`)
   - Patches `versionCode` and `versionName` from `github.run_number`
   - Builds signed APK + AAB
   - Creates GitHub Release with `v1.0.<run_number>` tag
3. APK is available at:
   - GitHub Release page
   - HTTP server at `http://192.168.100.106:8080` (local)

### Version Scheme

- `versionCode` = `run_number` (sequential integer per repo)
- `versionName` = `1.0.<run_number>`
- Tag format: `v1.0.<run_number>`

---

## Common Tasks

### Build APK Locally
```bash
./gradlew assembleDebug
```

### Trigger Release
```bash
gh workflow run 278098630 --ref main
```

### Download Latest APK
```bash
gh run download <run-id> --name app-release-apk --dir apk
```

### Check Workflow Status
```bash
gh run list --limit 5
```

### Install on Device
```bash
adb install apk/app-release.apk
```

### Update Version Catalog
Edit `gradle/libs.versions.toml` — versions are centralized there.

---

## Update Mechanism

App checks for updates on every startup:

1. **GET** `api.github.com/repos/Gopartner/my-mobile-app/releases/latest`
2. **Parse** `tag_name` → extract `run_number` as `remoteVersionCode`
3. **Compare** with current `versionCode` (from `PackageManager`)
4. **If new version found** → show `AlertDialog` with "Update" / "Nanti"
5. **Download** APK to `cache/updates/` on background thread
6. **Install** via `FileProvider` + `ACTION_VIEW` intent

---

## Files to Know

| Path | Purpose |
|------|---------|
| `app/src/main/java/.../MainActivity.kt` | Main logic |
| `app/src/main/java/.../updater/AppUpdater.kt` | Auto-update |
| `app/src/main/res/layout/activity_main.xml` | Full UI |
| `app/src/main/res/values/` | Colors, strings, themes |
| `app/src/main/res/values-night/` | Dark mode overrides |
| `app/src/main/res/drawable/` | Vector drawables (triangle, icon) |
| `app/build.gradle.kts` | Module build config |
| `build.gradle.kts` | Root plugin declarations |
| `gradle/libs.versions.toml` | Version catalog |
| `.github/workflows/android.yml` | Debug CI |
| `.github/workflows/emulator.yml` | Instrumented tests |
| `.github/workflows/release.yml` | Release build + signing |
| `gradle/wrapper/gradle-wrapper.properties` | Gradle distribution |

---

## Design Rules

- **Result card** is ABOVE input card
- **Inputs** are stacked vertically (A on top, B below), not side by side
- **Results** show in 3 units: mm, cm, m (descending)
- **Green card** for results (`@color/result_surface`)
- **Blue** for side A, **Orange** for side B, **Green** for hypotenuse C
- **Real-time** — every keystroke triggers `calculate()`
- **Triangle diagram** uses `@drawable/triangle_diagram` vector

---

## Git Rules

- Branch: `main` (default), `develop` for WIP
- Commit: conventional commits (`feat:`, `fix:`, `docs:`, `ci:`, `design:`)
- No force push
- Keep `main` always releasable

---

## Documentation Sync Rules

Every change must keep docs up to date:

| If you change… | You must update… |
|----------------|------------------|
| Code logic, UI layout, architecture | `AGENTS.md` (architecture, design rules) |
| Dependencies, AGP, Gradle, SDK | `README.md` (tech stack table) + `AGENTS.md` (tech stack) |
| CI workflows, release process | `README.md` (CI/CD section, badges) + `AGENTS.md` (workflow rules) |
| Features, behavior | `README.md` (features list) + `AGENTS.md` (design rules) |
| App icon, screenshots | `README.md` (preview section) |
| Version scheme | `README.md` + `AGENTS.md` (version scheme) |
| Team info, signing key | `AGENTS.md` (team section) |

**Release checklist** (before triggering release workflow):
1. Bump version in `README.md` if needed
2. Update feature list if new functionality added
3. Update CI/CD section if workflow changed
4. Verify `AGENTS.md` reflects current architecture
5. Commit docs changes **before** triggering release

---

## Team

- **Developer:** yudibilly
- **Location:** Surabaya, Jawa Timur, Indonesia
- **Starter:** https://github.com/Gopartner/android-starter
