# 🔍 Phase 5 - Task 5.1: Architecture Analysis

**Date:** December 25, 2025  
**Status:** ✅ **COMPLETE**  
**Time Spent:** Analysis complete  

---

## 📊 Current Architecture Summary

### Modules Overview

The project currently has **4 main modules**:
1. **domain** - Business logic and repository interfaces (87 files)
2. **data** - Data layer with Room database (55 files)
3. **presentation** - UI layer (Android-specific, being replaced by shared)
4. **mockDomain** - Mock data provider (temporary, will be removed)

---

## 📦 Domain Module Analysis

### Location:
`domain/src/main/java/com/veles/purchase/domain/`

### Statistics:
- **Total Files:** 87 (Kotlin + Java)
- **Source Sets:** `main` (Android)
- **Package:** `com.veles.purchase.domain`

### Structure:
```
domain/
└── src/main/java/com/veles/purchase/domain/
    └── repository/
        ├── collection/
        ├── auth/
        ├── message/
        ├── user/
        ├── storage/
        ├── history/
        ├── purchase/
        ├── sku/
        └── setting/
```

### Repository Interfaces Found:
1. **CollectionRepository** - Collection management
2. **AuthRepository** - Authentication
3. **MessageRepository** - Messaging/Notifications
4. **UserRepository** - User management
5. **StorageRepository** - File storage
6. **HistoryRepository** - History tracking
7. **PurchaseRepository** - Purchase management
8. **SkuRepository** - SKU (Stock Keeping Unit) management
9. **SettingRepository** - App settings

### Android-Specific Dependencies (Need to Replace):
- ❌ Using `javax.inject` (Dagger) - Replace with Koin
- ⚠️ May have LiveData - Replace with Flow
- ⚠️ May have Android-specific types

---

## 💾 Data Module Analysis

### Location:
`data/src/main/java/com/veles/purchase/data/`

### Statistics:
- **Total Files:** 55 (Kotlin + Java)
- **Source Sets:** `main` (Android)
- **Package:** `com.veles.purchase.data`

### Structure:
```
data/
└── src/main/java/com/veles/purchase/data/
    ├── room/
    │   ├── dao/
    │   │   ├── PurchaseDAO
    │   │   ├── SkuDAO
    │   │   └── SkuPhotoDAO
    │   ├── table/
    │   │   ├── PurchaseTable
    │   │   ├── SkuEntity
    │   │   └── SkuPhotoEntity
    │   ├── migration/
    │   ├── util/
    │   │   ├── HistoryTypeConverter
    │   │   ├── LocalDateTimeConverter
    │   │   └── UriConverter
    │   └── AppDatabase.kt
    └── repository/
        ├── collection/
        ├── auth/
        ├── message/
        ├── user/
        ├── storage/
        ├── purchase/
        ├── sku/
        └── setting/
```

### Database: **Room** ✅

**AppDatabase.kt:**
```kotlin
@Database(
    entities = [
        PurchaseTable::class,
        SkuEntity::class,
        SkuPhotoEntity::class
    ],
    version = LAST_VERSION,
    exportSchema = false
)
@TypeConverters(
    LocalDateTimeConverter::class,
    UriConverter::class,
    HistoryTypeConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getPurchaseDAO(): PurchaseDAO
    abstract fun getSkuDAO(): SkuDAO
    abstract fun getSkuPhotoDAO(): SkuPhotoDAO
}
```

### DAOs Found:
1. **PurchaseDAO** - Purchase CRUD operations
2. **SkuDAO** - SKU CRUD operations
3. **SkuPhotoDAO** - SKU photo management

### Entities Found:
1. **PurchaseTable** - Purchase database entity
2. **SkuEntity** - SKU database entity
3. **SkuPhotoEntity** - SKU photo database entity

### Type Converters:
1. **LocalDateTimeConverter** - Convert LocalDateTime ↔ Long
2. **UriConverter** - Convert Uri ↔ String
3. **HistoryTypeConverter** - Convert history types

### Dependencies (from data.gradle.kts):
- ✅ **Room** (`room-runtime`, `room-ktx`, `room-compiler`)
- ❌ **Retrofit** (Android-specific) - Replace with Ktor
- ❌ **Gson** - Replace with kotlinx.serialization
- ❌ **Dagger** (`javax.inject`) - Replace with Koin
- ❌ **Firebase** (partially Android-specific) - Create expect/actual
- ❌ **Coroutines** (play-services specific) - Use core only
- ⚠️ **Flipper** (debugging) - Keep for Android, skip iOS

---

## 🎯 Migration Strategy

### Decision: **Room KMP** ✅

**Rationale:**
- ✅ Already using Room in Android
- ✅ Minimal code changes needed
- ✅ Official Google KMP support (Room 2.6.1+)
- ✅ Familiar API for the team
- ✅ Good iOS support in Room KMP

### Migration Approach: **3-Phase**

#### Phase A: Domain Layer (Task 5.3)
**Time:** 4-6 hours

**Strategy:**
1. Create `shared/src/commonMain/kotlin/com/veles/purchase/domain/`
2. Copy all repository interfaces
3. Copy all domain models (if any)
4. Replace `javax.inject` with Koin annotations
5. Replace LiveData with Flow
6. Use kotlinx.datetime instead of java.time
7. Remove Android-specific types

**Challenges:**
- ⚠️ May need to refactor some repository methods
- ⚠️ Need to handle Parcelable (if used)

---

#### Phase B: Data Layer - Database (Task 5.4 Part 1)
**Time:** 6-8 hours

**Strategy:**
1. Create `shared/src/commonMain/kotlin/com/veles/purchase/data/`
2. Migrate AppDatabase to KMP Room
3. Migrate all DAOs (keep Room annotations)
4. Migrate all entities
5. Migrate type converters
6. Create platform-specific database builders (expect/actual)

**Database Builder Pattern:**
```kotlin
// commonMain
expect class DatabaseBuilder {
    fun build(): AppDatabase
}

// androidMain
actual class DatabaseBuilder(private val context: Context) {
    actual fun build(): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "purchase_db"
        ).build()
    }
}

// iosMain
actual class DatabaseBuilder {
    actual fun build(): AppDatabase {
        val dbFile = NSHomeDirectory() + "/purchase_db"
        return Room.databaseBuilder<AppDatabase>(
            name = dbFile
        ).build()
    }
}
```

**Challenges:**
- ⚠️ LocalDateTimeConverter needs kotlinx.datetime
- ⚠️ UriConverter is Android-specific (need platform solution)
- ⚠️ Database file paths differ by platform

---

#### Phase C: Data Layer - Network & Repositories (Task 5.4 Part 2)
**Time:** 4-6 hours

**Strategy:**
1. Replace Retrofit with Ktor Client
2. Replace Gson with kotlinx.serialization
3. Migrate repository implementations
4. Create Firebase expect/actual wrappers
5. Handle file storage (expect/actual)

**Network Setup:**
```kotlin
// commonMain
class ApiClient {
    val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }
}

// Platform-specific engines:
// androidMain: OkHttp
// iosMain: Darwin
```

**Challenges:**
- 🔴 Firebase needs expect/actual for auth, messaging, storage
- 🔴 File storage is platform-specific
- ⚠️ Retrofit API calls need to be rewritten for Ktor

---

## 🔧 Technical Challenges & Solutions

### 1. Date/Time Handling
**Challenge:** `java.time.LocalDateTime` is Android-specific  
**Solution:** Use `kotlinx.datetime.LocalDateTime`

```kotlin
// Before (Android)
import java.time.LocalDateTime

// After (KMP)
import kotlinx.datetime.LocalDateTime
```

### 2. URI Handling
**Challenge:** `android.net.Uri` is Android-specific  
**Solution:** Use String or create expect/actual wrapper

```kotlin
// Option 1: Use String everywhere
typealias UriString = String

// Option 2: expect/actual
expect class PlatformUri
actual typealias PlatformUri = android.net.Uri // Android
actual typealias PlatformUri = String // iOS
```

### 3. Dependency Injection
**Challenge:** Using Dagger (`javax.inject`)  
**Solution:** Already migrated to Koin in shared module ✅

### 4. Database Type Converters
**Challenge:** TypeConverters use Android types  
**Solution:** Rewrite with KMP types

```kotlin
// Before
class LocalDateTimeConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDateTime? {
        return value?.let { 
            LocalDateTime.ofEpochSecond(it, 0, ZoneOffset.UTC) 
        }
    }
}

// After (KMP)
import kotlinx.datetime.*

class LocalDateTimeConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDateTime? {
        return value?.let { 
            Instant.fromEpochSeconds(it).toLocalDateTime(TimeZone.UTC) 
        }
    }
}
```

### 5. Firebase Integration
**Challenge:** Firebase SDK is partially platform-specific  
**Solution:** Create expect/actual wrappers

```kotlin
// commonMain
expect class FirebaseAuth {
    fun signIn(email: String, password: String): Flow<Result<User>>
    fun signOut()
}

// androidMain
actual class FirebaseAuth {
    private val auth = com.google.firebase.auth.FirebaseAuth.getInstance()
    // ... implementation
}

// iosMain
actual class FirebaseAuth {
    // Use Firebase iOS SDK
    // ... implementation
}
```

---

## 📋 Migration Checklist

### Files to Migrate:

#### Domain Module (87 files):
- [ ] Repository interfaces (9 repositories)
- [ ] Domain models (if any)
- [ ] Use cases (if any)
- [ ] Value objects (if any)

#### Data Module (55 files):
- [ ] AppDatabase.kt
- [ ] DAOs (3 DAOs: Purchase, Sku, SkuPhoto)
- [ ] Entities (3 entities)
- [ ] Type Converters (3 converters)
- [ ] Repository implementations (9+ repos)
- [ ] Network services (Retrofit → Ktor)
- [ ] Firebase wrappers
- [ ] Storage handlers

### Dependencies to Add (shared/build.gradle.kts):

```kotlin
commonMain.dependencies {
    // Room KMP
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.sqlite:sqlite-bundled:2.5.0-alpha01")
    
    // Ktor (Network)
    implementation("io.ktor:ktor-client-core:2.3.7")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.7")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.7")
    
    // Date/Time
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")
    
    // Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
}

androidMain.dependencies {
    // Ktor Android Engine
    implementation("io.ktor:ktor-client-okhttp:2.3.7")
}

iosMain.dependencies {
    // Ktor iOS Engine
    implementation("io.ktor:ktor-client-darwin:2.3.7")
}
```

### Plugins to Add:

```kotlin
plugins {
    // ... existing plugins
    id("com.google.devtools.ksp") version "2.0.21-1.0.27"
    id("androidx.room") version "2.6.1"
}

room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    add("kspCommonMainMetadata", "androidx.room:room-compiler:2.6.1")
    add("kspAndroid", "androidx.room:room-compiler:2.6.1")
    add("kspIosSimulatorArm64", "androidx.room:room-compiler:2.6.1")
    add("kspIosArm64", "androidx.room:room-compiler:2.6.1")
}
```

---

## ⏱️ Time Estimates

### Domain Migration (Task 5.3):
- Repository interfaces: 2-3 hours
- Domain models: 1-2 hours
- Testing & fixes: 1 hour
**Total: 4-6 hours**

### Data Migration (Task 5.4):
- Database setup: 2-3 hours
- DAOs & Entities: 2-3 hours
- Type Converters: 1-2 hours
- Repository implementations: 3-4 hours
- Network layer (Ktor): 2-3 hours
- Firebase wrappers: 2-3 hours
**Total: 12-18 hours**

### Integration (Tasks 5.6-5.9):
- DI updates: 2-3 hours
- Remove mockDomain: 1 hour
- Testing: 4-6 hours
**Total: 7-10 hours**

**Grand Total: 23-34 hours** (3-4 days of work)

---

## 🎯 Next Steps

### Immediate (Task 5.2): ✅ **Database Choice Made**
**Decision:** Room KMP  
**Rationale:** Already using Room, minimal migration effort

### Next (Task 5.3): **Migrate Domain Module**
**Start with:**
1. Create directory structure in `shared/src/commonMain/`
2. Copy repository interfaces
3. Fix imports and types
4. Build and test

---

## ✅ Analysis Complete

**Status:** Architecture fully analyzed ✅  
**Database Solution:** Room KMP selected ✅  
**Migration Strategy:** Defined ✅  
**Time Estimate:** 23-34 hours (3-4 days) ✅

**Ready to proceed to Task 5.3: Domain Migration! 🚀**

---

_Analysis Date: December 25, 2025_  
_Analyzed By: GitHub Copilot_  
_Files Counted: 142 (87 domain + 55 data)_  
_Repositories: 9 (Collection, Auth, Message, User, Storage, History, Purchase, Sku, Setting)_

