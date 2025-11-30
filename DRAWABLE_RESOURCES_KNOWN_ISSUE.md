# ⚠️ Drawable Resources - Known Issue

**Date:** November 30, 2025
**Status:** 🔧 Needs Resolution
**Issue:** Resource accessors not available at compile time

---

## 📋 Summary

All 33 drawable resources have been successfully migrated from the presentation module to `shared/src/commonMain/composeResources/drawable/`, and the Compose Resources system is configured. However, the generated resource accessors (`Res.drawable.ic_*`) are not being recognized by the Kotlin compiler at build time.

---

## ✅ What Works

1. **✅ Resources Copied** - All 33 files in correct location
2. **✅ Gradle Configured** - Compose Resources setup complete
3. **✅ Resources Generated** - Accessor files created successfully
4. **✅ Build Tasks Run** - `generateResourceAccessorsForCommonMain` and `generateComposeResClass` succeed

**Generated Files:**
- ✅ `/shared/build/generated/compose/resourceGenerator/kotlin/commonResClass/com/veles/purchase/shared/resources/Res.kt`
- ✅ `/shared/build/generated/compose/resourceGenerator/kotlin/commonMainResourceAccessors/com/veles/purchase/shared/resources/Drawable0.commonMain.kt`

---

## ❌ What Doesn't Work

**Compilation Error:**
```
e: Unresolved reference 'ic_category'.
e: Unresolved reference 'ic_navigate_next'.
e: Unresolved reference 'ic_baseline_history_24'.
```

**Code That Fails:**
```kotlin
import com.veles.purchase.shared.resources.Res
import org.jetbrains.compose.resources.painterResource

Icon(
    painter = painterResource(Res.drawable.ic_category), // ❌ Unresolved reference
    contentDescription = "Category"
)
```

---

## 🔍 Investigation Findings

### Generated Accessor Structure
```kotlin
// File: Drawable0.commonMain.kt
package com.veles.purchase.shared.resources

public val Res.drawable.ic_category: DrawableResource by lazy {
    DrawableResource("drawable:ic_category", setOf(
        ResourceItem(setOf(), "${MD}drawable/ic_category.xml", -1, -1),
    ))
}
```

**Analysis:**
- ✅ Accessors are generated correctly as extension properties
- ✅ Package is correct: `com.veles.purchase.shared.resources`
- ❌ Kotlin compiler doesn't see these files during compilation
- ❌ Generated sources aren't in the compilation classpath

### Gradle Configuration
```kotlin
// shared.gradle.kts
compose.resources {
    publicResClass = true
    packageOfResClass = "com.veles.purchase.shared.resources"
    generateResClass = always
}

dependencies {
    implementation(compose.components.resources)
}
```

**Analysis:**
- ✅ Configuration looks correct
- ❌ May need additional source set configuration
- ❌ Build task dependencies may not be correct

---

## 🎯 Possible Solutions

### Solution 1: Add Generated Sources to Source Sets (Most Likely)
```kotlin
// shared.gradle.kts
kotlin {
    sourceSets {
        val commonMain by getting {
            kotlin.srcDir("build/generated/compose/resourceGenerator/kotlin/commonResClass")
            kotlin.srcDir("build/generated/compose/resourceGenerator/kotlin/commonMainResourceAccessors")
            // ... existing dependencies
        }
    }
}
```

### Solution 2: Update Compose Multiplatform Version
Current version: `1.8.1`
- Try updating to latest version (1.8.2 or newer)
- Check release notes for resource generation fixes

### Solution 3: Use Different Resource Configuration
```kotlin
compose {
    resources {
        publicResClass = false  // Try different settings
        packageOfResClass = "com.veles.purchase.shared.resources"
        generateResClass = auto  // vs always
    }
}
```

### Solution 4: Check Task Dependencies
Ensure `compileDebugKotlinAndroid` depends on resource generation:
```kotlin
tasks.named("compileDebugKotlinAndroid") {
    dependsOn("generateResourceAccessorsForCommonMain")
    dependsOn("generateComposeResClass")
}
```

### Solution 5: IDE Re-sync
- Invalidate caches and restart IDE
- Re-import Gradle project
- Let IDE index generated files

---

## 🔄 Workaround (Current)

Until the issue is resolved, screens continue using emoji placeholders:

```kotlin
// CollectionEditScreen.kt
Text(
    text = "📂", // Category icon placeholder
    fontSize = 24.sp,
    color = Colors.gr
)

// TODO: Replace with actual icons when Compose Resources are working
// Icon(
//     painter = painterResource(Res.drawable.ic_category),
//     contentDescription = "Category"
// )
```

---

## 📊 Impact

### Low Impact Areas
- ✅ Build succeeds (with emoji placeholders)
- ✅ App functionality not affected
- ✅ Resources are ready to use once issue is fixed

### Medium Impact Areas
- ⚠️ Visual inconsistency (emojis vs proper icons)
- ⚠️ Cannot test icon rendering yet
- ⚠️ Additional migration step needed later

### High Impact Areas
- ❌ iOS icon support blocked (until resources work)
- ❌ Complete Phase 3 icon migration blocked

---

## 🎯 Next Actions

### Immediate (High Priority)
1. **Try Solution 1** - Add generated sources to source sets explicitly
2. **Verify Build** - Test if resources are now accessible
3. **Update CollectionEditScreen** - Replace emojis with actual icons

### Short Term (If Solution 1 Fails)
1. **Research** - Check Compose Multiplatform docs and issues
2. **Community** - Ask on Kotlin Slack / Stack Overflow
3. **Alternative** - Try different resource configuration approaches

### Long Term
1. **Document Solution** - Once working, document exact configuration
2. **Update All Screens** - Replace all emoji placeholders
3. **Test on iOS** - Verify resources work cross-platform

---

## 📚 References

### Documentation
- [Compose Multiplatform Resources](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-images-resources.html)
- [Resource Migration Guide](https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-multiplatform-resources-migration.html)
- [Compose Resources GitHub](https://github.com/JetBrains/compose-multiplatform/tree/master/components/resources)

### Similar Issues
- Search GitHub issues for: "compose multiplatform resources unresolved"
- Search Stack Overflow for: "kotlin multiplatform drawable resources"
- Check Kotlin Slack #compose-multiplatform channel

---

## 💡 Temporary Development Tips

### For Developers
1. **Don't remove resource files** - Keep them in place even if not usable yet
2. **Don't delete generated code** - May be needed for investigation
3. **Document attempts** - Note what was tried and results

### For Testing
1. **Test with emojis** - Verify screen layout and spacing
2. **Prepare replacements** - Know which emoji maps to which icon
3. **Plan visual test** - Once working, test all icon replacements

---

## 🔧 Debugging Steps Tried

### Attempts Made:
1. ❌ Clean and rebuild - No effect
2. ❌ Force resource regeneration with `--rerun-tasks` - No effect
3. ❌ Different task ordering (`generateComposeResClass` before `compileDebugKotlinAndroid`) - No effect
4. ❌ Checked generated file locations - Files exist and look correct
5. ❌ Verified package names match - All correct

### Not Yet Tried:
- [ ] Explicitly adding generated sources to source sets
- [ ] Updating Compose Multiplatform version
- [ ] Different resource configuration (`generateResClass = auto`)
- [ ] Custom task dependencies
- [ ] IDE invalidate caches
- [ ] Testing on different machine/environment

---

## 📝 Related Files

**Configuration:**
- `shared/shared.gradle.kts` - Compose Resources configuration
- `gradle/libs.versions.toml` - Version declarations

**Resources:**
- `shared/src/commonMain/composeResources/drawable/` - 33 drawable files

**Generated (Not in Version Control):**
- `shared/build/generated/compose/resourceGenerator/kotlin/commonResClass/Res.kt`
- `shared/build/generated/compose/resourceGenerator/kotlin/commonMainResourceAccessors/Drawable0.commonMain.kt`

**Documentation:**
- `DRAWABLE_RESOURCES_MIGRATION_COMPLETE.md` - Full migration details
- `ICON_INVENTORY.md` - Complete icon list
- `NEXT_STEPS_PLAN.md` - Phase 3 planning

---

## ✅ Success Criteria

**Issue will be considered resolved when:**
1. ✅ `Res.drawable.ic_*` references compile without errors
2. ✅ Icons display correctly in UI
3. ✅ Build succeeds with actual icon usage (no emojis)
4. ✅ Solution is documented and reproducible

---

_Last Updated: November 30, 2025_
_Status: 🔧 Under Investigation_
_Priority: 🟡 Medium (Workaround exists, not blocking)_