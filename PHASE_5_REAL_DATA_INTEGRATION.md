# 🔄 PHASE 5: Real Data Integration

**Created:** December 25, 2025  
**Status:** 🚀 **READY TO START**  
**Priority:** 🔴 **CRITICAL - Next Major Phase**  

---

## 📊 Current State

### ✅ Completed (Phases 1-3)
- ✅ **Phase 1:** mockDomain module with mock data (100%)
- ✅ **Phase 2:** All 12 screens migrated to KMP shared module (100%)
- ✅ **Phase 3:** iOS build configuration complete (100%)
- ⏭️ **Phase 4:** Testing (Skipped - moving to real data)

### 🎯 Phase 5 Goal
**Migrate data and domain layers to KMP and replace mockDomain with real repositories.**

---

## 📋 Phase 5 Overview

### Objectives
1. 🔄 **Migrate domain module** to Kotlin Multiplatform
2. 🔄 **Migrate data module** to Kotlin Multiplatform
3. 💾 **Setup database layer** (Room KMP or SQLDelight)
4. 🌐 **Setup network layer** (Ktor for API calls)
5. 🔌 **Replace mockDomain** with real repositories
6. 🧪 **Integration testing** with real data
7. 📱 **Verify both platforms** work with real data

### Success Criteria
- [ ] Domain layer in shared/commonMain
- [ ] Data layer in shared/commonMain
- [ ] Database working on both platforms
- [ ] Network calls working on both platforms
- [ ] All ViewModels using real repositories
- [ ] mockDomain removed from dependencies
- [ ] App works with real data on Android & iOS

---

## 🗺️ Phase 5 Tasks Breakdown

### Task 5.1: Analyze Current Architecture 📐
**Estimated Time:** 2-3 hours  
**Priority:** 🔴 Critical  

#### Steps:
1. **Review existing modules**
   ```bash
   # Examine current structure
   ls -la domain/
   ls -la data/
   ls -la mockDomain/
   ```

2. **Analyze dependencies**
   - Review `domain/build.gradle.kts`
   - Review `data/build.gradle.kts`
   - Map dependency graph
   - Identify Android-specific dependencies

3. **Create migration plan**
   - List all domain entities
   - List all repositories
   - List all use cases
   - List Android-specific code that needs expect/actual

4. **Document current architecture**
   - Create architecture diagram
   - Document data flow
   - Identify KMP challenges

#### Deliverables:
- [ ] `PHASE_5_ARCHITECTURE_ANALYSIS.md` created
- [ ] Current structure documented
- [ ] KMP migration challenges identified
- [ ] Task breakdown refined

---

### Task 5.2: Choose Database Solution 💾
**Estimated Time:** 2-4 hours  
**Priority:** 🔴 Critical  

#### Options:

##### Option A: Room KMP (Recommended if using Room)
**Pros:**
- Already using Room in Android
- Official Google support
- Familiar API
- Migration path from Android Room

**Cons:**
- Relatively new for KMP
- May have iOS limitations
- Requires Kotlin 2.0+

**Dependencies:**
```kotlin
// shared/build.gradle.kts
commonMain.dependencies {
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.sqlite:sqlite-bundled:2.5.0-alpha01")
}
```

##### Option B: SQLDelight (Popular KMP choice)
**Pros:**
- Mature KMP support
- Type-safe SQL queries
- Great iOS support
- Good documentation

**Cons:**
- Different API from Room
- Need to rewrite queries
- Learning curve

**Dependencies:**
```kotlin
// shared/build.gradle.kts
commonMain.dependencies {
    implementation("app.cash.sqldelight:runtime:2.0.1")
    implementation("app.cash.sqldelight:coroutines-extensions:2.0.1")
}
```

#### Decision Matrix:
| Criteria | Room KMP | SQLDelight |
|----------|----------|------------|
| Existing code | ✅ Keep Room | ❌ Rewrite |
| KMP maturity | ⚠️ Newer | ✅ Mature |
| iOS support | ⚠️ Good | ✅ Excellent |
| Learning curve | ✅ Low | ⚠️ Medium |
| Type safety | ✅ Good | ✅ Excellent |

#### Recommended: **Room KMP** (if currently using Room)

#### Steps:
1. Analyze current database code
2. Choose database solution
3. Add dependencies to shared module
4. Create database migration plan

#### Deliverables:
- [ ] Database solution chosen
- [ ] Dependencies added
- [ ] Migration plan created

---

### Task 5.3: Migrate Domain Module 📦
**Estimated Time:** 4-6 hours  
**Priority:** 🔴 Critical  

#### Steps:

1. **Setup shared/domain structure**
   ```
   shared/src/commonMain/kotlin/com/veles/purchase/
   ├── domain/
   │   ├── model/           # Domain entities
   │   ├── repository/      # Repository interfaces
   │   └── usecase/         # Use cases (if any)
   ```

2. **Move domain models**
   - Copy all entities from `domain/src/main/kotlin/`
   - Remove Android-specific annotations
   - Add `@Serializable` for KMP
   - Make all models `data class` compatible

3. **Move repository interfaces**
   - Copy all repository interfaces
   - Ensure they're platform-independent
   - Use Kotlin Flow instead of LiveData
   - Remove Android-specific types

4. **Handle special cases**
   - Replace Android Parcelable with expect/actual
   - Replace Context dependencies
   - Use kotlinx.datetime instead of java.time

#### Example Migration:

**Before (Android):**
```kotlin
// domain/src/main/kotlin/com/veles/purchase/domain/model/Purchase.kt
@Entity
data class Purchase(
    @PrimaryKey val id: Long,
    val name: String,
    val price: Double,
    val date: LocalDateTime // java.time
)
```

**After (KMP):**
```kotlin
// shared/src/commonMain/kotlin/.../domain/model/Purchase.kt
import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDateTime

@Serializable
data class Purchase(
    val id: Long,
    val name: String,
    val price: Double,
    val date: LocalDateTime // kotlinx.datetime
)
```

#### Deliverables:
- [ ] All domain models in shared/commonMain
- [ ] All repository interfaces in shared/commonMain
- [ ] No Android-specific code in domain
- [ ] Domain module can be removed
- [ ] `PHASE_5_DOMAIN_MIGRATION_COMPLETE.md`

---

### Task 5.4: Migrate Data Module 🔄
**Estimated Time:** 8-12 hours  
**Priority:** 🔴 Critical  

#### Steps:

1. **Setup shared/data structure**
   ```
   shared/src/commonMain/kotlin/com/veles/purchase/
   ├── data/
   │   ├── database/
   │   │   ├── dao/         # DAOs
   │   │   ├── entity/      # Database entities
   │   │   └── PurchaseDatabase.kt
   │   ├── repository/      # Repository implementations
   │   └── mapper/          # Entity ↔ Model mappers
   ```

2. **Migrate Database Layer**

   **If using Room KMP:**
   ```kotlin
   // shared/src/commonMain/kotlin/.../data/database/PurchaseDatabase.kt
   import androidx.room.Database
   import androidx.room.RoomDatabase
   
   @Database(
       entities = [
           PurchaseEntity::class,
           CollectionEntity::class,
           SkuEntity::class,
           // ... all entities
       ],
       version = 1
   )
   abstract class PurchaseDatabase : RoomDatabase() {
       abstract fun purchaseDao(): PurchaseDao
       abstract fun collectionDao(): CollectionDao
       abstract fun skuDao(): SkuDao
       // ... all DAOs
   }
   ```

   **Database builder (expect/actual):**
   ```kotlin
   // shared/src/commonMain/kotlin/.../data/database/DatabaseBuilder.kt
   expect class DatabaseBuilder {
       fun build(): PurchaseDatabase
   }
   
   // shared/src/androidMain/kotlin/.../data/database/DatabaseBuilder.kt
   import android.content.Context
   import androidx.room.Room
   
   actual class DatabaseBuilder(private val context: Context) {
       actual fun build(): PurchaseDatabase {
           return Room.databaseBuilder(
               context,
               PurchaseDatabase::class.java,
               "purchase.db"
           ).build()
       }
   }
   
   // shared/src/iosMain/kotlin/.../data/database/DatabaseBuilder.kt
   import androidx.room.Room
   import platform.Foundation.NSHomeDirectory
   
   actual class DatabaseBuilder {
       actual fun build(): PurchaseDatabase {
           val dbFile = NSHomeDirectory() + "/purchase.db"
           return Room.databaseBuilder<PurchaseDatabase>(
               name = dbFile
           ).build()
       }
   }
   ```

3. **Migrate DAOs**
   - Move all DAO interfaces
   - Keep Room annotations (compatible with KMP)
   - Replace LiveData with Flow
   - Ensure all return types are KMP-compatible

4. **Migrate Entities**
   - Move all database entities
   - Keep Room annotations
   - Remove Android-specific types
   - Use kotlinx.datetime

5. **Migrate Repository Implementations**
   - Move repository implementations
   - Inject DAOs via Koin
   - Use coroutines and Flow
   - Map entities to domain models

#### Example:

```kotlin
// shared/src/commonMain/kotlin/.../data/repository/PurchaseRepositoryImpl.kt
class PurchaseRepositoryImpl(
    private val purchaseDao: PurchaseDao,
    private val mapper: PurchaseMapper
) : PurchaseRepository {
    
    override fun getAllPurchases(): Flow<List<Purchase>> {
        return purchaseDao.getAllPurchases()
            .map { entities -> entities.map { mapper.toDomain(it) } }
    }
    
    override suspend fun insertPurchase(purchase: Purchase) {
        val entity = mapper.toEntity(purchase)
        purchaseDao.insert(entity)
    }
    
    // ... other methods
}
```

#### Deliverables:
- [ ] Database in shared/commonMain
- [ ] All DAOs migrated
- [ ] All entities migrated
- [ ] All repository implementations migrated
- [ ] Database builders (expect/actual) created
- [ ] Data module can be removed
- [ ] `PHASE_5_DATA_MIGRATION_COMPLETE.md`

---

### Task 5.5: Setup Network Layer 🌐
**Estimated Time:** 4-6 hours  
**Priority:** 🟡 High (if using APIs)  

#### Steps:

1. **Add Ktor dependencies**
   ```kotlin
   // shared/build.gradle.kts
   commonMain.dependencies {
       implementation("io.ktor:ktor-client-core:2.3.7")
       implementation("io.ktor:ktor-client-content-negotiation:2.3.7")
       implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.7")
   }
   
   androidMain.dependencies {
       implementation("io.ktor:ktor-client-okhttp:2.3.7")
   }
   
   iosMain.dependencies {
       implementation("io.ktor:ktor-client-darwin:2.3.7")
   }
   ```

2. **Create API client**
   ```kotlin
   // shared/src/commonMain/kotlin/.../data/network/ApiClient.kt
   import io.ktor.client.*
   import io.ktor.client.plugins.contentnegotiation.*
   import io.ktor.serialization.kotlinx.json.*
   import kotlinx.serialization.json.Json
   
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
   ```

3. **Create API services**
   ```kotlin
   // Example: PurchaseApiService.kt
   class PurchaseApiService(private val client: HttpClient) {
       suspend fun syncPurchases(): List<PurchaseDto> {
           return client.get("$BASE_URL/purchases").body()
       }
       
       // ... other API calls
   }
   ```

4. **Setup network repository**
   - Create network repository implementations
   - Handle offline/online sync
   - Cache management

#### Deliverables:
- [ ] Ktor client configured
- [ ] API services created
- [ ] Network repositories implemented
- [ ] Sync logic added
- [ ] `PHASE_5_NETWORK_SETUP_COMPLETE.md`

---

### Task 5.6: Update DI Configuration 🔌
**Estimated Time:** 2-3 hours  
**Priority:** 🔴 Critical  

#### Steps:

1. **Create data module for Koin**
   ```kotlin
   // shared/src/commonMain/kotlin/.../di/DataModule.kt
   val dataModule = module {
       // Database
       single { DatabaseBuilder().build() }
       
       // DAOs
       single { get<PurchaseDatabase>().purchaseDao() }
       single { get<PurchaseDatabase>().collectionDao() }
       single { get<PurchaseDatabase>().skuDao() }
       // ... all DAOs
       
       // Repositories
       single<PurchaseRepository> { 
           PurchaseRepositoryImpl(get(), get()) 
       }
       single<CollectionRepository> { 
           CollectionRepositoryImpl(get(), get()) 
       }
       // ... all repositories
       
       // Mappers
       single { PurchaseMapper() }
       single { CollectionMapper() }
       // ... all mappers
   }
   ```

2. **Create network module (if needed)**
   ```kotlin
   // shared/src/commonMain/kotlin/.../di/NetworkModule.kt
   val networkModule = module {
       single { ApiClient().client }
       single { PurchaseApiService(get()) }
       // ... all API services
   }
   ```

3. **Update AppModule**
   ```kotlin
   // shared/src/commonMain/kotlin/.../di/AppModule.kt
   val appModules = listOf(
       dataModule,      // Real data (replace mockDataModule)
       networkModule,   // If using network
       viewModelModule
   )
   ```

4. **Update platform initialization**

   **Android:**
   ```kotlin
   // androidApp/src/main/kotlin/.../MainActivity.kt
   class MainActivity : ComponentActivity() {
       override fun onCreate(savedInstanceState: Bundle?) {
           super.onCreate(savedInstanceState)
           
           startKoin {
               androidContext(this@MainActivity)
               modules(appModules)
           }
           
           // ...
       }
   }
   ```

   **iOS:**
   ```kotlin
   // shared/src/iosMain/kotlin/.../di/KoinIOS.kt
   fun initKoin() {
       startKoin {
           modules(appModules)
       }
   }
   ```

#### Deliverables:
- [ ] dataModule created
- [ ] networkModule created (if needed)
- [ ] mockDataModule removed
- [ ] Platform initialization updated
- [ ] All repositories injected correctly

---

### Task 5.7: Remove mockDomain Dependency 🗑️
**Estimated Time:** 1 hour  
**Priority:** 🟡 High  

#### Steps:

1. **Remove from build.gradle.kts**
   ```kotlin
   // shared/build.gradle.kts
   // Remove this line:
   // implementation(project(":mockDomain"))
   ```

2. **Remove MockDataModule**
   ```kotlin
   // Delete: shared/src/commonMain/kotlin/.../di/MockDataModule.kt
   ```

3. **Verify no imports from mockDomain**
   ```bash
   grep -r "com.veles.purchase.mockdomain" shared/src/
   # Should return nothing
   ```

4. **Update documentation**
   - Update README
   - Update architecture docs
   - Mark mockDomain as deprecated

#### Deliverables:
- [ ] mockDomain dependency removed
- [ ] MockDataModule deleted
- [ ] No mockDomain imports
- [ ] Documentation updated

---

### Task 5.8: Database Migration & Initial Data 📊
**Estimated Time:** 2-4 hours  
**Priority:** 🟡 High  

#### Steps:

1. **Create database migrations**
   ```kotlin
   // If migrating from existing Android database
   val MIGRATION_1_2 = object : Migration(1, 2) {
       override fun migrate(database: SupportSQLiteDatabase) {
           // Migration logic
       }
   }
   ```

2. **Add initial data seeding**
   ```kotlin
   // shared/src/commonMain/kotlin/.../data/database/DatabaseSeeder.kt
   class DatabaseSeeder(private val database: PurchaseDatabase) {
       suspend fun seedInitialData() {
           // Add default categories
           // Add sample data (optional)
       }
   }
   ```

3. **Handle first-time setup**
   - Detect first app launch
   - Seed initial data
   - Set version flags

#### Deliverables:
- [ ] Database migrations created
- [ ] Initial data seeder implemented
- [ ] First-time setup handled

---

### Task 5.9: Integration Testing 🧪
**Estimated Time:** 4-6 hours  
**Priority:** 🔴 Critical  

#### Steps:

1. **Test Android**
   - Uninstall old app (clear data)
   - Install new app with real data
   - Test all 12 screens
   - Verify database operations
   - Test CRUD operations

2. **Test iOS**
   - Delete app from simulator
   - Install new app
   - Test all 12 screens
   - Verify database operations
   - Test CRUD operations

3. **Test data persistence**
   - Create data
   - Close app
   - Reopen app
   - Verify data persists

4. **Test offline functionality**
   - Turn off network
   - Verify app works
   - Test data operations

#### Test Cases:
- [ ] Create purchase
- [ ] Edit purchase
- [ ] Delete purchase
- [ ] Create collection
- [ ] Edit collection
- [ ] Delete collection
- [ ] Add SKU
- [ ] Edit SKU
- [ ] Delete SKU
- [ ] Categories CRUD
- [ ] History logging
- [ ] Statistics calculation

#### Deliverables:
- [ ] All test cases pass on Android
- [ ] All test cases pass on iOS
- [ ] Data persistence verified
- [ ] Offline functionality works
- [ ] `PHASE_5_TESTING_RESULTS.md`

---

### Task 5.10: Performance Optimization ⚡
**Estimated Time:** 2-4 hours  
**Priority:** 🟢 Medium  

#### Steps:

1. **Database optimization**
   - Add indexes
   - Optimize queries
   - Enable WAL mode (Write-Ahead Logging)

2. **Memory optimization**
   - Profile memory usage
   - Fix memory leaks
   - Optimize large lists

3. **Launch time optimization**
   - Lazy initialization
   - Background database operations
   - Optimize Koin setup

4. **Network optimization** (if applicable)
   - Implement caching
   - Batch requests
   - Compression

#### Deliverables:
- [ ] Database optimized
- [ ] Memory usage acceptable
- [ ] Launch time < 3 seconds
- [ ] Network efficient
- [ ] Performance metrics documented

---

### Task 5.11: Final Documentation 📚
**Estimated Time:** 2-3 hours  
**Priority:** 🟡 High  

#### Documents to Create:

1. **PHASE_5_COMPLETE_SUMMARY.md**
   - What was migrated
   - Architecture changes
   - Testing results
   - Performance metrics
   - Known issues

2. **ARCHITECTURE.md** (Update)
   - New architecture diagram
   - Data flow documentation
   - Module structure
   - Database schema

3. **MIGRATION_GUIDE.md**
   - How to migrate user data
   - Database version handling
   - Rollback procedure

#### Deliverables:
- [ ] Complete summary created
- [ ] Architecture documented
- [ ] Migration guide written
- [ ] README updated

---

## 📊 Phase 5 Timeline

### Week 1: Analysis & Setup (Days 1-3)
- **Day 1:** Task 5.1 - Architecture analysis
- **Day 2:** Task 5.2 - Choose database solution
- **Day 3:** Task 5.3 - Migrate domain module

### Week 2: Data Layer (Days 4-8)
- **Day 4-5:** Task 5.4 - Migrate data module
- **Day 6:** Task 5.5 - Setup network layer
- **Day 7:** Task 5.6 - Update DI
- **Day 8:** Task 5.7 - Remove mockDomain

### Week 3: Testing & Polish (Days 9-12)
- **Day 9:** Task 5.8 - Database migration
- **Day 10-11:** Task 5.9 - Integration testing
- **Day 12:** Task 5.10 - Performance optimization
- **Day 13:** Task 5.11 - Final documentation

**Total Estimated Time:** 2-3 weeks (40-60 hours)

---

## 🎯 Success Criteria

### Must Have (MVP):
- [ ] ✅ Domain layer in KMP
- [ ] ✅ Data layer in KMP
- [ ] ✅ Database working on both platforms
- [ ] ✅ All ViewModels using real data
- [ ] ✅ mockDomain removed
- [ ] ✅ App works on Android
- [ ] ✅ App works on iOS

### Should Have:
- [ ] 🎯 Network layer configured
- [ ] 🎯 Offline support
- [ ] 🎯 Data sync logic
- [ ] 🎯 Good performance
- [ ] 🎯 No critical bugs

### Nice to Have:
- [ ] 🌟 Optimized queries
- [ ] 🌟 Advanced caching
- [ ] 🌟 Background sync
- [ ] 🌟 Excellent performance

---

## 🚀 Getting Started

### Step 1: Start with Architecture Analysis
```bash
cd /Users/yuriimelnyk/StudioProjects/Purchase

# Examine current structure
ls -la domain/src/main/kotlin/
ls -la data/src/main/kotlin/

# Create analysis document
touch PHASE_5_ARCHITECTURE_ANALYSIS.md
```

### Step 2: Review Current Code
- Understand domain layer structure
- Understand data layer structure
- Identify Android-specific dependencies
- Plan migration approach

### Step 3: Choose Database Solution
- Evaluate Room KMP vs SQLDelight
- Consider existing database
- Choose based on project needs

---

## 📚 Reference Documentation

### KMP Database:
- Room KMP: https://developer.android.com/kotlin/multiplatform/room
- SQLDelight: https://cashapp.github.io/sqldelight/

### KMP Network:
- Ktor Client: https://ktor.io/docs/getting-started-ktor-client.html

### Dependencies:
- kotlinx.datetime: https://github.com/Kotlin/kotlinx-datetime
- kotlinx.serialization: https://github.com/Kotlin/kotlinx.serialization

### Examples:
- KMP Sample Apps: https://github.com/JetBrains/compose-multiplatform-ios-android-template

---

## ⚠️ Common Challenges

### Database Migration:
- **Challenge:** Room KMP is relatively new
- **Solution:** Follow official migration guide, test thoroughly

### Data Types:
- **Challenge:** Date/Time types differ (java.time vs kotlinx.datetime)
- **Solution:** Use kotlinx.datetime everywhere, create mappers

### Platform-Specific Code:
- **Challenge:** File paths, database locations differ
- **Solution:** Use expect/actual for platform-specific code

### Performance:
- **Challenge:** Database operations might be slower on iOS
- **Solution:** Optimize queries, use indexes, profile both platforms

---

## 💡 Best Practices

### Code Organization:
- ✅ Keep domain layer pure Kotlin
- ✅ Use expect/actual sparingly
- ✅ Separate platform code cleanly
- ✅ Use dependency injection

### Database:
- ✅ Add indexes for frequently queried fields
- ✅ Use transactions for batch operations
- ✅ Handle migrations properly
- ✅ Test on both platforms

### Testing:
- ✅ Test each layer independently
- ✅ Integration tests for full flow
- ✅ Test on real devices (not just emulators)
- ✅ Test data persistence

### Performance:
- ✅ Profile on both platforms
- ✅ Optimize bottlenecks
- ✅ Use lazy initialization
- ✅ Background operations for heavy tasks

---

## 📞 Quick Commands

### Analyze Structure:
```bash
# List domain files
find domain/src/main/kotlin -name "*.kt"

# List data files
find data/src/main/kotlin -name "*.kt"

# Count files to migrate
find domain/src/main/kotlin -name "*.kt" | wc -l
find data/src/main/kotlin -name "*.kt" | wc -l
```

### Add Dependencies:
```bash
# Edit shared build file
open shared/build.gradle.kts
```

### Build & Test:
```bash
# Clean build
./gradlew clean

# Build Android
./gradlew :androidApp:assembleDebug

# Build iOS framework
./gradlew :shared:linkDebugFrameworkIosSimulatorArm64

# Run tests
./gradlew test
```

---

## ✅ Phase 5 Completion Checklist

### Code Migration:
- [ ] Domain module migrated to shared
- [ ] Data module migrated to shared
- [ ] Database working on both platforms
- [ ] Network layer setup (if needed)
- [ ] All repositories implemented
- [ ] mockDomain dependency removed

### Testing:
- [ ] All 12 screens work with real data
- [ ] CRUD operations work
- [ ] Data persists correctly
- [ ] Offline functionality works
- [ ] Performance acceptable

### Documentation:
- [ ] Architecture analysis complete
- [ ] Migration decisions documented
- [ ] Complete summary created
- [ ] Architecture diagram updated
- [ ] README updated

### Quality:
- [ ] No critical bugs
- [ ] No data loss
- [ ] Good performance
- [ ] Code reviewed
- [ ] Ready for production

---

## 🎉 After Phase 5

You'll have:
- ✅ Full KMP architecture (domain + data + UI)
- ✅ Real data instead of mocks
- ✅ Database working on both platforms
- ✅ Production-ready codebase
- ✅ Single source of truth for business logic

### Next: Phase 6 - Production Readiness
- Polish UI/UX
- Security audit
- Performance optimization
- App store preparation
- Beta testing
- Release!

---

**Status:** 📋 **PLANNING COMPLETE** - Ready to start!  
**Created by:** GitHub Copilot  
**Date:** December 25, 2025  
**Next Action:** Start Task 5.1 - Architecture Analysis

**Let's build the real data layer! 💪🔥**

