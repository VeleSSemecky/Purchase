# 🔍 Screen Migration Audit & Fix Plan

**Date:** November 30, 2025
**Status:** 🚨 CRITICAL - Screens need fixing to match originals exactly

---

## 📋 Executive Summary

During migration to KMP, we simplified several screens by using Material3 components instead of custom components from the presentation module. This resulted in:

1. **Missing custom Swipeable implementation** - Used Material3's SwipeToDismissBox instead
2. **Missing SearchTopAppBar component** - Created simplified version
3. **Missing exact UI matching** - Some padding, sizes, and behaviors differ
4. **Missing custom compose utilities** - Colors, textStyles, and helper functions

**Action Required:** Migrate ALL custom components from presentation module to shared, ensure exact UI/UX parity.

---

## 🔎 Critical Issues Found

### 1. ❌ PurchaseListScreen - Major Differences

**Original:** `ListPurchaseFragment.kt`
**Migrated:** `PurchaseListScreen.kt`

**Missing Components:**
- ❌ Custom `SwipeToDismiss` from `/presentation/compose/Swipeable.kt`
- ❌ Custom `SearchTopAppBar` from `/presentation/compose/search/SearchAppBar.kt`
- ❌ Custom `Colors` object from `/presentation/compose/Colors.kt`
- ❌ `textStyle1()` and `textStyle2()` functions
- ❌ Exact FractionalThreshold(0.7f) for swipe dismiss
- ❌ Custom dismiss animations
- ❌ Progress overlay matching original
- ❌ Sort functionality with proper dialog

**Current Issues:**
```kotlin
// ❌ WRONG: Using Material3's SwipeToDismissBox
SwipeToDismissBox(
    state = dismissState,
    backgroundContent = {}
) { ... }

// ✅ CORRECT: Should use custom Swipeable
SwipeToDismiss(
    state = dismissState,
    background = {},
    dismissThresholds = { FractionalThreshold(0.7f) }
) { ... }
```

**Search Issues:**
```kotlin
// ❌ WRONG: Simplified search bar
SearchTopBar(...) // Custom implementation

// ✅ CORRECT: Should use SearchTopAppBar
SearchTopAppBar(
    searchTextState = searchText,
    onTextChange = { state.updateSearchText(it) },
    navigationIcon = { ... },
    title = { ... },
    actions = { searchWidgetState -> ... }
)
```

---

### 2. ⚠️ List Later Screen - Missing Custom Swipeable

**Original:** Uses custom `SwipeToDismiss`
**Migrated:** Uses Material3 `SwipeToDismissBox`

**Impact:** Different swipe thresholds and animation behavior

---

### 3. ⚠️ All Screens - Missing Icon Resources

**Issue:** Using Material Icons instead of custom drawable icons

**Original:**
```kotlin
painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24)
painter = painterResource(id = R.drawable.ic_baseline_search_24)
painter = painterResource(id = R.drawable.ic_baseline_settings_24)
```

**Migrated:**
```kotlin
imageVector = Icons.Default.ArrowBack
imageVector = Icons.Default.Search
imageVector = Icons.Default.Settings
```

**Impact:** Icons look different from original design

---

## 📦 Components to Migrate

### Priority 1: Critical (Blocks exact parity)

1. **Custom Swipeable.kt** ✅ HIGH
   - **From:** `/presentation/src/main/java/com/veles/purchase/presentation/compose/Swipeable.kt`
   - **To:** `/shared/src/commonMain/kotlin/com/veles/purchase/presentation/compose/Swipeable.kt`
   - **Size:** ~957 lines
   - **Dependencies:** None (self-contained)
   - **Used by:** ListPurchaseFragment, ListLaterFragment

2. **SearchAppBar.kt** ✅ HIGH
   - **From:** `/presentation/src/main/java/com/veles/purchase/presentation/compose/search/SearchAppBar.kt`
   - **To:** `/shared/src/commonMain/kotlin/com/veles/purchase/presentation/compose/search/SearchAppBar.kt`
   - **Size:** ~258 lines
   - **Dependencies:** SearchWidgetState (enum)
   - **Used by:** ListPurchaseFragment, other list screens

3. **Colors.kt** ✅ HIGH
   - **From:** `/presentation/src/main/java/com/veles/purchase/presentation/presentation/compose/Colors.kt`
   - **To:** `/shared/src/commonMain/kotlin/com/veles/purchase/presentation/compose/Colors.kt`
   - **Used by:** ALL screens

4. **TextStyles** ✅ HIGH
   - **From:** textStyle1(), textStyle2() in presentation
   - **To:** `/shared/src/commonMain/kotlin/com/veles/purchase/presentation/compose/TextStyles.kt`
   - **Used by:** Purchase list, later list, etc.

### Priority 2: Important (Visual consistency)

5. **IconSquare.kt** ⚠️ MEDIUM
   - Custom icon wrapper component
   - Used in multiple toolbars
   - Size: ~50 lines

6. **Custom Card Components** ⚠️ MEDIUM
   - CollectionCard, PurchaseCard styling
   - Ensure exact padding, elevation, shapes

### Priority 3: Nice to have (Enhancement)

7. **Dialogs and Bottom Sheets** ⏳ LOW
   - Sort dialog
   - Filter dialog
   - Category selection

---

## 🎨 Icon Migration Plan

### Current State

**Icons in use:**
```
presentation/src/main/res/drawable/
├── ic_baseline_arrow_back_24.xml
├── ic_baseline_search_24.xml
├── ic_baseline_settings_24.xml
├── ic_baseline_add_24.xml
├── ic_baseline_close_24.xml
├── ic_baseline_done_24.xml
├── ic_baseline_edit_24.xml
├── ic_baseline_delete_24.xml
├── image.xml / no_image.xml
└── ... (50+ more)
```

### Migration Strategies

#### Option A: Convert to Compose ImageVector (Recommended ✅)
**Pros:**
- KMP compatible
- Type-safe
- No resource loading
- Better performance

**Cons:**
- Need to convert each XML to ImageVector code
- More code to maintain

**Implementation:**
```kotlin
// shared/src/commonMain/kotlin/com/veles/purchase/presentation/compose/icons/

Icons.kt
object PurchaseIcons {
    val ArrowBack: ImageVector
        get() = ... // Converted from XML
    val Search: ImageVector
        get() = ... // Converted from XML
    val Settings: ImageVector
        get() = ... // Converted from XML
}
```

#### Option B: Use Drawable Resources with expect/actual
**Pros:**
- Keep existing XML files
- Less code conversion

**Cons:**
- Platform-specific
- Not true KMP
- More complex

#### Option C: Use Compose Resources (New in Compose Multiplatform)
**Pros:**
- True multiplatform
- Can use existing XML/SVG
- Proper resource management

**Cons:**
- Requires setup
- Migration effort

**Recommendation:** **Option C** (Compose Resources) for true KMP support

---

## 🛠️ Detailed Fix Plan

### Phase 1: Migrate Core Components (Day 1-2)

**Task 1.1: Migrate Swipeable.kt** (3-4 hours)
```bash
Steps:
1. Copy Swipeable.kt to shared/commonMain
2. Remove Android-specific imports
3. Update package names
4. Test with PurchaseListScreen
5. Update ListLaterScreen to use it
6. Verify swipe behavior matches original
```

**Task 1.2: Migrate SearchAppBar.kt** (2-3 hours)
```bash
Steps:
1. Copy SearchAppBar.kt to shared/commonMain/compose/search/
2. Copy SearchWidgetState.kt
3. Remove Android resource dependencies
4. Update icon references
5. Test with PurchaseListScreen
6. Verify search functionality
```

**Task 1.3: Migrate Colors.kt** (1 hour)
```bash
Steps:
1. Read original Colors.kt
2. Create shared/commonMain/compose/Colors.kt
3. Copy all color definitions
4. Update all screens to use shared Colors
5. Verify color matching
```

**Task 1.4: Create TextStyles.kt** (1 hour)
```bash
Steps:
1. Extract textStyle1(), textStyle2() functions
2. Create shared TextStyles.kt
3. Add all text style functions
4. Update screens to use shared text styles
```

### Phase 2: Fix PurchaseListScreen (Day 2)

**Task 2.1: Replace SwipeToDismissBox** (2 hours)
```kotlin
// Change from:
SwipeToDismissBox(state = dismissState, ...) { }

// To:
SwipeToDismiss(
    state = dismissState,
    background = {},
    dismissThresholds = { FractionalThreshold(0.7f) }
) {
    val elevation = animateDpAsState(
        if (dismissState.dismissDirection == null) 0.dp else 4.dp
    ).value
    ItemPurchase(elevation = elevation, ...)
}
```

**Task 2.2: Replace Search Bar** (2 hours)
```kotlin
// Replace custom SearchTopBar with SearchTopAppBar
SearchTopAppBar(
    searchTextState = searchText,
    onTextChange = { viewModel.updateSearchText(it) },
    navigationIcon = {
        IconButton(onClick = { onNavigateBack() }) {
            Icon(painter = painterResource(R.drawable.ic_baseline_arrow_back_24), ...)
        }
    },
    title = {
        Text(text = collection.name, ...)
    },
    actions = { searchWidgetState ->
        IconButton(onClick = { searchWidgetState.value = SearchWidgetState.OPENED }) {
            Icon(painter = painterResource(R.drawable.ic_baseline_search_24), ...)
        }
        IconButton(onClick = { onNavigateToSettings() }) {
            Icon(painter = painterResource(R.drawable.ic_baseline_settings_24), ...)
        }
    }
)
```

**Task 2.3: Fix Sort Functionality** (1-2 hours)
```kotlin
// Add SortPurchase composable matching original
@Composable
fun SortPurchase(state: SortPurchaseState) {
    val sortPurchase = state.flowSortPurchase.collectAsState()
    Text(
        text = when (sortPurchase.value) {
            SortPurchase.SORTING_A_Z -> "Sorting A-Z"
            SortPurchase.SORTING_Z_A -> "Sorting Z-A"
            SortPurchase.SORTING_DATA_NEW -> "Sorting: Newest First"
            SortPurchase.SORTING_DATA_OLD -> "Sorting: Oldest First"
            SortPurchase.SORTING_CHECK -> "Sorting: Checked First"
            SortPurchase.SORTING_UNCHECK -> "Sorting: Unchecked First"
        },
        color = Colors.gr,
        modifier = Modifier
            .clickable { state.onSortClicked() }
            .fillMaxWidth()
            .padding(16.dp)
    )
}
```

**Task 2.4: Match All Padding/Sizes** (1 hour)
```kotlin
// Verify exact match for:
- Card padding: start = 16.dp, end = 16.dp
- Item spacing: verticalArrangement = Arrangement.spacedBy(8.dp)
- Text padding: horizontal = 8.dp
- Chip padding: top = 6.dp
- Height: 20.dp for chips
```

### Phase 3: Fix All Other Screens (Day 3)

**Task 3.1: Audit Each Screen**
- [ ] CollectionListScreen
- [ ] CollectionEditScreen
- [ ] PurchaseEditScreen
- [ ] CategoryScreen
- [ ] HistoryScreen
- [ ] ListLaterScreen
- [ ] SkuListScreen
- [ ] SkuEditScreen
- [ ] SkuStatisticsScreen
- [ ] BiometricScreen
- [ ] SettingsPurchaseScreen

**Task 3.2: For Each Screen:**
1. Read original from presentation module
2. Compare with migrated version
3. List differences in padding, colors, sizes
4. Update to match exactly
5. Test visual parity

### Phase 4: Icon Migration (Day 4)

**Task 4.1: Setup Compose Resources** (2 hours)
```kotlin
// In shared/build.gradle.kts
compose.resources {
    publicResClass = true
    packageOfResClass = "com.veles.purchase.resources"
    generateResClass = always
}
```

**Task 4.2: Copy Icon Resources** (2 hours)
```bash
# Copy all drawables from presentation to shared
cp -r presentation/src/main/res/drawable/* \
    shared/src/commonMain/composeResources/drawable/
```

**Task 4.3: Update Icon Usage** (3-4 hours)
```kotlin
// From:
Icon(imageVector = Icons.Default.ArrowBack, ...)

// To:
Icon(painter = painterResource(Res.drawable.ic_baseline_arrow_back_24), ...)
```

### Phase 5: Testing & Verification (Day 5)

**Task 5.1: Visual Testing**
- [ ] Compare each screen side-by-side with original
- [ ] Verify all colors match
- [ ] Verify all padding/spacing matches
- [ ] Verify all fonts/sizes match
- [ ] Verify all icons match

**Task 5.2: Functional Testing**
- [ ] Test swipe-to-delete (exact threshold)
- [ ] Test search functionality
- [ ] Test sort functionality
- [ ] Test navigation
- [ ] Test all button actions

**Task 5.3: Build Testing**
```bash
./gradlew :shared:compileDebugKotlinAndroid
./gradlew :androidApp:assembleDebug
# Verify no errors
```

---

## 📊 Migration Checklist

### Components to Migrate
- [ ] Swipeable.kt (957 lines)
- [ ] SearchAppBar.kt (258 lines)
- [ ] SearchWidgetState.kt (enum)
- [ ] Colors.kt
- [ ] TextStyles.kt
- [ ] IconSquare.kt
- [ ] 50+ drawable icons

### Screens to Fix
- [ ] PurchaseListScreen (CRITICAL)
- [ ] ListLaterScreen
- [ ] CollectionListScreen
- [ ] CollectionEditScreen
- [ ] PurchaseEditScreen
- [ ] CategoryScreen
- [ ] HistoryScreen
- [ ] SkuListScreen
- [ ] SkuEditScreen
- [ ] SkuStatisticsScreen
- [ ] BiometricScreen
- [ ] SettingsPurchaseScreen

### Verification
- [ ] All custom components migrated
- [ ] All screens match originals exactly
- [ ] All icons migrated
- [ ] Build successful
- [ ] Visual parity confirmed
- [ ] Functional parity confirmed

---

## 📝 Implementation Order

### Immediate (Today)
1. ✅ Migrate Swipeable.kt to shared
2. ✅ Migrate SearchAppBar.kt to shared
3. ✅ Migrate Colors.kt to shared
4. ✅ Fix PurchaseListScreen completely

### Tomorrow
5. Migrate TextStyles.kt
6. Fix ListLaterScreen
7. Audit and fix all other screens

### Next
8. Setup Compose Resources
9. Migrate all icons
10. Final testing and verification

---

## 🎯 Success Criteria

**Screen is considered "fixed" when:**
1. ✅ Uses exact same components as original
2. ✅ All padding, spacing, sizes match exactly
3. ✅ All colors match exactly
4. ✅ All fonts and text styles match
5. ✅ All icons match
6. ✅ All animations and behaviors match
7. ✅ Search functionality works identically
8. ✅ Swipe behavior matches (threshold, animation)
9. ✅ Build successful
10. ✅ Side-by-side comparison shows no differences

---

## 📚 References

**Original Files:**
- `/presentation/src/main/java/com/veles/purchase/presentation/compose/Swipeable.kt`
- `/presentation/src/main/java/com/veles/purchase/presentation/compose/search/SearchAppBar.kt`
- `/presentation/src/main/java/com/veles/purchase/presentation/presentation/compose/Colors.kt`
- `/presentation/src/main/java/com/veles/purchase/presentation/presentation/mvvm/purchase/list/ListPurchaseFragment.kt`

**Target Files:**
- `/shared/src/commonMain/kotlin/com/veles/purchase/presentation/compose/Swipeable.kt`
- `/shared/src/commonMain/kotlin/com/veles/purchase/presentation/compose/search/SearchAppBar.kt`
- `/shared/src/commonMain/kotlin/com/veles/purchase/presentation/compose/Colors.kt`
- `/shared/src/commonMain/kotlin/com/veles/purchase/presentation/compose/purchase/list/PurchaseListScreen.kt`

---

**Status:** 🚨 Ready to begin fixes
**Priority:** HIGH
**Estimated Time:** 4-5 days for complete parity
**Next Action:** Start with Swipeable.kt migration
