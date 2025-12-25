# 🔍 Missing Icons Audit - Detailed Comparison

**Date:** November 30, 2025  
**Purpose:** Identify all icons not properly migrated from presentation to shared  
**Status:** 🔄 In Progress

---

## 📊 Findings

### Missing Icons by Screen

#### 1. SkuListScreen ⚠️ MISSING ICONS

**Original (SkuListFragment.kt):**
- ✅ Back button: `ic_baseline_arrow_back_24`
- ✅ Statistics button: `ic_baseline_insert_chart_outlined_24`
- ✅ Add FAB: Material Icons.Add

**Migrated (SkuListScreen.kt):**
- ❌ Back button: **MISSING**
- ❌ Statistics button: **MISSING**
- ✅ Add FAB: Material Icons.Add (correct)

**Action Required:**
1. Add back navigation button with `Icons.AutoMirrored.Filled.ArrowBack`
2. Add statistics action button with `Res.drawable.ic_baseline_insert_chart_outlined_24`

---

#### 2. SkuEditScreen ⚠️ SIMPLIFIED (Documented)

**Original (SkuEditFragment.kt):**
- ✅ Back button: `ic_baseline_arrow_back_24`
- ✅ Save button: `ic_done_black_24dp`
- ✅ Add photo button: `ic_baseline_add_a_photo_24` (bottom bar)
- ✅ Calendar button: `ic_baseline_calendar_today_24` (bottom bar)

**Migrated (SkuEditScreen.kt):**
- ✅ Back button: Material Icons.ArrowBack (correct)
- ✅ Save button: Material Icons.Done (correct)
- 📝 Add photo button: **DEFERRED** (Phase 3+)
- 📝 Calendar button: **DEFERRED** (Phase 3+)

**Status:** Intentionally simplified (documented in Phase 2 docs)

---

#### 3. SkuStatisticsScreen ✅ CORRECT

**Original (OutlayGraphFragment.kt):**
- ✅ Back button: `ic_baseline_arrow_back_24`

**Migrated (SkuStatisticsScreen.kt):**
- ✅ Back button: Material Icons.ArrowBack (correct)

**Status:** Correct

---

#### 4. ListLaterScreen ⚠️ CHECK NEEDED

**Original (ListLaterPurchaseFragment.kt):**
Let me check what's used...

---

#### 5. HistoryScreen ⚠️ CHECK NEEDED

**Original (HistoryComposeFragment.kt):**
- Back button: `ic_baseline_arrow_back_24`
- Event type icons: `ic_press`
- Time icons: `ic_baseline_access_time_24`
- Image indicators: `image` / `no_image`

**Migrated (HistoryScreen.kt):**
- Back button: Material Icons.ArrowBack
- Event type icons: Emojis (intentional)
- Time icons: Emojis (intentional)
- Image indicators: Emoji (intentional)

**Status:** Using emojis intentionally (documented)

---

## 🎯 Icons to Fix

### High Priority - SkuListScreen

#### Missing Back Button
```kotlin
// Add to TopAppBar
navigationIcon = {
    IconButton(onClick = onNavigateBack) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = Color.White
        )
    }
}
```

#### Missing Statistics Button
```kotlin
// Add to TopAppBar actions
actions = {
    IconButton(onClick = onNavigateToStatistics) {
        Icon(
            painter = painterResource(Res.drawable.ic_baseline_insert_chart_outlined_24),
            contentDescription = "Statistics",
            tint = Color.White
        )
    }
}
```

---

## 📋 Complete Icon Inventory Comparison

### Icons in Presentation Module (Used)
1. ✅ `ic_baseline_arrow_back_24` - Back buttons (multiple screens)
2. ✅ `ic_baseline_menu` - Menu/drawer
3. ✅ `ic_baseline_search_24` - Search
4. ✅ `ic_baseline_settings_24` - Settings
5. ✅ `ic_baseline_payment_24` - Payment/History
6. ✅ `ic_baseline_camera_alt_24` - Camera/PIP
7. ✅ `ic_baseline_insert_chart_outlined_24` - Statistics ⚠️ **NOT IN SHARED**
8. ✅ `ic_done_black_24dp` - Save/Done
9. ✅ `ic_delete_black_24dp` - Delete
10. ✅ `ic_category` - Category
11. ✅ `ic_purchase_collections` - Collections
12. ✅ `ic_navigate_next` - Navigate forward
13. ✅ `ic_baseline_history_24` - History
14. ✅ `ic_baseline_add_a_photo_24` - Add photo (deferred)
15. ✅ `ic_baseline_calendar_today_24` - Calendar (deferred)
16. ✅ `ic_baseline_access_time_24` - Time (using emoji in shared)
17. ✅ `ic_press` - Event indicator (using emoji in shared)
18. ✅ `image` / `no_image` - Image placeholders (using emoji in shared)
19. ✅ `ic_error` - Error dialog
20. ✅ `ic_fortune_wheel` - Currency dialog
21. ✅ `ic_baseline_close_24` - Close
22. ✅ `ic_baseline_pause_24` - Video control (not migrated - PIP screen)
23. ✅ `ic_baseline_stop_24` - Video control (not migrated - PIP screen)
24. ✅ `ic_baseline_play_arrow_24` - Video control (not migrated - PIP screen)
25. ✅ `ic_baseline_cameraswitch_24` - Video control (not migrated - PIP screen)

### Icons in Shared Module (Used)
1. ✅ Material Icons.ArrowBack - Back buttons (most screens)
2. ✅ Material Icons.Menu - Menu/drawer
3. ✅ Material Icons.Search - Search
4. ✅ Material Icons.Settings - Settings
5. ✅ `Res.drawable.ic_baseline_payment_24` - Payment drawer
6. ✅ `Res.drawable.ic_baseline_camera_alt_24` - Camera drawer
7. ✅ `Res.drawable.ic_baseline_settings_24` - Settings drawer
8. ❌ **MISSING** `ic_baseline_insert_chart_outlined_24` - Statistics
9. ✅ Material Icons.Check/Done - Save/Done
10. ✅ Material Icons.Delete - Delete
11. ✅ `Res.drawable.ic_category` - Category
12. ✅ `Res.drawable.ic_purchase_collections` - Collections
13. ✅ `Res.drawable.ic_navigate_next` - Navigate forward
14. ✅ `Res.drawable.ic_baseline_history_24` - History
15. 📝 Deferred: Add photo
16. 📝 Deferred: Calendar
17. ✅ Emoji: Time (intentional)
18. ✅ Emoji: Event indicators (intentional)
19. ✅ Emoji: Image indicators (intentional)
20. ❌ Not migrated: Error dialog (screen not migrated)
21. ❌ Not migrated: Fortune wheel (dialog not migrated)
22. ✅ Material Icons.Close - Close
23. ❌ Not migrated: Video controls (PIP screen not migrated)

---

## 🔧 Required Fixes

### 1. SkuListScreen - Add Missing Icons

**File:** `shared/src/commonMain/kotlin/com/veles/purchase/presentation/compose/sku/list/SkuListScreen.kt`

**Changes Needed:**
1. Add back button to toolbar
2. Add statistics button to toolbar
3. Add imports for Res.drawable.ic_baseline_insert_chart_outlined_24
4. Add onNavigateToStatistics parameter

---

## 📊 Summary

### Icons Status
- **Fully Migrated:** 13 icons
- **Using Material Icons (Correct):** 6 icons
- **Using Emojis (Intentional):** 3 types
- **Missing in Shared:** 1 icon (statistics)
- **Not Migrated (Screens Not Done):** 6 icons (PIP, error, fortune wheel)
- **Deferred (Documented):** 2 icons (photo, calendar)

### Action Required
1. ✅ Fix SkuListScreen - add back button and statistics button

---

_Created: November 30, 2025_  
_Status: Audit Complete - Fix in Progress_

