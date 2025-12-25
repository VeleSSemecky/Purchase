# 🧪 Phase 4 - Step 1: Android Emulator Testing

**Task:** Test all 12 screens on Android emulator  
**Priority:** 🔴 **CRITICAL - START HERE**  
**Estimated Time:** 2-3 hours  
**Date:** December 25, 2025

---

## 🎯 Objective

Verify that all 12 migrated screens work correctly on Android with mock data.

---

## 📋 Prerequisites

### Required:
- ✅ Android Studio installed
- ✅ Android emulator configured (API 34 recommended)
- ✅ Phase 3 complete (iOS build successful)
- ✅ Project builds without errors

### Check Build Status:
```bash
cd /Users/yuriimelnyk/StudioProjects/Purchase
./gradlew :androidApp:assembleDebug
```
Expected: `BUILD SUCCESSFUL`

---

## 🚀 Step-by-Step Testing Process

### Step 1: Start Android Emulator

#### Option A: From Android Studio
1. Open Android Studio
2. Go to **Tools → Device Manager**
3. Select an emulator (e.g., Pixel 5 API 34)
4. Click **Play** button to start

#### Option B: Command Line
```bash
# List available emulators
emulator -list-avds

# Start specific emulator
emulator -avd Pixel_5_API_34 &

# Wait for emulator to boot (30-60 seconds)
```

### Verify Emulator Running:
```bash
adb devices
# Should show: emulator-5554    device
```

---

### Step 2: Install and Launch App

```bash
# Navigate to project
cd /Users/yuriimelnyk/StudioProjects/Purchase

# Clean previous builds
./gradlew clean

# Build and install app
./gradlew :androidApp:installDebug

# Launch app
adb shell am start -n com.veles.purchase.android/.MainActivity
```

**Expected Result:** App launches and shows MainScreen

---

### Step 3: Monitor Logs

Open a second terminal window:

```bash
# Monitor all logs
adb logcat | grep -i "purchase\|error\|exception"

# Or filter specific tags
adb logcat -s "Purchase" "MainActivity" "Koin"

# Clear logs first for clean start
adb logcat -c
```

**Look for:**
- ✅ Koin initialization successful
- ✅ ViewModels created
- ✅ Mock data loaded
- ❌ Any errors or exceptions

---

### Step 4: Test Each Screen

Follow this checklist for **ALL 12 SCREENS**:

---

## 📱 Screen Testing Checklist

### Screen 1: MainScreen (Dashboard) ⏳

**Location:** Entry point of app

#### Test Cases:
- [ ] **App launches** without crash
- [ ] **MainScreen displays** correctly
- [ ] **Drawer icon** visible in top bar
- [ ] **Tap drawer icon** - drawer opens
- [ ] **8 menu items** visible in drawer:
  1. History Pays (with payment icon)
  2. List of collections (with collection icon)
  3. List of purchases (with purchase icon)
  4. Categories (with category icon)
  5. SKU items (with SKU icon)
  6. SKU Statistics (with chart icon)
  7. Settings (with settings icon)
  8. App Info (with info icon)
- [ ] **All icons render** correctly (black on white background)
- [ ] **Drawer closes** when tapping outside

#### Screenshot:
```bash
# Take screenshot
adb exec-out screencap -p > screenshots/phase4/android/01_mainscreen.png
```

#### Notes:
```
Status: ✅ PASS / ⚠️ PARTIAL / ❌ FAIL
Issues: [List any issues]
```

---

### Screen 2: CollectionsScreen ⏳

**Navigation:** Drawer → "List of collections"

#### Test Cases:
- [ ] **Navigate** from drawer to collections
- [ ] **Collections list** displays
- [ ] **Mock collections** visible (should have sample data)
- [ ] **Search bar** present at top
- [ ] **FAB button** visible (+ icon)
- [ ] **Tap collection** - navigates to detail
- [ ] **Swipe collection** - shows delete option
- [ ] **Back button** - returns to MainScreen

#### Screenshot:
```bash
adb exec-out screencap -p > screenshots/phase4/android/02_collections_list.png
```

#### Mock Data Verification:
- [ ] Collection names visible
- [ ] Item counts shown
- [ ] Images display (or placeholder)

#### Notes:
```
Status: ___________
Issues: ___________
Mock data count: ___________
```

---

### Screen 3: CollectionEditScreen ⏳

**Navigation:** Collections → FAB (+) or tap collection

#### Test Cases (Create Mode):
- [ ] **Tap FAB** on collections screen
- [ ] **Edit screen** opens
- [ ] **Title** shows "New Collection" or similar
- [ ] **Name field** editable
- [ ] **Image picker** UI shown
- [ ] **Save button** visible
- [ ] **Cancel button** works

#### Test Cases (Edit Mode):
- [ ] **Tap existing collection**
- [ ] **Name pre-filled** with mock data
- [ ] **Delete button** visible (edit mode)
- [ ] **Save updates** collection

#### Screenshot:
```bash
adb exec-out screencap -p > screenshots/phase4/android/03_collection_edit.png
```

#### Notes:
```
Status: ___________
Issues: ___________
```

---

### Screen 4: PurchaseListScreen ⏳

**Navigation:** Drawer → "List of purchases"

#### Test Cases:
- [ ] **Purchase list** displays
- [ ] **Mock purchases** visible
- [ ] **Purchase items** show:
  - [ ] Name
  - [ ] Price (UAH currency)
  - [ ] Date
  - [ ] Category
- [ ] **Search bar** works
- [ ] **Filter button** present
- [ ] **FAB** for new purchase
- [ ] **Tap purchase** - navigates to detail
- [ ] **Swipe to delete** works

#### Screenshot:
```bash
adb exec-out screencap -p > screenshots/phase4/android/04_purchase_list.png
```

#### Mock Data Verification:
- [ ] Multiple purchases shown
- [ ] Dates formatted correctly
- [ ] Prices show UAH symbol
- [ ] Categories display

#### Notes:
```
Status: ___________
Issues: ___________
Purchase count: ___________
```

---

### Screen 5: PurchaseEditScreen ⏳

**Navigation:** Purchases → FAB (+) or tap purchase

#### Test Cases:
- [ ] **Edit screen** opens
- [ ] **Name field** editable
- [ ] **Price field** accepts numbers
- [ ] **Date picker** button present
- [ ] **Category selector** works
- [ ] **Collection selector** shows mock collections
- [ ] **Save button** works
- [ ] **Cancel button** works
- [ ] **Delete button** (edit mode only)

#### Screenshot:
```bash
adb exec-out screencap -p > screenshots/phase4/android/05_purchase_edit.png
```

#### Notes:
```
Status: ___________
Issues: ___________
```

---

### Screen 6: CategoryManagementScreen ⏳

**Navigation:** Drawer → "Categories"

#### Test Cases:
- [ ] **Category list** displays
- [ ] **Mock categories** visible with:
  - [ ] Category names
  - [ ] Category colors
  - [ ] Usage counts
- [ ] **Add category** button works
- [ ] **Edit category** works
- [ ] **Delete category** works
- [ ] **Search** works

#### Screenshot:
```bash
adb exec-out screencap -p > screenshots/phase4/android/06_categories.png
```

#### Mock Data Verification:
- [ ] Default categories present
- [ ] Colors display correctly
- [ ] Counts shown

#### Notes:
```
Status: ___________
Issues: ___________
```

---

### Screen 7: HistoryScreen ⏳

**Navigation:** Drawer → "History Pays"

#### Test Cases:
- [ ] **History list** displays
- [ ] **Mock history entries** visible
- [ ] **Entries grouped** by date
- [ ] **Each entry shows**:
  - [ ] Action type (created/updated/deleted)
  - [ ] Item name
  - [ ] Timestamp
- [ ] **Search** works
- [ ] **Filter** works

#### Screenshot:
```bash
adb exec-out screencap -p > screenshots/phase4/android/07_history.png
```

#### Notes:
```
Status: ___________
Issues: ___________
```

---

### Screen 8: BiometricAuthScreen ⏳

**Navigation:** Drawer → Settings → Enable biometric (if configured)

#### Test Cases:
- [ ] **Auth screen** displays
- [ ] **Biometric prompt** shows (if device supports)
- [ ] **Fallback UI** shown (if no biometric)
- [ ] **Cancel button** works
- [ ] **Success flow** works
- [ ] **Failure flow** handled

#### Screenshot:
```bash
adb exec-out screencap -p > screenshots/phase4/android/08_biometric.png
```

#### Notes:
```
Status: ___________
Issues: ___________
Device has biometric: Y/N
```

---

### Screen 9: ListLaterScreen ⏳

**Navigation:** Drawer → "Buy Later" (if in menu) or from purchase edit

#### Test Cases:
- [ ] **"Buy later" list** displays
- [ ] **Mock deferred items** visible
- [ ] **Move to active** button works
- [ ] **Delete** button works
- [ ] **Empty state** shown (if no items)

#### Screenshot:
```bash
adb exec-out screencap -p > screenshots/phase4/android/09_list_later.png
```

#### Notes:
```
Status: ___________
Issues: ___________
```

---

### Screen 10: SkuListScreen ⏳

**Navigation:** Drawer → "SKU items"

#### Test Cases:
- [ ] **SKU list** displays
- [ ] **Mock SKUs** visible with:
  - [ ] SKU names
  - [ ] Prices
  - [ ] Categories
  - [ ] Photo indicators (image/no_image icons)
- [ ] **Search** works
- [ ] **Filter** works
- [ ] **FAB** for new SKU
- [ ] **Tap SKU** - navigates to detail

#### Screenshot:
```bash
adb exec-out screencap -p > screenshots/phase4/android/10_sku_list.png
```

#### Mock Data Verification:
- [ ] Multiple SKUs shown
- [ ] Image indicators correct
- [ ] Prices display

#### Notes:
```
Status: ___________
Issues: ___________
```

---

### Screen 11: SkuEditScreen ⏳

**Navigation:** SKU List → FAB (+) or tap SKU

#### Test Cases:
- [ ] **Edit screen** opens
- [ ] **Name field** editable
- [ ] **Price field** works
- [ ] **Category selector** works
- [ ] **Photo picker** UI shown
- [ ] **Multiple photos** support shown
- [ ] **Save button** works
- [ ] **Delete button** (edit mode)

#### Screenshot:
```bash
adb exec-out screencap -p > screenshots/phase4/android/11_sku_edit.png
```

#### Notes:
```
Status: ___________
Issues: ___________
```

---

### Screen 12: SkuStatisticsScreen ⏳

**Navigation:** Drawer → "SKU Statistics"

#### Test Cases:
- [ ] **Statistics screen** displays
- [ ] **Mock SKU data** shown
- [ ] **Price trends** visible
- [ ] **Purchase frequency** shown
- [ ] **Charts render** (if implemented)
- [ ] **Date range selector** works

#### Screenshot:
```bash
adb exec-out screencap -p > screenshots/phase4/android/12_sku_statistics.png
```

#### Mock Data Verification:
- [ ] Historical data shown
- [ ] Statistics calculated correctly

#### Notes:
```
Status: ___________
Issues: ___________
```

---

## 📊 Test Results Summary

### Overall Results:

**Date Tested:** December 25, 2025  
**Platform:** Android  
**Emulator:** ___________  
**App Version:** Debug  

### Screens Summary:

| # | Screen Name | Status | Critical Issues | Notes |
|---|-------------|--------|-----------------|-------|
| 1 | MainScreen | ⏳ | | |
| 2 | CollectionsScreen | ⏳ | | |
| 3 | CollectionEditScreen | ⏳ | | |
| 4 | PurchaseListScreen | ⏳ | | |
| 5 | PurchaseEditScreen | ⏳ | | |
| 6 | CategoryManagementScreen | ⏳ | | |
| 7 | HistoryScreen | ⏳ | | |
| 8 | BiometricAuthScreen | ⏳ | | |
| 9 | ListLaterScreen | ⏳ | | |
| 10 | SkuListScreen | ⏳ | | |
| 11 | SkuEditScreen | ⏳ | | |
| 12 | SkuStatisticsScreen | ⏳ | | |

**Pass Rate:** ____ / 12 (___%)

---

## 🐛 Issues Found

### Critical Issues (🔴 - Fix Immediately):
```
1. [Issue description]
   Screen: [Screen name]
   Impact: [What breaks]
   
2. ...
```

### High Priority (🟠 - Fix Soon):
```
1. [Issue description]
   Screen: [Screen name]
   Impact: [What's affected]
```

### Medium Priority (🟡 - Fix Later):
```
1. [Issue description]
   Screen: [Screen name]
```

### Low Priority (🟢 - Nice to Have):
```
1. [Issue description]
   Screen: [Screen name]
```

---

## 📸 Screenshots

All screenshots saved to:
```
screenshots/phase4/android/
├── 01_mainscreen.png
├── 02_collections_list.png
├── 03_collection_edit.png
├── 04_purchase_list.png
├── 05_purchase_edit.png
├── 06_categories.png
├── 07_history.png
├── 08_biometric.png
├── 09_list_later.png
├── 10_sku_list.png
├── 11_sku_edit.png
└── 12_sku_statistics.png
```

---

## ✅ Completion Checklist

- [ ] All 12 screens tested
- [ ] All screenshots captured
- [ ] All issues documented
- [ ] Test results table filled
- [ ] Log files reviewed
- [ ] Mock data verified working
- [ ] Navigation flows tested
- [ ] Overall pass/fail determined

---

## 🎯 Success Criteria

### Minimum (MVP):
- [ ] ✅ At least 10/12 screens pass (83%)
- [ ] ✅ No critical bugs that crash app
- [ ] ✅ Navigation works
- [ ] ✅ Mock data displays

### Ideal:
- [ ] 🎯 12/12 screens pass (100%)
- [ ] 🎯 All features work
- [ ] 🎯 No high-priority bugs
- [ ] 🎯 Good performance

---

## 🚀 Next Steps

### If All Tests Pass:
1. ✅ Mark Step 1 complete
2. ✅ Create `PHASE_4_ANDROID_TEST_RESULTS.md` with this data
3. ➡️ Move to **Step 2: iOS Testing**

### If Issues Found:
1. Document all issues clearly
2. Prioritize fixes
3. Decide: Fix now or defer to Step 4?
4. If critical bugs: Fix immediately
5. If minor bugs: Continue to iOS testing

---

## 💡 Testing Tips

### General:
- Test one screen thoroughly before moving to next
- Take clear, full-screen screenshots
- Note any console errors
- Test both happy path and error cases

### What to Look For:
- ✅ Screen loads without crash
- ✅ Mock data appears
- ✅ Navigation works
- ✅ Icons render correctly
- ✅ Buttons are clickable
- ✅ Text is readable
- ✅ No obvious visual bugs

### Common Issues:
- ❌ App crashes on screen load
- ❌ Mock data doesn't show
- ❌ Navigation freezes
- ❌ Icons missing/broken
- ❌ Console errors
- ❌ Slow performance

---

## 📞 Quick Commands Reference

### Build & Install:
```bash
./gradlew :androidApp:installDebug
```

### Launch App:
```bash
adb shell am start -n com.veles.purchase.android/.MainActivity
```

### Screenshot:
```bash
adb exec-out screencap -p > screenshots/phase4/android/[name].png
```

### View Logs:
```bash
adb logcat | grep -i purchase
```

### Restart App:
```bash
adb shell am force-stop com.veles.purchase.android
adb shell am start -n com.veles.purchase.android/.MainActivity
```

### Uninstall (if needed):
```bash
adb uninstall com.veles.purchase.android
```

---

**Status:** 📋 **READY TO START**  
**Estimated Time:** 2-3 hours  
**Priority:** 🔴 **CRITICAL**  

**Let's test the Android app! 🚀**

