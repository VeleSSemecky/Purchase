# План міграції Android → Kotlin Multiplatform

## Мета
Мігрувати Android проект на Kotlin Multiplatform для підтримки Android та iOS платформ.

## Стратегія: Mock-First Approach

Використовуємо підхід з тимчасовим mockDomain модулем для незалежної міграції UI та domain/data шарів.

---

## ✅ ФАЗА 1: Створення mockDomain модуля (ЗАВЕРШЕНО)

### Мета
Створити KMP модуль з mock реалізаціями domain layer для тестування UI.

### Виконано

#### 1. Структура модуля
```
mockDomain/
├── mockDomain.gradle.kts          # KMP конфігурація
├── README.md                       # Документація модуля
└── src/
    ├── commonMain/kotlin/
    │   └── com/veles/purchase/domain/
    │       ├── model/              # 10+ моделей
    │       │   ├── purchase/       # PurchaseModel, PurchaseCategoryModel, etc.
    │       │   ├── setting/        # PurchaseSetting, SizeType, ShapeType
    │       │   ├── SkuModel.kt
    │       │   ├── SkuPhotoModel.kt
    │       │   └── SkuSumMonthModel.kt
    │       ├── repository/         # Interfaces + Mock implementations
    │       │   ├── purchase/
    │       │   │   ├── PurchaseRepository.kt
    │       │   │   └── MockPurchaseRepository.kt
    │       │   ├── sku/
    │       │   │   ├── SkuRepository.kt
    │       │   │   ├── MockSkuRepository.kt
    │       │   │   ├── SkuPhotoRepository.kt
    │       │   │   └── MockSkuPhotoRepository.kt
    │       │   └── setting/
    │       │       ├── SettingRepository.kt
    │       │       └── MockSettingRepository.kt
    │       ├── di/
    │       │   └── MockDomainModule.kt
    │       └── utill/
    │           └── Utill.kt
    ├── androidMain/kotlin/
    └── iosMain/kotlin/
```

#### 2. Ключові адаптації для KMP

**Java → Kotlin stdlib:**
- ✅ `java.util.UUID` → `kotlin.uuid.Uuid`
- ✅ `java.util.Calendar` → `kotlinx.datetime.Clock`
- ✅ `java.time.LocalDateTime` → `kotlinx.datetime.LocalDateTime`
- ✅ `java.util.Currency` → Hardcoded "USD"/"UAH"

**Mock repositories з реалістичними даними:**
- ✅ MockPurchaseRepository - 5 покупок (молоко, хліб, книга, кава, телефон)
- ✅ MockSkuRepository - 3 SKU з цінами та описами
- ✅ MockSkuPhotoRepository - управління фото
- ✅ MockSettingRepository - налаштування UI

#### 3. Gradle конфігурація
- ✅ Додано `mockDomain` в `settings.gradle.kts`
- ✅ Налаштовано KMP таргети: Android, iOS (x64, arm64, simulatorArm64)
- ✅ Додано залежності: coroutines, kotlinx-datetime

#### 4. DI модуль
- ✅ `MockDomainModule` - singleton factory для всіх repositories
- ✅ Підготовлено для інтеграції з Koin (Фаза 3)

### Результат
mockDomain модуль створено та готовий для використання. Всі моделі та repositories адаптовані для KMP.

---

## 🔄 ФАЗА 2: Міграція Presentation UI в shared модуль (В ПРОЦЕСІ)

### Мета
Перенести весь Android UI код з `presentation` модуля в `shared` KMP модуль.

### План дій

#### 2.1. Підготовка shared модуля
- [ ] Оновити `shared.gradle.kts` з необхідними залежностями:
  - Compose Multiplatform
  - Navigation Compose (мультиплатформна версія)
  - Coil для image loading
  - Koin для DI
  - kotlinx-serialization

#### 2.2. Налаштування структури
```
shared/src/
├── commonMain/kotlin/
│   └── com/veles/purchase/
│       ├── presentation/        # З presentation/src/main/java
│       │   ├── compose/         # Compose UI компоненти
│       │   ├── mvvm/            # ViewModels
│       │   ├── navigation/      # Navigation graph
│       │   └── model/           # UI models
│       ├── di/                  # Koin modules
│       └── core/                # Core utilities
├── androidMain/kotlin/
│   └── com/veles/purchase/
│       ├── platform/            # Android-specific код
│       │   ├── biometric/       # BiometricPrompt wrapper
│       │   ├── notification/    # FCM
│       │   └── broadcast/       # BroadcastReceiver
│       └── expect/              # actual implementations
└── iosMain/kotlin/
    └── com/veles/purchase/
        ├── platform/            # iOS-specific код
        └── expect/              # actual implementations
```

#### 2.3. Створити expect/actual для platform-specific коду
- [ ] Context/Resources → expect/actual
- [ ] Image loading → Coil KMP
- [ ] Biometric → expect/actual (Android: BiometricPrompt, iOS: LocalAuthentication)
- [ ] Notifications → expect/actual (Android: FCM, iOS: APNs)
- [ ] File storage → expect/actual
- [ ] Permissions → expect/actual

#### 2.4. Міграція DI: Dagger → Koin
```kotlin
// Було (Dagger):
@Module
class PresentationModule {
    @Provides
    fun provideRepository(): Repository = ...
}

// Стане (Koin):
val presentationModule = module {
    single<Repository> { RepositoryImpl() }
}
```

#### 2.5. Міграція навігації
- [ ] Navigation Component → Compose Navigation (KMP)
- [ ] SafeArgs → kotlinx-serialization для type-safe навігації
- [ ] Перенести всі navigation graphs

#### 2.6. Адаптація ViewModels
- [ ] Базовий клас: AndroidViewModel → ViewModel (без Context)
- [ ] Використання StateFlow замість LiveData
- [ ] Інжекція через Koin constructor injection

#### 2.7. Migrація Compose UI
- [ ] Перенести всі @Composable функції
- [ ] Замінити Android-specific компоненти:
  - `rememberLauncherForActivityResult` → expect/actual
  - `LocalContext` → ресурси через expect/actual
  - Image loading через Coil KMP
- [ ] Перевірити Material3 компоненти (вони вже KMP)

#### 2.8. Створити wrapper interfaces для складної логіки
Згідно з рішенням:
```kotlin
// commonMain
interface BiometricAuthenticator {
    suspend fun authenticate(title: String, subtitle: String): Boolean
}

// androidMain
actual class AndroidBiometricAuthenticator : BiometricAuthenticator {
    // BiometricPrompt implementation
}

// iosMain  
actual class IOSBiometricAuthenticator : BiometricAuthenticator {
    // LocalAuthentication implementation (Фаза 5-6)
}
```

### Технічні рішення (з урахуванням Further Considerations)

#### 1. Platform-specific код
- **Wrapper interfaces** для складної логіки:
  - Biometric authentication
  - Push notifications
  - File storage
  - Permissions
- **expect/actual** для простих випадків:
  - Context/Resources
  - Platform info
  - Simple utilities

#### 2. Біометрія та FCM
- **Фаза 2-3**: Android-only реалізації в `shared/androidMain`
- **Фаза 5-6**: iOS реалізації після міграції data/domain
- Використання wrapper interfaces для ізоляції

#### 3. Image Loading
- **Coil** (має KMP підтримку)
- Альтернатива: Kamel (native KMP library)

### Залежності для додавання в shared.gradle.kts
```kotlin
commonMain {
    // Navigation
    implementation("org.jetbrains.androidx.navigation:navigation-compose:2.8.0-alpha10")
    
    // Koin
    implementation("io.insert-koin:koin-core:4.0.0")
    implementation("io.insert-koin:koin-compose:4.0.0")
    
    // Image loading
    implementation("io.coil-kt.coil3:coil-compose:3.0.0")
    implementation("io.coil-kt.coil3:coil-network-ktor:3.0.0")
    
    // Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
    
    // ViewModel
    implementation("org.jetbrains.androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")
}

androidMain {
    // Biometric
    implementation("androidx.biometric:biometric:1.2.0-alpha05")
    
    // FCM
    implementation("com.google.firebase:firebase-messaging-ktx:24.0.3")
}
```

### Статус
- [ ] 2.1. Підготовка shared модуля
- [ ] 2.2. Налаштування структури
- [ ] 2.3. expect/actual інтерфейси
- [ ] 2.4. Міграція DI
- [ ] 2.5. Міграція навігації
- [ ] 2.6. Адаптація ViewModels
- [ ] 2.7. Міграція Compose UI
- [ ] 2.8. Wrapper interfaces

---

## 🔜 ФАЗА 3: Інтеграція mockDomain в shared UI

### Мета
Підключити mockDomain до shared модуля та протестувати весь UI flow.

### План дій
- [ ] Додати залежність `implementation(project(":mockDomain"))` в shared.gradle.kts
- [ ] Налаштувати Koin module з mock repositories:
```kotlin
val mockDataModule = module {
    single<PurchaseRepository> { MockPurchaseRepository() }
    single<SkuRepository> { MockSkuRepository() }
    single<SkuPhotoRepository> { MockSkuPhotoRepository() }
    single<SettingRepository> { MockSettingRepository() }
}
```
- [ ] Інжектити repositories в ViewModels через Koin
- [ ] Протестувати на Android:
  - Всі екрани відображаються
  - Навігація працює
  - CRUD операції з mock даними
  - Списки, деталі, форми
- [ ] Протестувати на iOS Simulator:
  - Компіляція iOS framework
  - UI рендериться коректно
  - Навігація працює
  - Mock дані відображаються

### Критерії успіху
- ✅ Android app працює з mock даними
- ✅ iOS app працює з mock даними
- ✅ Вся навігація функціонує
- ✅ Немає залежностей від реального data layer

---

## 🔜 ФАЗА 4: Міграція data модуля на KMP

### Мета
Створити KMP версію data модуля з реальними реалізаціями.

### План дій

#### 4.1. Створити KMP структуру
```
data/
└── src/
    ├── commonMain/kotlin/
    │   └── com/veles/purchase/data/
    │       ├── repository/       # Repository implementations
    │       ├── datasource/       # Local/Remote data sources
    │       └── mapper/           # Entity ↔ Model mappers
    ├── androidMain/kotlin/
    │   └── com/veles/purchase/data/
    │       ├── local/            # Room DB (Android)
    │       ├── remote/           # Firebase Android SDK
    │       └── storage/          # Android storage
    └── iosMain/kotlin/
        └── com/veles/purchase/data/
            ├── local/            # Room DB (iOS) або CoreData
            ├── remote/           # Firebase iOS SDK
            └── storage/          # iOS storage
```

#### 4.2. Міграція Room database
- [ ] Перенести існуючу Room DB з `shared` в `data`
- [ ] Налаштувати KSP для KMP
- [ ] Створити DAOs для всіх entities
- [ ] Налаштувати Room для iOS (якщо використовуємо Room KMP)

#### 4.3. expect/actual для platform APIs

**Firebase:**
```kotlin
// commonMain
expect class FirebaseFirestore {
    suspend fun getCollection(path: String): List<Document>
    suspend fun saveDocument(path: String, data: Map<String, Any>)
}

// androidMain
actual class FirebaseFirestore {
    private val firestore = Firebase.firestore
    // Android Firebase SDK implementation
}

// iosMain
actual class FirebaseFirestore {
    private val firestore = FIRFirestore.firestore()
    // iOS Firebase SDK implementation (Фаза 5-6)
}
```

**Storage:**
```kotlin
expect interface FileStorage {
    suspend fun saveFile(path: String, data: ByteArray)
    suspend fun readFile(path: String): ByteArray?
}
```

**Network:**
- [ ] Retrofit → Ktor Client (KMP)
- [ ] Налаштувати endpoints
- [ ] Serialization через kotlinx-serialization

#### 4.4. Repository implementations
- [ ] PurchaseRepositoryImpl
- [ ] SkuRepositoryImpl
- [ ] SkuPhotoRepositoryImpl
- [ ] SettingRepositoryImpl

### Залежності
```kotlin
commonMain {
    // Ktor
    implementation("io.ktor:ktor-client-core:3.0.0")
    implementation("io.ktor:ktor-client-content-negotiation:3.0.0")
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.0.0")
    
    // Room KMP (якщо використовуємо)
    implementation("androidx.room:room-runtime:2.7.0-alpha12")
}

androidMain {
    implementation("io.ktor:ktor-client-android:3.0.0")
    implementation("com.google.firebase:firebase-firestore-ktx:25.1.1")
    implementation("com.google.firebase:firebase-storage-ktx:21.0.1")
}

iosMain {
    implementation("io.ktor:ktor-client-darwin:3.0.0")
    // Firebase iOS через CocoaPods
}
```

---

## 🔜 ФАЗА 5: Міграція domain модуля на KMP

### Мета
Переробити існуючий JVM-only domain на KMP модуль.

### План дій
- [ ] Змінити `domain.gradle.kts` з `kotlin-jvm` на `kotlin-multiplatform`
- [ ] Перенести код з `src/main/java` в `src/commonMain/kotlin`
- [ ] Адаптувати Java APIs (аналогічно mockDomain):
  - UUID → kotlin.uuid
  - Calendar/LocalDateTime → kotlinx.datetime
  - Currency → платформо-специфічні рішення
- [ ] Залишити тільки interfaces в commonMain
- [ ] Use cases - business logic (platform-independent)
- [ ] Core utilities (dispatcher, logger) → KMP versions

### Налаштування Dispatcher
```kotlin
// commonMain
expect class CoroutineDispatcherProvider {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
}

// androidMain
actual class CoroutineDispatcherProvider {
    actual val main = Dispatchers.Main
    actual val io = Dispatchers.IO
    actual val default = Dispatchers.Default
}

// iosMain
actual class CoroutineDispatcherProvider {
    actual val main = Dispatchers.Main
    actual val io = Dispatchers.Default  // iOS немає окремого IO
    actual val default = Dispatchers.Default
}
```

### iOS реалізації (Фаза 5-6)
Додати iOS-специфічний код для:
- [ ] Біометрична автентифікація (LocalAuthentication framework)
- [ ] Push notifications (APNs)
- [ ] File storage (NSFileManager)
- [ ] Permissions (iOS permissions system)

---

## 🔜 ФАЗА 6: Заміна mockDomain на реальний domain

### Мета
Поступово замінити всі mock repositories на реальні реалізації.

### Стратегія
**По одному use case/repository:**
1. [ ] Purchase domain:
   - Замінити MockPurchaseRepository на PurchaseRepositoryImpl
   - Протестувати CRUD операції
   - Перевірити на Android та iOS
   
2. [ ] Sku domain:
   - Замінити MockSkuRepository на SkuRepositoryImpl
   - Замінити MockSkuPhotoRepository на SkuPhotoRepositoryImpl
   - Тестування
   
3. [ ] Settings domain:
   - Замінити MockSettingRepository на SettingRepositoryImpl
   - Тестування
   
4. [ ] Інші use cases:
   - Auth
   - Storage
   - Notifications
   - Collections
   - тощо

### Koin модулі
```kotlin
// Було (Фаза 3):
val mockDataModule = module {
    single<PurchaseRepository> { MockPurchaseRepository() }
}

// Стане (Фаза 6):
val dataModule = module {
    single<PurchaseRepository> { 
        PurchaseRepositoryImpl(
            purchaseDao = get(),
            firestore = get()
        ) 
    }
}
```

### Видалення mockDomain
- [ ] Переконатися що всі repositories замінені
- [ ] Видалити залежність з shared.gradle.kts
- [ ] Видалити `include(":mockDomain")` з settings.gradle.kts
- [ ] Видалити директорію `/mockDomain`

---

## Технічний стек після міграції

### Common (Multiplatform)
- Kotlin 2.0+
- Compose Multiplatform
- Coroutines + Flow
- kotlinx-datetime
- kotlinx-serialization
- Koin DI
- Ktor Client
- Room KMP (або SQLDelight)
- Navigation Compose Multiplatform
- Coil/Kamel (image loading)

### Android-specific
- BiometricPrompt
- Firebase Android SDK
- Android storage APIs
- WorkManager (якщо потрібно)

### iOS-specific
- LocalAuthentication (biometric)
- Firebase iOS SDK (через CocoaPods)
- UserDefaults
- APNs

---

## Metrics & Timeline

### Поточний прогрес
- ✅ Фаза 1: 100% (mockDomain створено)
- 🔄 Фаза 2: 0% (UI міграція)
- ⏳ Фаза 3: 0% (інтеграція)
- ⏳ Фаза 4: 0% (data KMP)
- ⏳ Фаза 5: 0% (domain KMP)
- ⏳ Фаза 6: 0% (заміна mock)

### Оцінка часу
- Фаза 1: ✅ Завершено
- Фаза 2: ~2-3 тижні (найскладніша - багато UI коду)
- Фаза 3: ~3-5 днів (інтеграція та тестування)
- Фаза 4: ~1-2 тижні (data layer)
- Фаза 5: ~1 тиждень (domain вже підготовлений в mockDomain)
- Фаза 6: ~1 тиждень (поступова заміна)

**Загальна оцінка: 6-8 тижнів**

---

## Наступні кроки

### Негайні дії (Фаза 2)
1. ✅ Створити mockDomain модуль
2. **→ Оновити shared.gradle.kts з необхідними залежностями**
3. Створити базову структуру директорій в shared
4. Почати переносити Compose UI компоненти
5. Налаштувати Koin DI
6. Мігрувати ViewModels

### Команди для виконання
```bash
# 1. Синхронізація проекту
./gradlew clean build

# 2. Компіляція mockDomain
./gradlew :mockDomain:build

# 3. Тестування shared модуля
./gradlew :shared:testDebugUnitTest

# 4. Збірка iOS framework (пізніше)
./gradlew :shared:linkDebugFrameworkIosSimulatorArm64
```

---

## Ризики та мітігація

### Ризик 1: Складність міграції Dagger → Koin
**Мітігація:** Поступова міграція, використання wrapper pattern

### Ризик 2: Firebase iOS SDK інтеграція
**Мітігація:** Використання CocoaPods, expect/actual wrappers

### Ризик 3: Platform-specific UI behavior
**Мітігація:** Extensive testing на обох платформах, use of expect/actual

### Ризик 4: Performance на iOS
**Мітігація:** Профілювання, оптимізація після базової функціональності

---

## Документація

- [mockDomain README](/mockDomain/README.md)
- [KMP Best Practices](https://kotlinlang.org/docs/multiplatform.html)
- [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)
- [Room KMP](https://developer.android.com/kotlin/multiplatform/room)

---

**Останнє оновлення:** 29 листопада 2025
**Статус:** Фаза 1 завершена, перехід до Фази 2

