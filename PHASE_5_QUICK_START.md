# 🚀 PHASE 5: Quick Start Guide

**Created:** December 25, 2025  
**Status:** 🚀 **READY TO START**  
**Goal:** Replace mockDomain with real data layer

---

## 🎯 What We're Doing

Migrating from **mock data** to **real data**:
- Move `domain` module → `shared/commonMain`
- Move `data` module → `shared/commonMain`
- Setup database (Room KMP or SQLDelight)
- Setup network (Ktor)
- Remove mockDomain dependency

---

## ⏱️ Timeline

**Estimated:** 2-3 weeks (40-60 hours)

### Week 1: Domain & Setup
- Day 1-2: Analyze architecture
- Day 3-4: Migrate domain layer
- Day 5: Choose & setup database

### Week 2: Data Layer
- Day 6-8: Migrate data layer
- Day 9-10: Setup network (if needed)
- Day 11: Update DI & remove mockDomain

### Week 3: Testing & Polish
- Day 12-13: Integration testing
- Day 14-15: Performance optimization
- Day 16: Documentation

---

## 📋 11 Tasks Overview

| # | Task | Time | Priority |
|---|------|------|----------|
| 5.1 | Architecture Analysis | 2-3h | 🔴 Critical |
| 5.2 | Choose Database | 2-4h | 🔴 Critical |
| 5.3 | Migrate Domain | 4-6h | 🔴 Critical |
| 5.4 | Migrate Data | 8-12h | 🔴 Critical |
| 5.5 | Setup Network | 4-6h | 🟡 High |
| 5.6 | Update DI | 2-3h | 🔴 Critical |
| 5.7 | Remove mockDomain | 1h | 🟡 High |
| 5.8 | Database Migration | 2-4h | 🟡 High |
| 5.9 | Integration Testing | 4-6h | 🔴 Critical |
| 5.10 | Performance | 2-4h | 🟢 Medium |
| 5.11 | Documentation | 2-3h | 🟡 High |

---

## 🚀 Start Here: Task 5.1

### Step 1: Analyze Current Architecture

```bash
cd /Users/yuriimelnyk/StudioProjects/Purchase

# Examine domain module
ls -la domain/src/main/kotlin/com/veles/purchase/domain/

# Examine data module
ls -la data/src/main/kotlin/com/veles/purchase/data/

# Count files
echo "Domain files:"
find domain/src/main/kotlin -name "*.kt" | wc -l

echo "Data files:"
find data/src/main/kotlin -name "*.kt" | wc -l
```

### Step 2: Document Current Structure

Create `PHASE_5_ARCHITECTURE_ANALYSIS.md`:

```markdown
# Phase 5: Architecture Analysis

## Current Modules

### domain/
- Entities: [list]
- Repositories: [list]
- Use Cases: [list]

### data/
- Database: [Room/Other]
- DAOs: [list]
- Entities: [list]
- Repositories: [implementations]

### Dependencies:
- Android-specific: [list]
- To migrate: [list]
```

### Step 3: Identify Challenges

Look for:
- ❌ Android-specific imports (`android.*`, `androidx.*`)
- ❌ `LiveData` usage (replace with `Flow`)
- ❌ `java.time` (replace with `kotlinx.datetime`)
- ❌ `Context` dependencies
- ❌ `Parcelable` implementations

---

## 🔍 Database Decision (Task 5.2)

### Are you currently using Room?

#### Yes → Choose **Room KMP**

**Pros:**
- Keep existing code
- Minimal changes
- Official Google support

**Setup:**
```kotlin
// shared/build.gradle.kts
commonMain.dependencies {
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.sqlite:sqlite-bundled:2.5.0-alpha01")
}

plugins {
    id("com.google.devtools.ksp")
    id("androidx.room")
}

room {
    schemaDirectory("$projectDir/schemas")
}
```

#### No / Want Better KMP Support → Choose **SQLDelight**

**Pros:**
- Mature KMP support
- Type-safe queries
- Excellent iOS support

**Setup:**
```kotlin
// shared/build.gradle.kts
commonMain.dependencies {
    implementation("app.cash.sqldelight:runtime:2.0.1")
    implementation("app.cash.sqldelight:coroutines-extensions:2.0.1")
}

androidMain.dependencies {
    implementation("app.cash.sqldelight:android-driver:2.0.1")
}

iosMain.dependencies {
    implementation("app.cash.sqldelight:native-driver:2.0.1")
}

plugins {
    id("app.cash.sqldelight") version "2.0.1"
}
```

---

## 📦 Migration Strategy

### Phase A: Domain Layer (Task 5.3)

**Move to:** `shared/src/commonMain/kotlin/com/veles/purchase/domain/`

**Steps:**
1. Copy all `domain/model/*.kt` → `shared/src/commonMain/.../domain/model/`
2. Copy all `domain/repository/*.kt` → `shared/src/commonMain/.../domain/repository/`
3. Fix imports:
   - `java.time.LocalDateTime` → `kotlinx.datetime.LocalDateTime`
   - Remove `@Parcelize` (or create expect/actual)
   - Add `@Serializable` where needed
4. Ensure all types are KMP-compatible
5. Remove `domain` module dependency

**Example:**
```kotlin
// Before (Android)
import java.time.LocalDateTime

data class Purchase(
    val id: Long,
    val date: LocalDateTime
)

// After (KMP)
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Purchase(
    val id: Long,
    val date: LocalDateTime
)
```

---

### Phase B: Data Layer (Task 5.4)

**Move to:** `shared/src/commonMain/kotlin/com/veles/purchase/data/`

#### 1. Database Setup (expect/actual)

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

#### 2. Migrate DAOs
- Copy all DAO interfaces
- Replace `LiveData` with `Flow`
- Keep Room annotations

```kotlin
// Before
@Dao
interface PurchaseDao {
    @Query("SELECT * FROM purchases")
    fun getAll(): LiveData<List<PurchaseEntity>>
}

// After
@Dao
interface PurchaseDao {
    @Query("SELECT * FROM purchases")
    fun getAll(): Flow<List<PurchaseEntity>>
}
```

#### 3. Migrate Entities
- Copy all entity classes
- Update date/time types
- Keep Room annotations

#### 4. Migrate Repositories
- Copy repository implementations
- Update to use Flow
- Inject via Koin

---

### Phase C: Update DI (Task 5.6)

```kotlin
// shared/src/commonMain/kotlin/.../di/DataModule.kt
val dataModule = module {
    // Database
    single { DatabaseBuilder(get()).build() }
    
    // DAOs
    single { get<PurchaseDatabase>().purchaseDao() }
    single { get<PurchaseDatabase>().collectionDao() }
    // ... all DAOs
    
    // Repositories
    single<PurchaseRepository> { 
        PurchaseRepositoryImpl(get(), get()) 
    }
    // ... all repositories
}

// Replace mockDataModule with dataModule
val appModules = listOf(
    dataModule,      // ← Use real data
    viewModelModule
)
```

**Android initialization:**
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

---

## 🧪 Testing Checklist (Task 5.9)

### Android:
```bash
# Uninstall old app (clear data)
adb uninstall com.veles.purchase.android

# Install new app
./gradlew :androidApp:installDebug

# Launch
adb shell am start -n com.veles.purchase.android/.MainActivity
```

### iOS:
```bash
# Build framework
./gradlew :shared:linkDebugFrameworkIosSimulatorArm64

# Open Xcode
open iosApp/iosApp.xcodeproj

# Delete app from simulator first
# Then Cmd + R to run
```

### Test All Operations:
- [ ] Create purchase → Close app → Reopen → Verify persists
- [ ] Edit purchase → Verify changes saved
- [ ] Delete purchase → Verify deleted
- [ ] Same for collections, SKUs, categories
- [ ] Test on both Android & iOS

---

## 📊 Success Criteria

### Must Pass:
- [ ] ✅ All 12 screens work with real data
- [ ] ✅ Data persists after app restart
- [ ] ✅ CRUD operations work on both platforms
- [ ] ✅ No crashes
- [ ] ✅ mockDomain removed

### Performance:
- [ ] App launch < 3 seconds
- [ ] Smooth scrolling
- [ ] Fast database operations

---

## 🗑️ Remove mockDomain (Task 5.7)

```kotlin
// shared/build.gradle.kts
// Remove this line:
// implementation(project(":mockDomain"))

// Delete this file:
// shared/src/commonMain/kotlin/.../di/MockDataModule.kt
```

---

## 📚 Key Dependencies

### Add to shared/build.gradle.kts:

```kotlin
kotlin {
    // ... existing config
    
    sourceSets {
        commonMain.dependencies {
            // Database (Room KMP)
            implementation("androidx.room:room-runtime:2.6.1")
            implementation("androidx.sqlite:sqlite-bundled:2.5.0-alpha01")
            
            // Date/Time
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")
            
            // Serialization
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
            
            // Network (Ktor) - if needed
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
    }
}

dependencies {
    ksp("androidx.room:room-compiler:2.6.1")
}
```

---

## 📞 Quick Commands

### Analyze:
```bash
# Count domain files
find domain/src/main/kotlin -name "*.kt" | wc -l

# Count data files
find data/src/main/kotlin -name "*.kt" | wc -l

# Search for Android-specific imports
grep -r "import android\." domain/src/
grep -r "import androidx\." domain/src/
```

### Build:
```bash
# Clean
./gradlew clean

# Build Android
./gradlew :androidApp:assembleDebug

# Build iOS
./gradlew :shared:linkDebugFrameworkIosSimulatorArm64
```

### Test:
```bash
# Run unit tests
./gradlew test

# Install Android app
./gradlew :androidApp:installDebug
```

---

## ⚠️ Common Issues & Solutions

### Issue: "Cannot find kotlinx.datetime"
**Solution:** Add dependency:
```kotlin
implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")
```

### Issue: "LiveData not found"
**Solution:** Replace with Flow:
```kotlin
// Before
fun getAll(): LiveData<List<Purchase>>

// After
fun getAll(): Flow<List<Purchase>>
```

### Issue: "Room compiler error"
**Solution:** Add KSP plugin and dependency:
```kotlin
plugins {
    id("com.google.devtools.ksp")
}

dependencies {
    ksp("androidx.room:room-compiler:2.6.1")
}
```

### Issue: "iOS database not found"
**Solution:** Check DatabaseBuilder implementation for iOS:
```kotlin
val dbFile = NSHomeDirectory() + "/purchase.db"
```

---

## 🎯 Today's Goal

**Start Task 5.1: Architecture Analysis**

```bash
cd /Users/yuriimelnyk/StudioProjects/Purchase

# Create analysis doc
touch PHASE_5_ARCHITECTURE_ANALYSIS.md
open PHASE_5_ARCHITECTURE_ANALYSIS.md

# Analyze structure
ls -la domain/src/main/kotlin/
ls -la data/src/main/kotlin/
```

**Time:** 2-3 hours  
**Output:** Understanding of current architecture and migration plan

---

## 📖 Full Documentation

For complete details, see:
- **PHASE_5_REAL_DATA_INTEGRATION.md** - Complete plan

---

**Status:** 🚀 **READY TO START**  
**Next Action:** Analyze current architecture  
**Priority:** 🔴 **CRITICAL**

**Let's migrate to real data! 💪🔥**

