# 💱 Currency Code Migration - KMP Solution

**Date:** December 25, 2025  
**Status:** ✅ **COMPLETE**

---

## 🎯 Problem

**Android-specific code:**
```kotlin
Currency.getInstance(Locale.getDefault()).currencyCode
```

**Issues:**
- `java.util.Currency` is JVM/Android-only
- `java.util.Locale` is JVM/Android-only  
- Not available on iOS or other KMP targets

---

## ✅ Solution: expect/actual Pattern

### Why expect/actual?

Currently (as of 2025), **there is NO standard KMP library for locale/currency**:
- ❌ kotlinx-datetime does NOT include locale/currency
- ❌ No official JetBrains library for regional formats yet
- ❌ Third-party libraries are immature

**Best practice:** Use `expect/actual` for platform-specific functionality.

---

## 📝 Implementation

### 1. Common Declaration (expect)

**File:** `shared/src/commonMain/kotlin/com/veles/purchase/domain/core/platform/CurrencyProvider.kt`

```kotlin
package com.veles.purchase.domain.core.platform

/**
 * Platform-specific currency provider
 * Returns the device's default currency code (e.g., "USD", "EUR", "GBP")
 */
expect fun getDefaultCurrencyCode(): String
```

### 2. Android Implementation (actual)

**File:** `shared/src/androidMain/kotlin/com/veles/purchase/domain/core/platform/CurrencyProvider.android.kt`

```kotlin
package com.veles.purchase.domain.core.platform

import android.icu.util.Currency
import java.util.Locale

actual fun getDefaultCurrencyCode(): String {
    return try {
        Currency.getInstance(Locale.getDefault()).currencyCode
    } catch (e: Exception) {
        "USD" // Fallback to USD if currency detection fails
    }
}
```

**Uses:**
- `android.icu.util.Currency` (available on Android API 24+)
- `java.util.Locale` (Android standard library)

### 3. iOS Implementation (actual)

**File:** `shared/src/iosMain/kotlin/com/veles/purchase/domain/core/platform/CurrencyProvider.ios.kt`

```kotlin
package com.veles.purchase.domain.core.platform

import platform.Foundation.NSLocale
import platform.Foundation.currentLocale
import platform.Foundation.currencyCode

actual fun getDefaultCurrencyCode(): String {
    return try {
        NSLocale.currentLocale.currencyCode ?: "USD"
    } catch (e: Exception) {
        "USD" // Fallback to USD if currency detection fails
    }
}
```

**Uses:**
- `platform.Foundation.NSLocale` (iOS Foundation framework)
- Native iOS API for locale/currency

---

## 💻 Usage

### In Models:

**Before (Android-only):**
```kotlin
import java.util.Currency
import java.util.Locale

data class SkuModel(
    val skuCurrencyCode: String = Currency.getInstance(Locale.getDefault()).currencyCode
)
```

**After (KMP-compatible):**
```kotlin
import com.veles.purchase.domain.core.platform.getDefaultCurrencyCode

data class SkuModel(
    val skuCurrencyCode: String = getDefaultCurrencyCode()
)
```

### In Entities:

**Before (Android-only):**
```kotlin
import android.icu.util.Currency
import java.util.Locale

@Entity
data class SkuEntity(
    val skuCurrencyCode: String = Currency.getInstance(Locale.getDefault()).currencyCode
)
```

**After (KMP-compatible):**
```kotlin
@Entity
data class SkuEntity(
    val skuCurrencyCode: String = "USD"  // Default in database
)
```

**Note:** For database entities, it's better to use a simple default and let the application layer handle platform-specific defaults.

---

## 🔧 Files Modified

### Created (New Files):
1. ✅ `shared/src/commonMain/.../CurrencyProvider.kt` - expect declaration
2. ✅ `shared/src/androidMain/.../CurrencyProvider.android.kt` - Android impl
3. ✅ `shared/src/iosMain/.../CurrencyProvider.ios.kt` - iOS impl

### Modified:
4. ✅ `shared/src/commonMain/.../SkuModel.kt` - Uses `getDefaultCurrencyCode()`
5. ✅ `shared/src/commonMain/.../SkuSumMonthModel.kt` - Fixed `java.time` → `kotlinx.datetime`
6. ✅ `shared/src/commonMain/.../SkuEntity.kt` - Hardcoded "USD" default
7. ✅ `shared/src/commonMain/.../SkuSumMonthRelations.kt` - Hardcoded "USD" default

---

## 📚 Reference Documentation

### Official Kotlin Docs:
- **expect/actual:** https://kotlinlang.org/docs/multiplatform-expect-actual.html
- **KMP Mobile:** https://kotlinlang.org/docs/multiplatform-mobile-getting-started.html

### Why No Standard Library Yet?

As of December 2025:
- Locale/currency/regional formatting is **complex** and **platform-specific**
- JetBrains hasn't released a standard library yet
- Regional format handling requires:
  - Number formatting
  - Date formatting
  - Currency symbols and codes
  - Time zones
  - Measurement units
  - etc.

**Future:** May be added to kotlinx.* libraries eventually

### Current Options:

1. **expect/actual** (our approach) ✅
   - Pros: Official pattern, full platform control
   - Cons: Manual implementation per platform

2. **Hardcoded defaults**
   - Pros: Simple, works everywhere
   - Cons: Not locale-aware

3. **Third-party libraries**
   - Still immature as of 2025
   - Example: `moko-resources` (partial support)

---

## 🎯 Best Practices

### For Models:
```kotlin
// ✅ Good: Platform-specific detection
val currency: String = getDefaultCurrencyCode()

// ✅ Also good: Make it required (let caller decide)
val currency: String  // No default - must be provided
```

### For Entities (Database):
```kotlin
// ✅ Good: Simple default
val currency: String = "USD"

// ⚠️ Avoid: Platform-specific in entity
val currency: String = getDefaultCurrencyCode()  // Causes issues with migrations
```

### For User Preferences:
```kotlin
// ✅ Best: Let user configure, default to platform
val userCurrency: String = preferences.get("currency") 
    ?: getDefaultCurrencyCode()
```

---

## ✅ Benefits of This Approach

1. **Truly Multiplatform** ✅
   - Works on Android (uses `android.icu.util.Currency`)
   - Works on iOS (uses `NSLocale.currencyCode`)
   - Easy to add other platforms later

2. **Type-Safe** ✅
   - Compile-time checking
   - No runtime reflection

3. **Testable** ✅
   - Can mock `getDefaultCurrencyCode()` in tests
   - Platform-specific behavior isolated

4. **Maintainable** ✅
   - Clear separation of platform code
   - Easy to update per platform

5. **Fallback-Safe** ✅
   - Returns "USD" if detection fails
   - No crashes on edge cases

---

## 🚀 Future Migration Path

When/if JetBrains releases an official kotlinx-locale library:

```kotlin
// Future (hypothetical):
import kotlinx.locale.Locale
import kotlinx.locale.Currency

val currencyCode = Locale.current.currency.code
```

**Migration will be easy:**
- Remove our expect/actual files
- Replace `getDefaultCurrencyCode()` with standard library
- Update imports

---

## 📊 Platform Support

| Platform | API Used | Min Version |
|----------|----------|-------------|
| Android | `android.icu.util.Currency` | API 24+ |
| iOS | `NSLocale.currencyCode` | iOS 10+ |
| JVM | (Not implemented yet) | - |
| JS | (Not implemented yet) | - |

**To add more platforms:** Create new `actual` implementations in respective source sets.

---

## ✅ Summary

**Problem:** `Currency.getInstance(Locale.getDefault())` is JVM/Android-only

**Solution:** expect/actual pattern with platform-specific implementations
- Android: Uses `android.icu.util.Currency`
- iOS: Uses `NSLocale.currencyCode`
- Fallback: "USD" on errors

**Files Created:** 3 (1 expect + 2 actuals)
**Files Modified:** 4 (models and entities)

**Result:** ✅ Fully KMP-compatible currency code detection!

---

_Documentation Date: December 25, 2025_  
_Pattern: expect/actual (official KMP pattern)_  
_Status: Production-ready_

