# 🧪 Phase 3 Testing Guide - Android Emulator

**Date:** November 30, 2025  
**Purpose:** Systematic testing of all screens with mock data  
**Status:** ⏳ Ready to start

---

## 📋 Prerequisites

### 1. Android Emulator Setup
- **Device:** Pixel 6 or similar
- **API Level:** 34+ (Android 14+)
- **RAM:** 4GB+
- **Theme:** Dark theme enabled (matches app design)

### 2. Install APK
```bash
# Build and install in one command
cd /Users/yuriimelnyk/StudioProjects/Purchase
./gradlew :androidApp:installDebug

# Or use adb directly
adb install androidApp/build/outputs/apk/debug/androidApp-debug.apk
```

### 3. Launch App
```bash
# Launch via adb
adb shell am start -n com.example.androidapp/.MainActivity

# Or launch manually from emulator
```

---

## 🧪 Test Cases

### Test 1: MainScreen (Collection List) 🏠

**Expected Mock Data:**
- 3 collections should appear:
  - 🏠 Home
  - 🏢 Work
  - 🎁 Gifts

**Test Steps:**
1. ✅ App launches successfully
2. ✅ All 3 collections are visible
3. ✅ Collection names are displayed correctly
4. ✅ Icons/avatars appear for each collection
5. ✅ Tap on each collection navigates to PurchaseListScreen
6. ✅ FAB (Add Collection) button is visible and clickable
7. ✅ Settings icon in toolbar is visible

**UI Check:**
- [ ] Dark theme applied (black/dark gray background)
- [ ] Material3 components render correctly
- [ ] No visual glitches
- [ ] Smooth animations

**Screenshot:** 📸 `test1_mainscreen.png`

---

### Test 2: CollectionEditScreen (Create/Edit Collection) ✏️

**Test Steps:**
1. ✅ Tap FAB on MainScreen
2. ✅ CollectionEditScreen opens
3. ✅ Title says "Add Collection" or "Edit Collection"
4. ✅ Text field for collection name is visible
5. ✅ Can type collection name
6. ✅ Save button saves collection
7. ✅ Back button returns to MainScreen
8. ✅ Edit existing collection (tap on collection item)
9. ✅ Collection name pre-filled when editing

**UI Check:**
- [ ] Toolbar with back and save buttons
- [ ] TextField with proper styling
- [ ] Keyboard appears when tapping text field

**Screenshot:** 📸 `test2_collection_edit.png`

---

### Test 3: PurchaseListScreen (Purchase List with Search) 🛒

**Expected Mock Data:**
- ~15-20 purchases per collection
- Mix of items with and without photos
- Various categories
- Different prices

**Test Steps:**
1. ✅ Tap on "Home" collection from MainScreen
2. ✅ PurchaseListScreen opens
3. ✅ List of purchases appears with mock data
4. ✅ Each item shows:
   - Image indicator (image or no_image icon)
   - Purchase name (title)
   - Description
   - Price (in UAH)
   - Category chip
5. ✅ Tap search icon in toolbar
6. ✅ Search bar appears
7. ✅ Type search query (e.g., "milk")
8. ✅ List filters based on search
9. ✅ Clear search returns full list

**Swipe-to-Delete Test:**
1. ✅ Swipe purchase item left/right
2. ✅ Red delete background appears
3. ✅ Swipe past 70% threshold (0.7f)
4. ✅ Item is deleted
5. ✅ Swipe less than 70% returns item to position

**UI Check:**
- [ ] SearchTopAppBar (custom component)
- [ ] SwipeToDismiss (custom component, 0.7f threshold)
- [ ] Image indicators (image/no_image icons)
- [ ] Category chips with proper colors
- [ ] Price formatting (UAH currency)
- [ ] Smooth scrolling in LazyColumn

**Screenshot:** 
- 📸 `test3_purchase_list.png`
- 📸 `test3_purchase_search.png`
- 📸 `test3_purchase_swipe.png`

---

### Test 4: PurchaseEditScreen (Add/Edit Purchase) ✏️

**Test Steps:**
1. ✅ Tap FAB on PurchaseListScreen
2. ✅ PurchaseEditScreen opens
3. ✅ All fields visible:
   - Purchase name
   - Description
   - Price
   - Quantity
   - Category selection
   - Date picker
4. ✅ Can type in text fields
5. ✅ Can select category from dropdown
6. ✅ Can change date
7. ✅ Save button works
8. ✅ Back button returns to list
9. ✅ Edit existing purchase shows pre-filled data

**UI Check:**
- [ ] All TextFields with proper styling
- [ ] Dropdown/Category selector works
- [ ] Date picker appears and works
- [ ] Save button in toolbar

**Screenshot:** 📸 `test4_purchase_edit.png`

---

### Test 5: ListLaterScreen ("Buy Later" List) 📋

**Expected Mock Data:**
- 5-10 items marked for "buy later"

**Test Steps:**
1. ✅ Navigate to ListLaterScreen
2. ✅ List of "buy later" items appears
3. ✅ Each item shows:
   - Image indicator (image/no_image icon)
   - Item name
   - Checkbox (checked/unchecked)
4. ✅ Tap checkbox to mark as purchased
5. ✅ Swipe to delete works (0.7f threshold)
6. ✅ Can add new items

**Swipe-to-Delete Test:**
1. ✅ Swipe item left/right
2. ✅ Delete background appears
3. ✅ Item deleted after 70% threshold

**UI Check:**
- [ ] Checkboxes work correctly
- [ ] SwipeToDismiss with 0.7f threshold
- [ ] Image indicators visible
- [ ] Smooth list scrolling

**Screenshot:** 📸 `test5_list_later.png`

---

### Test 6: CategoryScreen (Category Management) 🏷️

**Expected Mock Data:**
- ~10 categories (Food, Electronics, Clothing, etc.)

**Test Steps:**
1. ✅ Navigate to CategoryScreen
2. ✅ List of categories appears
3. ✅ Each category shows name and icon
4. ✅ Can add new category (FAB)
5. ✅ Can edit existing category
6. ✅ Can delete category (swipe or button)

**UI Check:**
- [ ] Category list renders correctly
- [ ] Icons display properly
- [ ] Add/edit functionality works

**Screenshot:** 📸 `test6_category.png`

---

### Test 7: HistoryScreen (Purchase History) 📜

**Expected Mock Data:**
- 20-30 history records
- Various timestamps

**Test Steps:**
1. ✅ Navigate to HistoryScreen
2. ✅ List of history items appears
3. ✅ Each item shows:
   - Purchase name
   - Date/time (formatted)
   - Action (added/edited/deleted)
4. ✅ Items sorted by date (newest first)
5. ✅ Smooth scrolling

**UI Check:**
- [ ] Date formatting correct ("dd MMMM yyyy" format)
- [ ] Time icons visible
- [ ] Proper spacing and layout

**Screenshot:** 📸 `test7_history.png`

---

### Test 8: SettingsPurchaseScreen (Settings) ⚙️

**Expected Mock Settings:**
- Display preferences
- Theme settings
- Biometric enabled/disabled

**Test Steps:**
1. ✅ Navigate to SettingsPurchaseScreen
2. ✅ Settings options appear
3. ✅ Can toggle switches
4. ✅ Changes are reflected in UI
5. ✅ Settings saved (verified by reopening)

**UI Check:**
- [ ] Switches and toggles work
- [ ] Settings sections organized
- [ ] Proper spacing and styling

**Screenshot:** 📸 `test8_settings.png`

---

### Test 9: BiometricScreen (Biometric Authentication) 🔐

**Note:** May not work on emulator without biometric setup

**Test Steps:**
1. ✅ Navigate to BiometricScreen
2. ✅ Biometric prompt appears (if supported)
3. ✅ Can authenticate or cancel
4. ✅ Success/failure states handled

**UI Check:**
- [ ] Biometric UI appears correctly
- [ ] Fallback message if not supported

**Screenshot:** 📸 `test9_biometric.png` (if supported)

---

### Test 10: SkuListScreen (SKU List) 📦

**Expected Mock Data:**
- ~20 SKUs across multiple categories:
  - Fruits: Apple, Banana, Orange, etc.
  - Vegetables: Tomato, Cucumber, Carrot, etc.
  - Dairy: Milk, Cheese, Yogurt, etc.
  - Meat: Chicken, Beef, Pork, etc.

**Test Steps:**
1. ✅ Navigate to SkuListScreen
2. ✅ List of SKUs appears
3. ✅ Each SKU shows:
   - Name
   - Category
   - Icon/image
4. ✅ Can search/filter SKUs
5. ✅ Tap SKU navigates to SkuEditScreen
6. ✅ FAB to add new SKU
7. ✅ Statistics icon navigates to SkuStatisticsScreen

**UI Check:**
- [ ] Back button in toolbar
- [ ] Statistics icon in toolbar
- [ ] SKU list with proper styling
- [ ] Smooth scrolling

**Screenshot:** 📸 `test10_sku_list.png`

---

### Test 11: SkuEditScreen (Add/Edit SKU) ✏️

**Test Steps:**
1. ✅ Tap FAB on SkuListScreen
2. ✅ SkuEditScreen opens
3. ✅ All fields visible:
   - SKU name
   - Category
   - Description
   - Unit (kg, pcs, etc.)
4. ✅ Can type in fields
5. ✅ Save button works
6. ✅ Edit existing SKU shows pre-filled data

**UI Check:**
- [ ] TextFields styled correctly
- [ ] Dropdown/pickers work
- [ ] Save button in toolbar

**Screenshot:** 📸 `test11_sku_edit.png`

---

### Test 12: SkuStatisticsScreen (Spending Statistics) 📊

**Expected Mock Data:**
- Statistics grouped by SKU
- Total spending sum
- Data for selected year/month

**Test Steps:**
1. ✅ Navigate from SkuListScreen (statistics icon)
2. ✅ SkuStatisticsScreen opens
3. ✅ Toolbar shows:
   - Title: "Statistics"
   - Subtitle: Display period (e.g., "2025" or "January 2025")
4. ✅ Total sum displayed at top
5. ✅ List of SKUs with individual sums
6. ✅ Currency formatting (UAH)

**UI Check:**
- [ ] Total spending card at top
- [ ] SKU list with amounts
- [ ] Currency displayed correctly (UAH)
- [ ] Proper color scheme (dark theme)

**Screenshot:** 📸 `test12_sku_statistics.png`

---

## 📊 Navigation Flow Test

Test complete navigation path:

```
MainScreen (Collections)
  ↓ tap Home
PurchaseListScreen
  ↓ tap FAB
PurchaseEditScreen
  ↓ save
PurchaseListScreen
  ↓ back
MainScreen
  ↓ tap Settings
SettingsPurchaseScreen
  ↓ back
MainScreen
  ↓ navigate to SKU
SkuListScreen
  ↓ tap Statistics
SkuStatisticsScreen
  ↓ back
SkuListScreen
  ↓ back
MainScreen
```

**Check:**
- [ ] All navigation transitions smooth
- [ ] Back navigation works correctly
- [ ] No crashes or freezes
- [ ] Data persists across navigation

---

## 🐛 Bug Report Template

For each bug found, document:

```markdown
### Bug #X: [Title]
**Severity:** Critical / High / Medium / Low
**Screen:** [Screen name]
**Steps to Reproduce:**
1. ...
2. ...
3. ...

**Expected Result:**
...

**Actual Result:**
...

**Screenshot:** 📸 `bug_X.png`

**Device Info:**
- Emulator: Pixel 6
- API: 34 (Android 14)
- Build: Debug
```

---

## ✅ Test Results Summary

After testing, create summary in `PHASE_3_TESTING_RESULTS.md`:

```markdown
## Test Results Summary

**Date:** [Date]
**Tester:** [Name]
**Build:** Debug APK
**Device:** [Emulator details]

### Screens Tested: X/12

| Screen | Status | Notes |
|--------|--------|-------|
| MainScreen | ✅ Pass | ... |
| CollectionEditScreen | ✅ Pass | ... |
| PurchaseListScreen | ⚠️ Issues | Bug #1: ... |
| ... | ... | ... |

### Bugs Found: X

1. [Bug #1 description]
2. [Bug #2 description]
...

### Performance Notes:
- App launch time: ...
- Navigation smoothness: ...
- Memory usage: ...

### Next Steps:
- Fix Bug #1
- Fix Bug #2
- Retest affected screens
```

---

## 🚀 Quick Start

```bash
# 1. Build and install
cd /Users/yuriimelnyk/StudioProjects/Purchase
./gradlew :androidApp:installDebug

# 2. Launch app
adb shell am start -n com.example.androidapp/.MainActivity

# 3. Start testing!
# Follow test cases above systematically

# 4. Take screenshots
adb shell screencap -p /sdcard/screenshot.png
adb pull /sdcard/screenshot.png test_screenshots/

# 5. Check logs if issues occur
adb logcat | grep -i "purchase\|error\|exception"
```

---

## 📝 Notes

- **Mock Data:** All data comes from mockDomain module
- **Persistence:** Data changes are in-memory only (not persisted)
- **Swipe Threshold:** 0.7f (70%) - important to verify!
- **Theme:** Dark theme throughout
- **Colors:** Green accent (#4ACFAC) for highlights

---

**Ready to Test!** 🎯  
Follow the test cases systematically and document all findings.

