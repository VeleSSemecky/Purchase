# Quick Start: mockDomain Module

## 🚀 Швидкий старт для розробників

### 1. Перевірка модуля

```bash
# Компіляція mockDomain
./gradlew :mockDomain:build

# Тільки commonMain
./gradlew :mockDomain:compileKotlinMetadata

# Android target
./gradlew :mockDomain:compileDebugKotlinAndroid

# iOS targets
./gradlew :mockDomain:compileKotlinIosArm64
./gradlew :mockDomain:compileKotlinIosX64
./gradlew :mockDomain:compileKotlinIosSimulatorArm64
```

### 2. Використання в коді

#### Базове використання

```kotlin
import com.veles.purchase.domain.di.MockDomainModule
import com.veles.purchase.domain.repository.purchase.PurchaseRepository

// Отримання repository
val purchaseRepository: PurchaseRepository = 
    MockDomainModule.providePurchaseRepository()

// Робота з даними
suspend fun loadPurchases() {
    val purchases = purchaseRepository.getPurchaseFlow("collection_1")
        .collect { list ->
            println("Purchases: ${list.size}")
        }
}
```

#### З Coroutines

```kotlin
import kotlinx.coroutines.*

fun main() = runBlocking {
    val repo = MockDomainModule.providePurchaseRepository()
    
    // Get single purchase
    val purchase = repo.getPurchase("collection_1", "purchase_id")
    println("Purchase: ${purchase.text}")
    
    // Search
    val results = repo.getSearchPurchaseList("collection_1", "молоко")
    println("Found: ${results.size} items")
    
    // Add new
    repo.setPurchase(
        PurchaseModel(
            createId = createPrimaryIDKey(),
            text = "Нова покупка",
            count = "1",
            isChecked = false,
            price = "50.00",
            userList = emptyList(),
            listImage = emptyList(),
            purchaseCategoryModel = null
        ),
        collectionId = "collection_1"
    )
}
```

#### З Flow (Reactive)

```kotlin
import kotlinx.coroutines.flow.*

class PurchaseViewModel {
    private val repo = MockDomainModule.providePurchaseRepository()
    
    val purchases: Flow<List<PurchaseModel>> = 
        repo.getPurchaseFlow("collection_1")
    
    val checkedCount: Flow<Int> = purchases.map { list ->
        list.count { it.isChecked }
    }
    
    val totalPrice: Flow<Double> = purchases.map { list ->
        list.sumOf { it.price.toDoubleOrNull() ?: 0.0 }
    }
}
```

### 3. Всі доступні Repositories

#### PurchaseRepository
```kotlin
val repo = MockDomainModule.providePurchaseRepository()

// Методи:
suspend fun getPurchase(collectionId: String, purchaseId: String): PurchaseModel
suspend fun getSearchPurchaseList(collectionId: String, search: String): List<PurchaseModel>
fun getPurchaseFlow(collectionId: String): Flow<List<PurchaseModel>>
suspend fun deletePurchase(purchaseId: String, collectionId: String)
suspend fun setPurchase(purchaseModel: PurchaseModel, collectionId: String)
```

#### SkuRepository
```kotlin
val repo = MockDomainModule.provideSkuRepository()

// Методи:
suspend fun getSkuModel(skuId: String): SkuModel?
suspend fun getSkuEntityList(): List<SkuModel>
suspend fun insert(skuModel: SkuModel, skuPhotoModelList: List<SkuPhotoModel>)
suspend fun delete(id: String)
suspend fun getSkuSumMonthList(year: Int, month: Int): List<SkuSumMonthModel>
```

#### SkuPhotoRepository
```kotlin
val repo = MockDomainModule.provideSkuPhotoRepository()

// Методи:
suspend fun getSkuPhotoModelList(skuId: String): List<SkuPhotoModel>
suspend fun deletePhoto(skuPhotoId: String)
```

#### SettingRepository
```kotlin
val repo = MockDomainModule.provideSettingRepository()

// Методи:
fun getFlowSettingsPurchase(): Flow<PurchaseSetting>
suspend fun saveSettingsPurchase(purchaseSetting: PurchaseSetting)
```

### 4. Приклади роботи з моделями

#### PurchaseModel

```kotlin
import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.domain.model.purchase.PurchaseCategoryModel

// Створення нової покупки
val purchase = PurchaseModel(
    createId = createPrimaryIDKey(),
    text = "Молоко",
    count = "2",
    isChecked = false,
    price = "45.50",
    userList = listOf("user1", "user2"),
    listImage = emptyList(),
    purchaseCategoryModel = PurchaseCategoryModel(
        id = "cat_1",
        name = "Продукти"
    )
)

// Перевірка чи порожня
if (purchase.isEmpty()) {
    println("Empty purchase")
}

// Отримання локальних фото
val localPhotos = purchase.getLocalListPurchasePhotoModel()
```

#### SkuModel

```kotlin
import com.veles.purchase.domain.model.SkuModel
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

val sku = SkuModel(
    skuId = createPrimaryIDKey(),
    skuLocalData = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
    skuName = "Кава Lavazza",
    skuComment = "500г зернова",
    skuPrice = "380.00",
    skuCurrencyCode = "UAH"
)
```

#### PurchaseSetting

```kotlin
import com.veles.purchase.domain.model.setting.*

val settings = PurchaseSetting(
    sizeType = SizeType.DP,
    shapeType = ShapeType.ROUNDED,
    topStart = 16f,
    topEnd = 16f,
    bottomEnd = 16f,
    bottomStart = 16f,
    isImage = true,
    isSymmetry = true
)

// Збереження
val settingRepo = MockDomainModule.provideSettingRepository()
settingRepo.saveSettingsPurchase(settings)

// Читання
settingRepo.getFlowSettingsPurchase().collect { setting ->
    println("Shape: ${setting.shapeType}")
}
```

### 5. Testing Utilities

#### Створення тестових даних

```kotlin
import com.veles.purchase.domain.utill.*

// UUID
val id = createPrimaryIDKey()  // UUID uppercase

// Strings
val empty = emptyString()      // ""
val dash = dashString()        // "-"
val zero = zeroString()        // "0"
val zeroInt = zeroInt()        // 0

// Null safety
val value: String? = null
val safe = value.default("default")  // "default"
```

#### Використання готових mock даних

```kotlin
// PurchaseModel має готові константи
val testPurchase = PurchaseModel.TEST
val emptyPurchase = PurchaseModel.EMPTY

// PurchaseCategoryModel
val testCategory = PurchaseCategoryModel.TEST
val emptyCategory = PurchaseCategoryModel.EMPTY
```

### 6. Інтеграція з Koin (Phase 3)

```kotlin
// shared/src/commonMain/kotlin/di/MockDataModule.kt
import org.koin.dsl.module

val mockDataModule = module {
    // Repositories
    single<PurchaseRepository> { 
        MockDomainModule.providePurchaseRepository() 
    }
    single<SkuRepository> { 
        MockDomainModule.provideSkuRepository() 
    }
    single<SkuPhotoRepository> { 
        MockDomainModule.provideSkuPhotoRepository() 
    }
    single<SettingRepository> { 
        MockDomainModule.provideSettingRepository() 
    }
}

// App initialization
fun initKoin() {
    startKoin {
        modules(mockDataModule)
    }
}

// В ViewModel
class PurchaseViewModel(
    private val purchaseRepository: PurchaseRepository  // Auto-injected
) : ViewModel() {
    // Use repository
}
```

### 7. Migration Tips

#### Від Dagger до mockDomain

```kotlin
// БУЛО (Dagger):
@Inject lateinit var repository: PurchaseRepository

class MyViewModel @Inject constructor(
    private val repository: PurchaseRepository
) : ViewModel()

// СТАНЕ (mockDomain + Koin):
class MyViewModel(
    private val repository: PurchaseRepository  // Same interface!
) : ViewModel()

// Koin injection
val viewModel: MyViewModel by viewModel()
```

#### От Android-specific до KMP

```kotlin
// БУЛО (Android):
import java.util.UUID
val id = UUID.randomUUID().toString()

// СТАЛО (KMP):
import com.veles.purchase.domain.utill.createPrimaryIDKey
val id = createPrimaryIDKey()

// БУЛО (Android):
import java.util.Calendar
val timestamp = Calendar.getInstance().timeInMillis

// СТАЛО (KMP):
import kotlinx.datetime.Clock
val timestamp = Clock.System.now().toEpochMilliseconds()
```

### 8. Debugging

```kotlin
// Enable logging
val repo = MockDomainModule.providePurchaseRepository()

// Check mock data
val flow = repo.getPurchaseFlow("collection_1")
flow.collect { purchases ->
    println("=== Mock Purchases ===")
    purchases.forEach { purchase ->
        println("ID: ${purchase.createId}")
        println("Text: ${purchase.text}")
        println("Price: ${purchase.price}")
        println("Checked: ${purchase.isChecked}")
        println("---")
    }
}
```

### 9. Performance Notes

- **Network delays:** 200-300ms симульовані через `delay()`
- **In-memory:** Всі дані в RAM, немає персистенції
- **Thread-safe:** MutableStateFlow забезпечує потокобезпеку
- **Reactive:** Всі зміни відразу емітяться в Flow

### 10. Limitations

❌ **НЕ робити:**
- Не покладатися на персистенцію (дані зникають після рестарту)
- Не використовувати для production (тільки для розробки UI)
- Не додавати складну бізнес-логіку (це mock!)

✅ **РОБИТИ:**
- Використовувати для UI розробки
- Тестувати навігацію та flows
- Перевіряти edge cases з mock даними
- Готувати ViewModels для реальних repositories

---

## 📚 Додаткові ресурси

- [README.md](README.md) - Повна документація модуля
- [STRUCTURE.md](STRUCTURE.md) - Детальна структура файлів
- [/MIGRATION_PLAN.md](../MIGRATION_PLAN.md) - Загальний план міграції

---

## 🐛 Troubleshooting

**Problem:** Compilation error with UUID
```
Solution: Використовуйте kotlin.uuid.Uuid замість java.util.UUID
```

**Problem:** LocalDateTime not found
```
Solution: Додайте kotlinx-datetime залежність
```

**Problem:** Repository returns empty list
```
Solution: Mock дані прив'язані до collectionId, використовуйте будь-який ID
```

---

**Ready to use!** 🚀

