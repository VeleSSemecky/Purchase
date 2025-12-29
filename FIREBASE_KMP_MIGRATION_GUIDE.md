# 📘 Firebase KMP Repository Migration Guide

**Reference Guide for Migrating Additional Repositories**

---

## 🎯 Purpose

This guide provides a step-by-step template for migrating additional Firebase repositories from Android Firebase SDK to GitLive Firebase KMP SDK.

**Status:** Phase 5.5 core migration complete. Use this guide for additional repositories.

---

## ✅ Prerequisites

Before migrating a repository, ensure:
- ✅ Firebase KMP dependencies added (Step 1 complete)
- ✅ Firebase initialized on both platforms (Step 2 complete)
- ✅ `firebaseModule` and `repositoryModule` exist
- ✅ You understand the existing migration pattern

---

## 📋 Migration Checklist

For each repository, follow these steps:

### Step 1: Analyze Existing Repository ⏳

**Actions:**
1. [ ] Read the Android repository implementation
2. [ ] Identify Firebase dependencies (Firestore, Auth, Storage, etc.)
3. [ ] List all Firebase operations used
4. [ ] Note any Android-specific code

**Example:**
```kotlin
// Android implementation to migrate
class PurchaseRepositoryImpl(
    private val firestore: FirebaseFirestore  // Android SDK
) : PurchaseRepository {
    override suspend fun getPurchases() = 
        firestore.collection("purchases")
            .get()
            .await()  // Android-specific
}
```

---

### Step 2: Create/Update DTOs ⏳

**Actions:**
1. [ ] Check if DTO exists in shared module
2. [ ] If not, create with `@Serializable`
3. [ ] Add conversion extensions

**Template:**
```kotlin
// File: shared/src/commonMain/kotlin/com/.../entity/MyDto.kt

import kotlinx.serialization.Serializable

@Serializable
data class MyDto(
    val id: String = "",
    val name: String = "",
    val timestamp: Long = 0L
)

// Conversion to domain model
fun MyDto.toDomainModel() = MyModel(
    id = id,
    name = name,
    timestamp = timestamp
)

// Conversion from domain model
fun MyModel.toDto() = MyDto(
    id = id,
    name = name,
    timestamp = timestamp
)
```

---

### Step 3: Create Firebase Extensions ⏳

**Actions:**
1. [ ] Add collection shortcuts to `FirebaseExtensions.kt`
2. [ ] Keep naming consistent

**Template:**
```kotlin
// File: shared/src/commonMain/kotlin/com/.../extensions/FirebaseExtensions.kt

// Add to existing file:
val FirebaseFirestore.myCollection
    get() = collection("myCollectionName")

fun FirebaseFirestore.mySubCollection(parentId: String) = 
    myCollection
        .document(parentId)
        .collection("subCollection")
```

---

### Step 4: Implement Repository ⏳

**Actions:**
1. [ ] Create repository implementation in shared module
2. [ ] Replace Android Firebase imports with GitLive imports
3. [ ] Update API calls to use GitLive syntax
4. [ ] Remove `.await()` calls (already suspend)
5. [ ] Update deprecated methods

**Template:**
```kotlin
// File: shared/src/commonMain/kotlin/com/.../repository/MyRepositoryImpl.kt

import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.auth.FirebaseAuth
import com.veles.purchase.data.extensions.myCollection
import com.veles.purchase.data.entity.MyDto
import com.veles.purchase.domain.repository.MyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MyRepositoryImpl(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth? = null  // If needed
) : MyRepository {

    // Example: Get single item
    override suspend fun getItem(id: String): MyModel {
        val snapshot = firestore.myCollection
            .document(id)
            .get()
        
        return snapshot.data<MyDto>().toDomainModel()
    }
    
    // Example: Get list
    override suspend fun getItems(): List<MyModel> {
        val snapshot = firestore.myCollection.get()
        
        return snapshot.documents.mapNotNull { 
            it.data<MyDto>().toDomainModel() 
        }
    }
    
    // Example: Real-time stream
    override fun getItemsFlow(): Flow<List<MyModel>> {
        return firestore.myCollection
            .snapshots
            .map { snapshot ->
                snapshot.documents.mapNotNull { 
                    it.data<MyDto>().toDomainModel() 
                }
            }
    }
    
    // Example: Create/Update
    override suspend fun saveItem(item: MyModel) {
        firestore.myCollection
            .document(item.id)
            .set(item.toDto())
    }
    
    // Example: Delete
    override suspend fun deleteItem(id: String) {
        firestore.myCollection
            .document(id)
            .delete()
    }
    
    // Example: Query with filter
    override suspend fun searchItems(query: String): List<MyModel> {
        val snapshot = firestore.myCollection
            .where {
                "name" greaterThanOrEqualTo query
                "name" lessThanOrEqualTo query + "\uF7FF"
            }
            .limit(40)
            .get()
        
        return snapshot.documents.mapNotNull { 
            it.data<MyDto>().toDomainModel() 
        }
    }
    
    // Example: Update specific fields
    override suspend fun updateField(id: String, fieldValue: String) {
        firestore.myCollection
            .document(id)
            .set(mapOf("fieldName" to fieldValue), merge = true)
    }
}
```

---

### Step 5: Add to Dependency Injection ⏳

**Actions:**
1. [ ] Add repository to `repositoryModule`
2. [ ] Ensure all dependencies are available

**Template:**
```kotlin
// File: shared/src/commonMain/kotlin/com/.../repository/RepositoryModule.kt

val repositoryModule = module {
    // ...existing repositories...
    
    // Add new repository
    single<MyRepository> { 
        MyRepositoryImpl(
            firestore = get(),
            auth = get()  // If needed
        ) 
    }
}
```

---

### Step 6: Test & Verify ⏳

**Actions:**
1. [ ] Compile the code
2. [ ] Check for errors
3. [ ] Test on Android
4. [ ] Test on iOS
5. [ ] Verify real-time updates work

**Verification:**
```bash
# Compile
./gradlew :shared:compileKotlinMetadata

# Build for Android
./gradlew :shared:compileDebugKotlinAndroid

# Build for iOS
./gradlew :shared:compileKotlinIosArm64
```

---

## 🔧 Common API Changes

### Android SDK → GitLive KMP

| Operation | Android Firebase SDK | GitLive Firebase KMP |
|-----------|---------------------|---------------------|
| **Import** | `import com.google.firebase.*` | `import dev.gitlive.firebase.*` |
| **Get document** | `.get().await()` | `.get()` (already suspend) |
| **Parse document** | `.toObject<T>()` | `.data<T>()` |
| **Real-time** | `.snapshots()` | `.snapshots` (property) |
| **Where filter** | `.where(field, equalTo = value)` | `.where { field equalTo value }` |
| **Update field** | `.update(field to value)` | `.set(mapOf(field to value), merge = true)` |
| **Delete** | `.delete().await()` | `.delete()` |
| **Set** | `.set(data).await()` | `.set(data)` |
| **Query limit** | `.limit(n).get().await()` | `.limit(n).get()` |

---

## 📝 Code Examples

### Example 1: Simple CRUD Repository

```kotlin
class ProductRepositoryImpl(
    private val firestore: FirebaseFirestore
) : ProductRepository {
    
    override suspend fun getProduct(id: String) = 
        firestore.collection("products")
            .document(id)
            .get()
            .data<ProductDto>()
            .toModel()
    
    override suspend fun saveProduct(product: Product) =
        firestore.collection("products")
            .document(product.id)
            .set(product.toDto())
    
    override suspend fun deleteProduct(id: String) =
        firestore.collection("products")
            .document(id)
            .delete()
}
```

### Example 2: Repository with Real-time Updates

```kotlin
class OrderRepositoryImpl(
    private val firestore: FirebaseFirestore
) : OrderRepository {
    
    override fun getOrdersFlow(userId: String): Flow<List<Order>> {
        return firestore.collection("orders")
            .where { "userId" equalTo userId }
            .snapshots
            .map { snapshot ->
                snapshot.documents.mapNotNull { 
                    it.data<OrderDto>().toModel() 
                }
            }
    }
}
```

### Example 3: Repository with Search

```kotlin
class SearchRepositoryImpl(
    private val firestore: FirebaseFirestore
) : SearchRepository {
    
    override suspend fun search(query: String): List<Item> {
        return firestore.collection("items")
            .where {
                "name" greaterThanOrEqualTo query
                "name" lessThanOrEqualTo query + "\uF7FF"
            }
            .limit(20)
            .get()
            .documents
            .mapNotNull { it.data<ItemDto>().toModel() }
    }
}
```

---

## ⚠️ Common Pitfalls

### 1. Using `.await()`
❌ **Wrong:**
```kotlin
firestore.collection("items").get().await()
```

✅ **Correct:**
```kotlin
firestore.collection("items").get()  // Already suspend
```

### 2. Using `.toObject<T>()`
❌ **Wrong:**
```kotlin
document.toObject<MyDto>()
```

✅ **Correct:**
```kotlin
document.data<MyDto>()
```

### 3. Using Old Where Syntax
❌ **Wrong:**
```kotlin
.where("field", equalTo = value)
```

✅ **Correct:**
```kotlin
.where { "field" equalTo value }
```

### 4. Using `update()` Directly
❌ **Wrong:**
```kotlin
.update("field" to value)  // Deprecated
```

✅ **Correct:**
```kotlin
.set(mapOf("field" to value), merge = true)
```

### 5. Android-Specific Types
❌ **Wrong:**
```kotlin
import android.os.Parcelable
data class MyDto(...) : Parcelable
```

✅ **Correct:**
```kotlin
import kotlinx.serialization.Serializable
@Serializable
data class MyDto(...)
```

---

## 📊 Estimation Guide

| Repository Complexity | Estimated Time |
|----------------------|----------------|
| Simple (CRUD only) | 30-45 minutes |
| Medium (CRUD + queries) | 45-60 minutes |
| Complex (CRUD + real-time + auth) | 1-2 hours |

**Factors that increase time:**
- Multiple collections
- Complex queries
- Real-time subscriptions
- Authentication logic
- File uploads/downloads

---

## ✅ Quality Checklist

Before marking a repository as complete:

### Code Quality:
- [ ] No Android-specific imports in common code
- [ ] No `.await()` calls (unnecessary in KMP)
- [ ] Using `.data<T>()` instead of `.toObject<T>()`
- [ ] Using `@Serializable` instead of `Parcelable`
- [ ] Using `Unit` instead of `Void`

### Functionality:
- [ ] All repository methods implemented
- [ ] DTO conversions work correctly
- [ ] Firebase extensions created
- [ ] Added to dependency injection

### Testing:
- [ ] Code compiles for commonMain
- [ ] Code compiles for Android
- [ ] Code compiles for iOS
- [ ] No compiler errors
- [ ] Only expected warnings

---

## 🚀 Example Migration Timeline

**Hour 1:**
- Analyze existing repository
- Create/update DTOs
- Create Firebase extensions

**Hour 2:**
- Implement repository
- Add to DI
- Test compilation

**Hour 3:**
- Fix any issues
- Test on Android
- Test on iOS

**Total:** 2-3 hours per repository (average)

---

## 📚 Reference

**Completed Examples:**
- `AuthWithGoogleRepositoryImpl.kt` - Authentication
- `FirebaseGetUserRepositoryImpl.kt` - Real-time data
- `FirebaseMessageTokenRepositoryImpl.kt` - Field updates

**Documentation:**
- `PHASE_5_5_FINAL_STATUS.md` - Complete overview
- `PHASE_5_5_STEP_3_COMPLETE.md` - Migration details

**GitLive Docs:**
- https://github.com/GitLiveApp/firebase-kotlin-sdk

---

**Use this guide to migrate additional repositories as needed!** 🚀

