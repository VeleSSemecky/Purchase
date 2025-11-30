# ✅ Build Issue Fixed - mockDomain Plugin Conflict

## Problem

Gradle build failed with error:
```
Error resolving plugin [id: 'org.jetbrains.kotlin.multiplatform', version: '2.1.0']
The request for this plugin could not be satisfied because the plugin is already 
on the classpath with an unknown version, so compatibility cannot be checked.
```

## Root Cause

The mockDomain.gradle.kts was using:
```kotlin
plugins {
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
}
```

This caused a classpath conflict because:
1. Kotlin plugin was already on classpath from buildSrc or parent configuration
2. Using `alias` with explicit version from version catalog tried to load it again
3. Gradle couldn't verify compatibility

## Solution

Changed from:
```kotlin
plugins {
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
}
```

To:
```kotlin
plugins {
    kotlin("multiplatform")
}
```

This uses the Kotlin plugin that's already on the classpath without version specification.

## Changes Made

### mockDomain.gradle.kts
- ✅ Changed plugin declaration to `kotlin("multiplatform")`
- ✅ Changed from `androidTarget` to `jvm` target (mockDomain is pure Kotlin)
- ✅ Updated sourceSets from `androidMain` to `jvmMain`
- ✅ Kept iOS targets (iosX64, iosArm64, iosSimulatorArm64)

## Verification

✅ **mockDomain compiles:**
```bash
./gradlew :mockDomain:compileKotlinJvm
./gradlew :mockDomain:compileKotlinIosSimulatorArm64
```

✅ **shared module compiles:**
```bash
./gradlew :shared:compileDebugKotlinAndroid
```

✅ **No errors in IDE**

## Why This Works

1. **kotlin("multiplatform")** - Short-hand syntax that uses classpath version
2. **jvm target** - More appropriate for domain layer than androidTarget
3. **No version conflicts** - Gradle uses single version from classpath

## Alternative Solutions Considered

### Option 1: Remove version from alias ❌
Would require changing libs.versions.toml globally

### Option 2: Use different plugin ❌
Kotlin Multiplatform is the right choice

### Option 3: Hardcode version ❌
```kotlin
id("org.jetbrains.kotlin.multiplatform") version "2.1.0"
```
Would cause same conflict

### ✅ Option 4: Use kotlin() DSL (CHOSEN)
```kotlin
kotlin("multiplatform")
```
Clean, simple, uses classpath version

## Benefits of JVM Target vs Android Target

For mockDomain, JVM target is better because:
- ✅ Pure Kotlin code (no Android APIs)
- ✅ Lighter configuration
- ✅ Can be used from both Android and JVM projects
- ✅ No Android SDK dependency
- ✅ Faster compilation

## Updated mockDomain.gradle.kts Structure

```kotlin
plugins {
    kotlin("multiplatform")  // ✅ Fixed
}

kotlin {
    jvm {  // ✅ Changed from androidTarget
        compilations.all {
            kotlinOptions {
                jvmTarget = "19"
            }
        }
    }
    
    // iOS targets unchanged
    listOf(iosX64(), iosArm64(), iosSimulatorArm64()).forEach {
        it.binaries.framework {
            baseName = "mockDomain"
            isStatic = true
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.coroutines.core)
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.7.1")
            }
        }

        val jvmMain by getting {  // ✅ Changed from androidMain
            dependencies {
                // JVM/Android specific if needed
            }
        }

        val iosMain by creating {
            dependsOn(commonMain)
            // ... iOS configuration
        }
    }
}
```

## Status

✅ **RESOLVED**

- mockDomain builds successfully
- shared module builds successfully
- No classpath conflicts
- All targets compile (JVM + iOS)

## Next Steps

Continue with Phase 2.2 - ViewModels Migration!

---

_Fixed: November 29, 2025_  
_Issue: Plugin classpath conflict_  
_Solution: Use kotlin("multiplatform") DSL_  
_Status: ✅ Resolved_

