# 🛒 Purchase - Kotlin Multiplatform App

**A cross-platform shopping list and purchase management application built with Kotlin Multiplatform (KMP), Compose Multiplatform, and Firebase.**

---

## 📱 Platform Support

- ✅ **Android** - Fully supported
- ✅ **iOS** - Fully supported (Phase 5 complete)

---

## 🏗️ Architecture

**Tech Stack:**
- **Kotlin Multiplatform (KMP)** - Shared business logic across platforms
- **Compose Multiplatform** - Shared UI components
- **Firebase KMP (GitLive)** - Cross-platform Firebase integration
- **Koin** - Dependency injection
- **Room** - Local database
- **Ktor** - Networking

**Architecture Pattern:**
- Clean Architecture
- MVVM (Model-View-ViewModel)
- Repository Pattern
- Unidirectional Data Flow

---

## 🚀 Quick Start

### Prerequisites
- **Android Studio** (latest version)
- **Xcode** (for iOS development)
- **JDK 17** or higher
- **CocoaPods** (for iOS dependencies)

### Building the Project

#### Android:
```bash
./gradlew :androidApp:assembleDebug
```

#### iOS:
```bash
./gradlew :shared:podInstall
# Then open iosApp/iosApp.xcworkspace in Xcode
```

For detailed iOS setup instructions, see: [`IOS_RUN_GUIDE.md`](IOS_RUN_GUIDE.md)

---

## 📚 Documentation

### Current Documentation

**Phase Completion:**
- ✅ [`PHASE_1_COMPLETE.md`](PHASE_1_COMPLETE.md) - Infrastructure setup
- ✅ [`FIREBASE_KMP_MIGRATION_COMPLETE.md`](FIREBASE_KMP_MIGRATION_COMPLETE.md) - **Complete Firebase migration summary**
- ✅ [`PHASE_5_5_FIREBASE_KMP_STARTED.md`](PHASE_5_5_FIREBASE_KMP_STARTED.md) - Firebase KMP migration overview
- ✅ [`PHASE_5_5_FINAL_STATUS.md`](PHASE_5_5_FINAL_STATUS.md) - Phase 5.5 complete
- ✅ [`PHASE_5_6_COMPLETE.md`](PHASE_5_6_COMPLETE.md) - Phase 5.6 complete
- 🔄 [`PHASE_6_PRODUCTION_READINESS.md`](PHASE_6_PRODUCTION_READINESS.md) - **Testing & deployment (NEXT)**

**Firebase KMP Migration:**
- 📘 [`FIREBASE_KMP_MIGRATION_GUIDE.md`](FIREBASE_KMP_MIGRATION_GUIDE.md) - Migration guide for additional repositories
- 📋 [`PHASE_5_5_STEP_2_COMPLETE.md`](PHASE_5_5_STEP_2_COMPLETE.md) - Firebase initialization details
- 📋 [`PHASE_5_5_STEP_3_COMPLETE.md`](PHASE_5_5_STEP_3_COMPLETE.md) - Repository migration details

**Platform Guides:**
- 🍎 [`IOS_RUN_GUIDE.md`](IOS_RUN_GUIDE.md) - How to run the app on iOS

**Project Planning:**
- 🗺️ [`ROADMAP.md`](ROADMAP.md) - Project roadmap and future plans

---

## ✅ Project Status

### Current Phase: 5.5 - Firebase KMP Migration ✅ COMPLETE

**Completion Status:**
- ✅ Step 1: Firebase KMP dependencies added
- ✅ Step 2: Firebase initialized on both platforms
- ✅ Step 3: Core repositories migrated (Auth, User, Token)
- ✅ Step 4: Testing & verification complete

**What's Working:**
- ✅ Google Sign-In authentication (Android & iOS)
- ✅ Real-time user data synchronization (Android & iOS)
- ✅ FCM token management (Android & iOS)
- ✅ Firestore operations (Android & iOS)

**Repositories Migrated to KMP:**
1. ✅ AuthWithGoogleRepository
2. ✅ FirebaseGetUserRepository
3. ✅ FirebaseMessageTokenRepository

Additional repositories can be migrated using the pattern in [`FIREBASE_KMP_MIGRATION_GUIDE.md`](FIREBASE_KMP_MIGRATION_GUIDE.md)

---

## 🔥 Firebase Integration

**Firebase KMP (GitLive) v2.3.1**

The app uses the GitLive Firebase KMP SDK for cross-platform Firebase support:
- ✅ Firebase Authentication
- ✅ Cloud Firestore
- ✅ Firebase Storage
- ✅ Cloud Messaging (FCM)

**Migration from Android SDK:**
- Android Firebase SDK → GitLive Firebase KMP SDK
- Platform-specific initialization (Android auto, iOS explicit)
- Shared repository implementations

See: [`PHASE_5_5_FINAL_STATUS.md`](PHASE_5_5_FINAL_STATUS.md) for complete Firebase migration details.

---

## 📦 Module Structure

```
Purchase/
├── androidApp/          # Android-specific code
├── iosApp/             # iOS-specific code (SwiftUI)
├── shared/             # Shared KMP code
│   ├── commonMain/     # Common business logic & UI
│   ├── androidMain/    # Android-specific implementations
│   └── iosMain/        # iOS-specific implementations
├── mockDomain/         # Mock data for testing
├── domain/            # Legacy domain layer (being migrated)
├── data/              # Legacy data layer (being migrated)
└── presentation/      # Legacy presentation layer (being migrated)
```

---

## 🧪 Testing

### Android Testing:
```bash
./gradlew :androidApp:testDebugUnitTest
```

### iOS Testing:
Open `iosApp.xcworkspace` in Xcode and run tests (Cmd+U)

---

## 🛠️ Development

### Adding New Features

1. **Define domain model** in `shared/src/commonMain/kotlin/com/.../domain/model/`
2. **Create repository interface** in `shared/src/commonMain/kotlin/com/.../domain/repository/`
3. **Implement repository** in `shared/src/commonMain/kotlin/com/.../data/repository/`
4. **Add to Koin DI** in repository module
5. **Create ViewModel** in `shared/src/commonMain/kotlin/com/.../presentation/`
6. **Build UI** using Compose Multiplatform

### Migrating Firebase Repositories

Follow the step-by-step guide in [`FIREBASE_KMP_MIGRATION_GUIDE.md`](FIREBASE_KMP_MIGRATION_GUIDE.md)

Estimated time: 30-60 minutes per repository

---

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test on both Android and iOS
5. Submit a pull request

---

## 📄 License

[Add your license here]

---

## 🔗 Resources

**Kotlin Multiplatform:**
- [Official KMP Docs](https://kotlinlang.org/docs/multiplatform.html)
- [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)

**Firebase KMP:**
- [GitLive Firebase KMP SDK](https://github.com/GitLiveApp/firebase-kotlin-sdk)

**Dependency Injection:**
- [Koin Documentation](https://insert-koin.io/)

---

**Last Updated:** December 25, 2025  
**Current Version:** Phase 5.5 Complete  
**Status:** ✅ Production Ready (Core Features)

