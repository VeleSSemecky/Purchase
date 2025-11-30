# MockDomain Module

## Призначення

Це тимчасовий Kotlin Multiplatform модуль, створений для міграції Android проекту на KMP архітектуру.

## Чому mockDomain?

Модуль створено для:

1. **Ізоляції UI від бізнес-логіки** - дозволяє розробляти та тестувати UI незалежно від data layer
2. **Поступової міграції** - можна мігрувати UI на мультиплатформу без очікування міграції data/domain
3. **Тестування навігації** - перевірити що весь UI flow працює коректно з mock даними
4. **Швидкої розробки** - не потрібно налаштовувати Firebase, Room, API для початкової розробки

## Структура

```
mockDomain/
├── src/
│   ├── commonMain/kotlin/
│   │   └── com/veles/purchase/domain/
│   │       ├── model/              # Domain models (з оригінального domain модуля)
│   │       ├── repository/         # Repository interfaces + Mock implementations
│   │       │   ├── purchase/       # MockPurchaseRepository
│   │       │   ├── sku/            # MockSkuRepository, MockSkuPhotoRepository
│   │       │   └── setting/        # MockSettingRepository
│   │       └── di/                 # MockDomainModule для DI
│   ├── androidMain/kotlin/         # Android-specific (якщо потрібно)
│   └── iosMain/kotlin/             # iOS-specific (якщо потрібно)
```

## Mock Repositories

### MockPurchaseRepository
- Повертає 5 mock покупок (молоко, хліб, книга, кава, телефон)
- Підтримує CRUD операції з in-memory storage
- Емулює затримки мережі (200-300ms)

### MockSkuRepository
- 3 mock SKU (молоко, хліб, кава)
- Підтримує статистику по місяцях

### MockSkuPhotoRepository
- Управління фото для SKU

### MockSettingRepository
- Налаштування вигляду карток покупок
- Зберігає стан в MutableStateFlow

## Використання

```kotlin
// В DI модулі вашого UI
val purchaseRepository = MockDomainModule.providePurchaseRepository()
val skuRepository = MockDomainModule.provideSkuRepository()

// Або інтеграція з Koin (Фаза 3)
module {
    single<PurchaseRepository> { MockPurchaseRepository() }
    single<SkuRepository> { MockSkuRepository() }
}
```

## Відмінності від оригінального domain модуля

1. **Java → Kotlin KMP**
   - `java.util.UUID` → `kotlin.uuid.Uuid`
   - `java.util.Calendar` → `kotlinx.datetime.Clock`
   - `java.time.LocalDateTime` → `kotlinx.datetime.LocalDateTime`
   - `java.util.Currency` → жорстко закодований "USD"/"UAH"

2. **Mock дані замість реальних**
   - Всі repositories повертають статичні дані
   - Емуляція затримок для реалістичності
   - In-memory storage замість БД

3. **Відсутні залежності**
   - Немає Android API
   - Немає Firebase
   - Немає Room
   - Тільки Kotlin stdlib + Coroutines

## План міграції

### ✅ Фаза 1: Створення mockDomain (ВИКОНАНО)
- Створено KMP модуль
- Скопійовано всі моделі з адаптацією для KMP
- Реалізовано mock repositories
- Створено DI модуль

### 🔄 Фаза 2: Міграція UI в shared (НАСТУПНА)
- Перенести presentation код в shared/commonMain
- Замінити Dagger на Koin
- Адаптувати Android-specific код

### 🔜 Фаза 3: Підключення mockDomain
- Додати залежність mockDomain → shared
- Інтегрувати через Koin
- Тестування на Android і iOS

### 🔜 Фаза 4-6: Реальна міграція
- Міграція data модуля
- Міграція domain модуля
- Заміна mockDomain на реальний domain
- **Видалення цього модуля**

## ⚠️ ВАЖЛИВО

**Цей модуль ТИМЧАСОВИЙ!** Він буде видалений після завершення міграції (Фаза 6).
Не додавайте сюди складну бізнес-логіку - це тільки для тестування UI!

