# 🍎 Phase 4 - Step 2: iOS Simulator Testing

**Task:** Test all 12 screens on iOS simulator  
**Priority:** 🔴 **CRITICAL - After Android Testing**  
**Estimated Time:** 2-3 hours  
**Date:** December 25, 2025

---

## 🎯 Objective

Verify that all 12 migrated screens work correctly on iOS with mock data.

---

## 📋 Prerequisites

### Required:
- ✅ Xcode installed (latest version)
- ✅ iOS simulator configured (iOS 17.2+)
- ✅ iOS framework built successfully
- ✅ Phase 4 Step 1 complete (Android testing done)

### Verify iOS Build:
```bash
cd /Users/yuriimelnyk/StudioProjects/Purchase
./gradlew :shared:linkDebugFrameworkIosSimulatorArm64
```
Expected: `BUILD SUCCESSFUL`

---

## 🚀 Step-by-Step Testing Process

### Step 1: Open Xcode Project

```bash
# Navigate to project
cd /Users/yuriimelnyk/StudioProjects/Purchase

# Open Xcode project
open iosApp/iosApp.xcodeproj
```

**Wait for Xcode to fully load and index the project.**

---

### Step 2: Configure Simulator

1. **Select Scheme:** iosApp (top-left toolbar)
2. **Select Destination:** iPhone 16 Pro or iPhone 15 Pro
   - Must be iOS 17.2 or higher
   - Click on device dropdown next to scheme

3. **Verify Framework Linked:**
   - In Xcode, navigate to **iosApp** target
   - Go to **General** tab
   - Check **Frameworks, Libraries, and Embedded Content**
   - Should see `shared.framework`

---

### Step 3: Build and Run

#### Option A: Xcode GUI (Recommended)
1. Press `Cmd + B` to build (verify no errors)
2. Press `Cmd + R` to run
3. Wait for simulator to launch
4. App should install and open automatically

#### Option B: Command Line
```bash
# Build for simulator
xcodebuild -project iosApp/iosApp.xcodeproj \
  -scheme iosApp \
  -destination 'platform=iOS Simulator,name=iPhone 16 Pro' \
  build

# Run
xcodebuild -project iosApp/iosApp.xcodeproj \
  -scheme iosApp \
  -destination 'platform=iOS Simulator,name=iPhone 16 Pro' \
  test
```

**Expected Result:** App launches and shows MainScreen

---

### Step 4: Monitor Console

In Xcode:
1. Open **Debug Area** (`Cmd + Shift + Y`)
2. Select **Console** tab
3. Watch for:
   - ✅ App initialization
   - ✅ Koin setup complete
   - ✅ ViewModels created
   - ✅ Mock data loaded
   - ❌ Any errors or crashes

Filter console:
```
# Click filter icon in console
# Type: "purchase" or "error" or "koin"
```

---

### Step 5: Test Each Screen

Follow this checklist for **ALL 12 SCREENS** (same as Android):

---

## 📱 Screen Testing Checklist

### Screen 1: MainScreen (Dashboard) ⏳

**Location:** Entry point of app

#### Test Cases:
- [ ] **App launches** without crash
- [ ] **MainScreen displays** correctly
- [ ] **Drawer icon** visible in top bar
- [ ] **Tap drawer icon** - drawer opens (swipe from left also works)
- [ ] **8 menu items** visible in drawer:
  1. History Pays (with payment icon)
  2. List of collections (with collection icon)
  3. List of purchases (with purchase icon)
  4. Categories (with category icon)
  5. SKU items (with SKU icon)
  6. SKU Statistics (with chart icon)
  7. Settings (with settings icon)
  8. App Info (with info icon)
- [ ] **All icons render** correctly from drawable resources
- [ ] **Drawer closes** when tapping outside

#### Screenshot:
In simulator: `Cmd + S` or **File → Save Screen**

Save as: `screenshots/phase4/ios/01_mainscreen.png`

#### iOS-Specific Checks:
- [ ] Safe area handling (notch area)
- [ ] Status bar visible
- [ ] Navigation bar correct

#### Notes:
```
Status: ✅ PASS / ⚠️ PARTIAL / ❌ FAIL
iOS-specific issues: [List any]
Compared to Android: [Same/Different]
```

---

### Screen 2: CollectionsScreen ⏳

**Navigation:** Drawer → "List of collections"

#### Test Cases:
- [ ] **Navigate** from drawer to collections
- [ ] **Collections list** displays
- [ ] **Mock collections** visible (same data as Android)
- [ ] **Search bar** present at top
- [ ] **FAB button** visible (+ icon)
- [ ] **Tap collection** - navigates to detail
- [ ] **Swipe to delete** works (0.7f threshold)
- [ ] **Back button** - returns to MainScreen

#### Screenshot:
Save as: `screenshots/phase4/ios/02_collections_list.png`

#### iOS-Specific Checks:
- [ ] List scrolling smooth
- [ ] Swipe gesture natural
- [ ] Safe area respected

#### Notes:
```
Status: ___________
iOS-specific issues: ___________
Mock data count matches Android: Y/N
```

---

### Screen 3: CollectionEditScreen ⏳

**Navigation:** Collections → FAB (+) or tap collection

#### Test Cases:
- [ ] **Edit screen** opens
- [ ] **Keyboard appears** when tapping name field
- [ ] **iOS keyboard** works correctly
- [ ] **Image picker** UI shown
- [ ] **Save button** works
- [ ] **Cancel button** works

#### Screenshot:
Save as: `screenshots/phase4/ios/03_collection_edit.png`

#### iOS-Specific Checks:
- [ ] Keyboard doesn't cover fields
- [ ] Dismiss keyboard works
- [ ] Safe area handling

#### Notes:
```
Status: ___________
iOS-specific issues: ___________
```

---

### Screen 4: PurchaseListScreen ⏳

**Navigation:** Drawer → "List of purchases"

#### Test Cases:
- [ ] **Purchase list** displays
- [ ] **Mock purchases** visible (same as Android)
- [ ] **Currency (UAH)** displays correctly
- [ ] **Dates** formatted correctly
- [ ] **Search** works
- [ ] **Filter** works
- [ ] **Swipe to delete** works

#### Screenshot:
Save as: `screenshots/phase4/ios/04_purchase_list.png`

#### iOS-Specific Checks:
- [ ] List performance (scroll fps)
- [ ] Swipe gesture threshold (0.7f)

#### Notes:
```
Status: ___________
Issues: ___________
```

---

### Screen 5: PurchaseEditScreen ⏳

**Navigation:** Purchases → FAB (+) or tap purchase

#### Test Cases:
- [ ] **Edit screen** opens
- [ ] **All fields** editable
- [ ] **Number keyboard** for price field
- [ ] **Date picker** works (iOS style)
- [ ] **Pickers** work correctly
- [ ] **Save** works
- [ ] **Delete** works (edit mode)

#### Screenshot:
Save as: `screenshots/phase4/ios/05_purchase_edit.png`

#### iOS-Specific Checks:
- [ ] iOS date picker displays
- [ ] iOS number keyboard for price
- [ ] Picker UI native feel

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
- [ ] **Mock categories** visible
- [ ] **Colors** render correctly
- [ ] **Add/Edit/Delete** work
- [ ] **Search** works

#### Screenshot:
Save as: `screenshots/phase4/ios/06_categories.png`

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
- [ ] **Mock history** visible
- [ ] **Date grouping** works
- [ ] **Timestamps** formatted correctly

#### Screenshot:
Save as: `screenshots/phase4/ios/07_history.png`

#### Notes:
```
Status: ___________
Issues: ___________
```

---

### Screen 8: BiometricAuthScreen ⏳

**Navigation:** Settings → Enable biometric

#### Test Cases:
- [ ] **Screen displays**
- [ ] **iOS stub implementation** shown
- [ ] **No crash** (iOS biometric not implemented yet)
- [ ] **Cancel** works

#### Screenshot:
Save as: `screenshots/phase4/ios/08_biometric.png`

#### iOS Notes:
```
Status: ___________
Note: iOS biometric is stub implementation - expected
```

---

### Screen 9: ListLaterScreen ⏳

**Navigation:** From purchase edit or drawer

#### Test Cases:
- [ ] **List displays**
- [ ] **Mock items** visible
- [ ] **Actions work**

#### Screenshot:
Save as: `screenshots/phase4/ios/09_list_later.png`

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
- [ ] **Mock SKUs** visible
- [ ] **Photo indicators** work (image/no_image icons from resources)
- [ ] **Search/Filter** work

#### Screenshot:
Save as: `screenshots/phase4/ios/10_sku_list.png`

#### iOS-Specific Checks:
- [ ] Image indicators from drawable resources render
- [ ] Icons tint correctly

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
- [ ] **All fields** work
- [ ] **Photo picker** UI shown
- [ ] **Save/Delete** work

#### Screenshot:
Save as: `screenshots/phase4/ios/11_sku_edit.png`

#### Notes:
```
Status: ___________
Issues: ___________
```

---

### Screen 12: SkuStatisticsScreen ⏳

**Navigation:** Drawer → "SKU Statistics"

#### Test Cases:
- [ ] **Statistics** display
- [ ] **Mock data** shown
- [ ] **Charts** render (if any)

#### Screenshot:
Save as: `screenshots/phase4/ios/12_sku_statistics.png`

#### Notes:
```
Status: ___________
Issues: ___________
```

---

## 📊 Test Results Summary

### Overall Results:

**Date Tested:** December 25, 2025  
**Platform:** iOS  
**Simulator:** iPhone ___________  
**iOS Version:** 17.2+  
**App Version:** Debug  

### Screens Summary:

| # | Screen Name | Status | vs Android | iOS-Specific Issues |
|---|-------------|--------|------------|---------------------|
| 1 | MainScreen | ⏳ | | |
| 2 | CollectionsScreen | ⏳ | | |
| 3 | CollectionEditScreen | ⏳ | | |
| 4 | PurchaseListScreen | ⏳ | | |
| 5 | PurchaseEditScreen | ⏳ | | |
| 6 | CategoryManagementScreen | ⏳ | | |
| 7 | HistoryScreen | ⏳ | | |
| 8 | BiometricAuthScreen | ⏳ | Expected: Stub | |
| 9 | ListLaterScreen | ⏳ | | |
| 10 | SkuListScreen | ⏳ | | |
| 11 | SkuEditScreen | ⏳ | | |
| 12 | SkuStatisticsScreen | ⏳ | | |

**Pass Rate:** ____ / 12 (___%)

---

## 🐛 Issues Found (iOS-Specific)

### Critical Issues (🔴):
```
1. [Issue description]
   Screen: [Screen name]
   iOS-specific: Y/N
   
2. ...
```

### High Priority (🟠):
```
1. [Issue description]
```

### Platform Differences:
```
1. [Difference from Android]
   Expected: Y/N
   Fix needed: Y/N
```

---

## 📸 Screenshots

All screenshots saved to:
```
screenshots/phase4/ios/
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
- [ ] Compared with Android results
- [ ] Test results table filled
- [ ] Console logs reviewed
- [ ] Mock data verified matching Android
- [ ] Overall pass/fail determined

---

## 🎯 Success Criteria

### Minimum (MVP):
- [ ] ✅ At least 10/12 screens pass (83%)
- [ ] ✅ No critical iOS-specific crashes
- [ ] ✅ Navigation works like Android
- [ ] ✅ Mock data displays same as Android
- [ ] ✅ Drawable resources render on iOS

### Ideal:
- [ ] 🎯 12/12 screens pass (100%)
- [ ] 🎯 Behavior identical to Android
- [ ] 🎯 No high-priority iOS issues
- [ ] 🎯 Performance comparable to Android

---

## 🔍 iOS-Specific Things to Check

### Resources:
- [ ] **Drawable icons** load from compose-resources
- [ ] **Image indicators** (image/no_image) work
- [ ] **Material Icons** render correctly
- [ ] **ColorFilter.tint()** applies correctly

### UI/UX:
- [ ] **Safe area** respected (notch, home indicator)
- [ ] **Navigation bar** height correct
- [ ] **Status bar** style correct
- [ ] **Keyboard behavior** natural
- [ ] **Gestures** feel native (swipe, tap)

### Performance:
- [ ] **App launch time** acceptable
- [ ] **Navigation** smooth (no lag)
- [ ] **List scrolling** 60fps
- [ ] **Memory usage** stable

### Known Limitations:
- [ ] **Biometric auth** - stub only (expected)
- [ ] **Some Android features** may differ on iOS

---

## 🚀 Next Steps

### If All Tests Pass:
1. ✅ Mark Step 2 complete
2. ✅ Create `PHASE_4_IOS_TEST_RESULTS.md`
3. ➡️ Move to **Step 3: Platform Comparison**

### If Issues Found:
1. Document all iOS-specific issues
2. Compare with Android behavior
3. Determine: Is this a bug or platform difference?
4. Prioritize fixes
5. Decide: Fix now or defer?

---

## 💡 iOS Testing Tips

### General:
- Compare behavior with Android version
- Note platform differences (iOS vs Android patterns)
- Test with both tap and swipe gestures
- Check safe area handling
- Monitor console for resource loading

### What to Look For (iOS):
- ✅ Same functionality as Android
- ✅ Drawable resources load correctly
- ✅ Icons tint properly
- ✅ Safe area respected
- ✅ Native iOS feel (keyboard, pickers, gestures)

### Common iOS Issues:
- ❌ Resources not found (framework bundling)
- ❌ Icons don't render (drawable XML issues)
- ❌ Keyboard covers fields
- ❌ Safe area not handled
- ❌ Gestures don't work
- ❌ Performance slower than Android

---

## 📞 Quick Commands Reference

### Build iOS Framework:
```bash
./gradlew :shared:linkDebugFrameworkIosSimulatorArm64
```

### Open Xcode:
```bash
open iosApp/iosApp.xcodeproj
```

### Clean Build:
```bash
./gradlew clean
./gradlew :shared:linkDebugFrameworkIosSimulatorArm64
```

### In Xcode:
- **Build:** `Cmd + B`
- **Run:** `Cmd + R`
- **Stop:** `Cmd + .`
- **Screenshot:** `Cmd + S` (in simulator)
- **Console:** `Cmd + Shift + Y`

### Framework Location:
```
shared/build/bin/iosSimulatorArm64/debugFramework/shared.framework
```

### Check Framework Resources:
```bash
ls -la shared/build/bin/iosSimulatorArm64/debugFramework/shared.framework/compose-resources/
```

---

## 🔧 Troubleshooting

### App Won't Build:
1. Clean Gradle build:
   ```bash
   ./gradlew clean
   ```
2. Clean Xcode build:
   - Xcode: **Product → Clean Build Folder** (`Cmd + Shift + K`)
3. Rebuild framework:
   ```bash
   ./gradlew :shared:linkDebugFrameworkIosSimulatorArm64
   ```
4. Rebuild in Xcode

### App Crashes on Launch:
1. Check Xcode console for error
2. Verify framework linked correctly
3. Check Koin initialization
4. Verify compose-resources bundled

### Resources Not Loading:
1. Check framework has `compose-resources/` folder
2. Verify drawable XMLs are in framework
3. Check `copyComposeResourcesToIosFramework` task ran
4. Rebuild framework

### Icons Don't Render:
1. Check drawable XML has no `android:tint`
2. Verify `android:fillColor="#000000"` set
3. Check `ColorFilter.tint()` used in code
4. Rebuild framework

---

**Status:** 📋 **READY TO START** (After Android testing)  
**Estimated Time:** 2-3 hours  
**Priority:** 🔴 **CRITICAL**  

**Let's test the iOS app! 🍎**

