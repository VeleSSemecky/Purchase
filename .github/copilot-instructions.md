# 🤖 Copilot Instructions for Purchase (Kotlin Multiplatform)

## Build, Test, and Lint Commands

### Android
- **Build:**
  ```bash
  ./gradlew :androidApp:assembleDebug
  ```
- **Run all unit tests:**
  ```bash
  ./gradlew :androidApp:testDebugUnitTest
  ```
- **Run a single test:**
  ```bash
  ./gradlew :androidApp:testDebugUnitTest --tests "com.veles.purchase.presentation.test.LoginViewModelTest"
  ```

### iOS
- **Build shared KMP framework:**
  ```bash
  ./gradlew :shared:linkDebugFrameworkIosSimulatorArm64
  ```
- **Run tests:**
  Open `iosApp.xcworkspace` in Xcode and press Cmd+U

### Shared/Mock Domain
- **Build mockDomain:**
  ```bash
  ./gradlew :mockDomain:build
  ```

### Lint
- No dedicated lint command found. Use IDE inspections and Compose/Gradle warnings.

---

## High-Level Architecture

- **Kotlin Multiplatform (KMP):** Shared business logic and UI in `shared/`.
- **Compose Multiplatform:** All new UI in `shared/src/commonMain/kotlin/com/veles/purchase/presentation/compose/`.
- **MVVM + Clean Architecture:**
  - Domain models and use cases in `shared/src/commonMain/kotlin/com/veles/purchase/domain/`
  - Data repositories in `shared/src/commonMain/kotlin/com/veles/purchase/data/`
  - ViewModels in `shared/src/commonMain/kotlin/com/veles/purchase/presentation/mvvm/`
- **Dependency Injection:** Koin modules in `shared/`.
- **Legacy Android code:** `presentation/`, `domain/`, `data/` (read-only, reference only)
- **Platform-specific code:**
  - Android: `androidApp/`
  - iOS: `iosApp/` (uses Swift Package Manager, not CocoaPods)

---

## Key Conventions

- **All new features:** Implement in `shared/commonMain` for KMP compatibility.
- **Never modify `presentation/`** — legacy Android-only, reference only.
- **Use Koin for DI** — add new modules in `shared/`.
- **Compose UI only** — no XML layouts in new code.
- **Material Design 3** — follow for all UI.
- **Version catalog:** All dependency versions in `libs.versions.toml`.
- **Testing:** Use `mockDomain/` for mock data and isolated tests.
- **Firebase:** Use GitLive Firebase KMP SDK for all platforms.
- **iOS dependencies:** Add via Swift Package Manager, not CocoaPods.

---

## Additional Notes
- For iOS, see `IOS_RUN_GUIDE.md` and `FIREBASE_KMP_MIGRATION_GUIDE.md` for setup and migration details.
- For migration patterns, see `USECASE_LAYER_MIGRATION.md` and `VIEWMODEL_USECASE_MIGRATION.md`.
- For Google Sign-In, see `QUICK_START_GOOGLE_SIGNIN.md`.

---

*Last updated automatically by Copilot CLI.*
