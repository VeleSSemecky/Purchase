# 🕐 Clock.System Fix - Kotlin Multiplatform

## Date: November 29, 2025

---

## Problem

```kotlin
e: Unresolved reference 'System'.
e: 'typealias Clock = Clock' is deprecated.
```

**Files affected:**
- `Utill.kt` - helper functions
- `SkuModel.kt` - default time value  
- `PurchaseModel.kt` - timestamp generation

---

## Root Cause

**Import Conflict:**
- `kotlin.time.Clock` (new Kotlin stdlib)
- `kotlinx.datetime.Clock` (datetime library)

When importing `kotlinx.datetime.Clock`, Kotlin sees it as deprecated in favor of `kotlin.time.Clock`.

**The Issue:**
```kotlin
import kotlinx.datetime.Clock  // ❌ Deprecated warning
Clock.System.now()              // ❌ Unresolved reference 'System'
```

---

## Solution

**Use Fully Qualified Names (no import):**

```kotlin
// ✅ CORRECT - No Clock import needed
fun currentLocalDateTime(): kotlinx.datetime.LocalDateTime {
    return kotlinx.datetime.Clock.System.now()
        .toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault())
}

fun currentTimeMillis(): Long {
    return kotlinx.datetime.Clock.System.now().toEpochMilliseconds()
}
```

---

## Files Fixed

### 1. Utill.kt ✅

**Before:**
```kotlin
import kotlinx.datetime.*

fun currentLocalDateTime(): LocalDateTime {
    val instant = Instant.fromEpochMilliseconds(System.currentTimeMillis())
    return instant.toLocalDateTime(TimeZone.currentSystemDefault())
}

fun currentTimeMillis(): Long = System.currentTimeMillis()  // ❌ Java API!
```

**After:**
```kotlin
// No kotlinx.datetime.Clock import!

fun currentLocalDateTime(): kotlinx.datetime.LocalDateTime {
    return kotlinx.datetime.Clock.System.now()
        .toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault())
}

fun currentTimeMillis(): Long {
    return kotlinx.datetime.Clock.System.now().toEpochMilliseconds()
}
```

### 2. SkuModel.kt ✅

**Before:**
```kotlin
import kotlinx.datetime.*

data class SkuModel(
    val skuId: String,
    val skuLocalData: LocalDateTime = Clock.System.now()...  // ❌ Error
)
```

**After:**
```kotlin
import com.veles.purchase.domain.utill.currentLocalDateTime
import kotlinx.datetime.LocalDateTime

data class SkuModel(
    val skuId: String,
    val skuLocalData: LocalDateTime = currentLocalDateTime(),  // ✅ Works!
)
```

### 3. PurchaseModel.kt ✅

**Before:**
```kotlin
import kotlinx.datetime.*

companion object {
    val EMPTY = PurchaseModel(
        createId = Clock.System.now()...  // ❌ Error
    )
}
```

**After:**
```kotlin
import com.veles.purchase.domain.utill.currentTimeMillis

companion object {
    val EMPTY = PurchaseModel(
        createId = currentTimeMillis().toString(),  // ✅ Works!
    )
}
```

---

## Why This Works

### The Problem with Imports:

1. **kotlin.time.Clock** (Kotlin 1.9+)
   - New standard library clock
   - Has `Clock.System` for monotonic time
   - Used for measuring durations

2. **kotlinx.datetime.Clock** (kotlinx-datetime library)
   - For calendar dates and times
   - Has `Clock.System` for wall-clock time
   - Used for timestamps and dates

**When you import one, it shadows the other!**

### The Solution:

**Use fully qualified names:**
```kotlin
kotlinx.datetime.Clock.System.now()  // ✅ Explicit package
```

**Or use helper functions:**
```kotlin
fun currentLocalDateTime() = 
    kotlinx.datetime.Clock.System.now()
        .toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault())
```

---

## Key Takeaways

### ✅ DO:
- Use fully qualified `kotlinx.datetime.Clock.System`
- Create helper functions in common code
- Avoid importing `Clock` directly

### ❌ DON'T:
- Import `kotlinx.datetime.Clock` (conflicts!)
- Use `System.currentTimeMillis()` (Java-only!)
- Use `kotlin.time.Clock` for timestamps (wrong API!)

---

## kotlinx-datetime API Reference

### Getting Current Time:

```kotlin
// Current instant
val now = kotlinx.datetime.Clock.System.now()

// As epoch milliseconds
val millis = now.toEpochMilliseconds()

// As LocalDateTime
val localDateTime = now.toLocalDateTime(TimeZone.currentSystemDefault())

// As formatted string
val formatted = now.toString()  // ISO-8601
```

### Time Zones:

```kotlin
// System default
TimeZone.currentSystemDefault()

// UTC
TimeZone.UTC

// Specific zone
TimeZone.of("America/New_York")
```

---

## Build Status

```
✅ mockDomain compiles
✅ shared compiles
✅ androidApp builds
✅ 0 errors

BUILD SUCCESSFUL!
```

---

## Related Documentation

- [kotlinx-datetime](https://github.com/Kotlin/kotlinx-datetime)
- [Clock.System API](https://kotlinlang.org/api/kotlinx-datetime/kotlinx-datetime/kotlinx.datetime/-clock/-system/)
- [Instant](https://kotlinlang.org/api/kotlinx-datetime/kotlinx-datetime/kotlinx.datetime/-instant/)

---

## Summary

**Problem:** Import conflict between `kotlin.time.Clock` and `kotlinx.datetime.Clock`

**Solution:** Use fully qualified names `kotlinx.datetime.Clock.System`

**Result:** ✅ All compilation errors fixed!

---

_Fixed: November 29, 2025_  
_Time: 10 minutes_  
_Status: RESOLVED ✅_

