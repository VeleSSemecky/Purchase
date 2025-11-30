# ✅ Phase 2 Checklist: UI Migration to shared

## 🎯 Мета
Перенести весь Android UI код з `presentation` модуля в `shared` KMP модуль.

---

## 📋 Pre-Migration Checklist

- [ ] ✅ Phase 1 завершена (mockDomain готовий)
- [ ] Backup проекту створено
- [ ] Git commit поточного стану
- [ ] Java 17 налаштована
- [ ] IDE готова до KMP розробки

---

## 1️⃣ Підготовка shared.gradle.kts

### 1.1 Додати Compose Multiplatform
```kotlin
- [ ] compose.runtime
- [ ] compose.ui
- [ ] compose.material3 (вже є ✅)
- [ ] compose.foundation (вже є ✅)
- [ ] compose.animation
- [ ] compose.ui.tooling.preview (androidMain)
```

### 1.2 Додати Navigation
```kotlin
- [ ] androidx.navigation:navigation-compose:2.8.0-alpha10
- [ ] kotlinx-serialization для type-safe args
```

### 1.3 Додати Koin DI
```kotlin
- [ ] io.insert-koin:koin-core:4.0.0
- [ ] io.insert-koin:koin-compose:4.0.0
- [ ] io.insert-koin:koin-android (androidMain)
```

### 1.4 Додати Image Loading
```kotlin
- [ ] io.coil-kt.coil3:coil-compose:3.0.0
- [ ] io.coil-kt.coil3:coil-network-ktor:3.0.0
```

### 1.5 Додати ViewModel
```kotlin
- [ ] androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0
- [ ] kotlinx-coroutines-core (вже є ✅)
```

### 1.6 Додати mockDomain
```kotlin
- [ ] implementation(project(":mockDomain"))
```

### 1.7 Android-specific (androidMain)
```kotlin
- [ ] androidx.biometric:biometric:1.2.0-alpha05
- [ ] com.google.firebase:firebase-messaging-ktx:24.0.3
- [ ] androidx.activity:activity-compose
```

---

## 2️⃣ Створити структуру директорій

### 2.1 commonMain
```bash
- [ ] com/veles/purchase/presentation/
- [ ] com/veles/purchase/presentation/compose/
- [ ] com/veles/purchase/presentation/mvvm/
- [ ] com/veles/purchase/presentation/navigation/
- [ ] com/veles/purchase/presentation/model/
- [ ] com/veles/purchase/di/
- [ ] com/veles/purchase/core/
```

### 2.2 androidMain
```bash
- [ ] com/veles/purchase/platform/biometric/
- [ ] com/veles/purchase/platform/notification/
- [ ] com/veles/purchase/platform/broadcast/
- [ ] com/veles/purchase/platform/storage/
- [ ] com/veles/purchase/platform/permissions/
```

### 2.3 iosMain
```bash
- [ ] com/veles/purchase/platform/biometric/ (placeholder)
- [ ] com/veles/purchase/platform/notification/ (placeholder)
- [ ] com/veles/purchase/platform/storage/ (placeholder)
```

---

## 3️⃣ Створити expect/actual інтерфейси

### 3.1 Context/Resources
```kotlin
- [ ] expect class PlatformContext
- [ ] expect fun getString(key: String): String
- [ ] expect fun getDrawable(name: String): Any
- [ ] actual implementations (Android + iOS)
```

### 3.2 Biometric Auth
```kotlin
- [ ] expect interface BiometricAuthenticator
- [ ] actual class AndroidBiometricAuthenticator
- [ ] actual class IOSBiometricAuthenticator (stub)
```

### 3.3 Notifications
```kotlin
- [ ] expect interface NotificationManager
- [ ] actual class AndroidNotificationManager (FCM)
- [ ] actual class IOSNotificationManager (stub)
```

### 3.4 File Storage
```kotlin
- [ ] expect interface FileStorage
- [ ] actual class AndroidFileStorage
- [ ] actual class IOSFileStorage (stub)
```

### 3.5 Permissions
```kotlin
- [ ] expect interface PermissionManager
- [ ] actual implementations
```

---

## 4️⃣ Налаштувати Koin DI

### 4.1 Створити Koin модулі
```kotlin
- [ ] mockDataModule (з mockDomain repositories)
- [ ] viewModelModule (всі ViewModels)
- [ ] platformModule (expect/actual dependencies)
```

### 4.2 Замінити Dagger annotations
```kotlin
// Знайти та замінити:
- [ ] @Inject → constructor injection
- [ ] @Module → module { }
- [ ] @Provides → single { } / factory { }
- [ ] @Singleton → single { }
- [ ] @ViewModelScope → viewModel { }
```

### 4.3 Налаштувати Koin ініціалізацію
```kotlin
- [ ] initKoin() в commonMain
- [ ] startKoin в Android Application
- [ ] startKoin в iOS (SwiftUI)
```

---

## 5️⃣ Міграція ViewModels

### 5.1 Базові зміни
```kotlin
- [ ] AndroidViewModel → ViewModel
- [ ] Видалити Context з конструктора
- [ ] LiveData → StateFlow
- [ ] MutableLiveData → MutableStateFlow
```

### 5.2 Список ViewModels для міграції
```
- [ ] PurchaseListViewModel
- [ ] PurchaseDetailViewModel
- [ ] PurchaseEditViewModel
- [ ] SkuListViewModel
- [ ] SkuDetailViewModel
- [ ] SkuEditViewModel
- [ ] SettingsViewModel
- [ ] BiometricViewModel
- [ ] AuthViewModel
- [ ] MainViewModel
... (всі ~23 ViewModels)
```

### 5.3 Dependency Injection
```kotlin
- [ ] Замінити Dagger @Inject на Koin constructor
- [ ] Переписати всі @Inject lateinit var
```

---

## 6️⃣ Міграція Navigation

### 6.1 Замінити NavController
```kotlin
- [ ] NavHost → Compose Navigation
- [ ] SafeArgs → @Serializable data classes
- [ ] navigation.xml → Kotlin DSL
```

### 6.2 Створити navigation graph
```kotlin
- [ ] MainNavGraph
- [ ] PurchaseNavGraph
- [ ] SkuNavGraph
- [ ] SettingsNavGraph
- [ ] AuthNavGraph
```

### 6.3 Typed navigation
```kotlin
- [ ] Створити sealed class для routes
- [ ] Додати kotlinx-serialization
```

---

## 7️⃣ Міграція Compose UI

### 7.1 Основні @Composable functions
```
- [ ] MainScreen
- [ ] PurchaseListScreen
- [ ] PurchaseDetailScreen
- [ ] PurchaseEditScreen
- [ ] SkuListScreen
- [ ] SkuDetailScreen
- [ ] SkuEditScreen
- [ ] SettingsScreen
... (всі ~50+ screens)
```

### 7.2 Замінити Android-specific
```kotlin
- [ ] LocalContext → wrapper
- [ ] rememberLauncherForActivityResult → expect/actual
- [ ] AndroidView → платформо-специфічний код
```

### 7.3 Image Loading
```kotlin
- [ ] Glide → Coil
- [ ] Оновити всі Image() composables
- [ ] AsyncImage з Coil
```

### 7.4 Reusable Components
```
- [ ] Buttons
- [ ] TextFields
- [ ] Cards
- [ ] Dialogs
- [ ] BottomSheets
- [ ] Toolbars
```

---

## 8️⃣ Міграція Models

### 8.1 UI Models
```
- [ ] PurchaseUI (якщо є wrapper моделі)
- [ ] SkuUI
- [ ] CategoryUI
- [ ] SettingsUI
... (всі presentation models)
```

### 8.2 Mapper functions
```kotlin
- [ ] Domain → UI mappers
- [ ] UI → Domain mappers
```

---

## 9️⃣ Android-specific код

### 9.1 Biometric
```kotlin
- [ ] BiometricPrompt wrapper
- [ ] Success/Error callbacks
- [ ] Integration в ViewModel
```

### 9.2 FCM
```kotlin
- [ ] FirebaseMessagingService
- [ ] Notification handling
- [ ] Token management
```

### 9.3 BroadcastReceivers
```kotlin
- [ ] Wrapper interfaces
- [ ] Android implementations
```

---

## 🔟 Testing & Validation

### 10.1 Compilation
```bash
- [ ] ./gradlew :shared:compileKotlinMetadata
- [ ] ./gradlew :shared:compileDebugKotlinAndroid
- [ ] ./gradlew :shared:compileKotlinIosSimulatorArm64
```

### 10.2 Android App
```bash
- [ ] Build успішний
- [ ] Всі екрани відображаються
- [ ] Навігація працює
- [ ] ViewModels отримують дані
- [ ] Image loading працює
- [ ] Biometric працює
```

### 10.3 iOS Build (Preview)
```bash
- [ ] ./gradlew :shared:linkDebugFrameworkIosSimulatorArm64
- [ ] Framework генерується
- [ ] Експорт в Xcode успішний
```

### 10.4 Lint & Errors
```bash
- [ ] Немає compilation errors
- [ ] Немає lint warnings (критичних)
- [ ] Code style дотримано
```

---

## 1️⃣1️⃣ Documentation

### 11.1 Створити документацію
```markdown
- [ ] PHASE_2_SUMMARY.md
- [ ] UI_MIGRATION_GUIDE.md
- [ ] KOIN_MIGRATION_NOTES.md
- [ ] TROUBLESHOOTING.md
```

### 11.2 Оновити існуючу
```markdown
- [ ] MIGRATION_PLAN.md (оновити прогрес)
- [ ] ROADMAP.md (Phase 2 → complete)
```

### 11.3 Code comments
```kotlin
- [ ] Додати KDoc до expect/actual
- [ ] Додати TODOs для iOS impl
- [ ] Документувати складні рішення
```

---

## 1️⃣2️⃣ Git Management

### 12.1 Commits
```bash
- [ ] Commit після кожного major milestone
- [ ] Чіткі commit messages
- [ ] Не комітити build files
```

### 12.2 Branches
```bash
- [ ] Створити feature/phase-2-ui-migration
- [ ] Regular pushes
- [ ] PR перед merge в main
```

---

## 📊 Progress Tracking

```
Total Tasks: ~150+
Completed: ___/150

Major Sections:
[   ] 1. Dependencies (7 tasks)
[   ] 2. Structure (6 tasks)
[   ] 3. expect/actual (10 tasks)
[   ] 4. Koin DI (12 tasks)
[   ] 5. ViewModels (25 tasks)
[   ] 6. Navigation (8 tasks)
[   ] 7. Compose UI (50+ tasks)
[   ] 8. Models (10 tasks)
[   ] 9. Android-specific (8 tasks)
[   ] 10. Testing (8 tasks)
[   ] 11. Documentation (6 tasks)
[   ] 12. Git (5 tasks)
```

---

## 🚨 Common Pitfalls

❌ **Не робити:**
- Копіювати весь код одразу (робити частинами!)
- Пропускати тестування після кожної частини
- Ігнорувати compilation warnings
- Забувати про iOS таргети

✅ **Робити:**
- Міграція по feature (purchase, sku, settings)
- Регулярні commits
- Тестувати на Android після кожної зміни
- Документувати складні рішення

---

## 🎯 Definition of Done (Phase 2)

Phase 2 вважається завершеною коли:

- [x] ✅ Всі Compose UI компоненти в shared/commonMain
- [x] ✅ Всі ViewModels працюють з Koin
- [x] ✅ Navigation повністю на Compose
- [x] ✅ expect/actual створені для platform code
- [x] ✅ Android app працює повністю
- [x] ✅ iOS framework компілюється
- [x] ✅ Biometric (Android only) працює
- [x] ✅ FCM (Android only) працює
- [x] ✅ Image loading через Coil
- [x] ✅ Документація створена
- [x] ✅ Code review пройдено
- [x] ✅ Готово до Phase 3 (підключення mockDomain)

---

## ⏭️ After Phase 2

Наступні кроки (Phase 3):
1. Додати mockDomain залежність
2. Налаштувати Koin з mock repositories
3. Підключити ViewModels до mockDomain
4. Тестувати на Android + iOS

---

**Good luck with Phase 2!** 💪🚀

_Estimated Time: 2-3 weeks_  
_Difficulty: ⭐⭐⭐⭐☆ (High)_  
_Impact: 🔥🔥🔥🔥🔥 (Critical)_

