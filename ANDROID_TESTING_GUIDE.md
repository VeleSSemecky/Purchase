# 📱 Android Emulator Testing Guide

**Date:** November 30, 2025  
**Purpose:** Systematic testing of all migrated screens  
**Status:** Ready to test

---

## 🎯 Prerequisites

### ✅ Build Status
```bash
BUILD SUCCESSFUL in 5s
✅ Android app builds correctly
✅ APK generated successfully
```

### Required Setup
- Android Studio installed
- Android emulator configured (Pixel 6 or similar)
- API Level 34+ (Android 14+)

---

## 🚀 Quick Start

### 1. Build and Install

```bash
cd /Users/yuriimelnyk/StudioProjects/Purchase

# Build the Android app
./gradlew :androidApp:assembleDebug

# Install on emulator (make sure emulator is running)
./gradlew :androidApp:installDebug

# Or use Android Studio:
# Run → Run 'androidApp'
```

### 2. Launch Emulator

**Option A: Android Studio**
- Tools → Device Manager → Start emulator

**Option B: Command Line**
```bash
# List available emulators
emulator -list-avds

# Start emulator
emulator -avd <avd_name>
```

---

## 📋 Systematic Testing Checklist

### Screen 1: MainScreen (Landing Page)

**Test:**
- [ ] App launches without crashes
- [ ] MainScreen displays
- [ ] Drawer menu button visible (top-left)
- [ ] Drawer opens on tap
- [ ] Drawer shows 4 items:
  - [ ] History (with payment icon)
  - [ ] Camera/PIP (with camera icon)
  - [ ] Settings (with settings icon)
  - [ ] Collections button visible

**What to check:**
- Colors match original
- Icons display correctly
- Navigation works
- Layout is correct

**Screenshot:** Take screenshot of MainScreen

---

### Screen 2: CollectionListScreen

**Navigate:** Tap "Collections" or main area

**Test:**
- [ ] Collections list displays
- [ ] Collections shown in 2-column grid
- [ ] Each collection card shows:
  - [ ] Collection name
  - [ ] Collection icon (📋)
  - [ ] Teal/green background color
- [ ] Swipe-to-delete works
- [ ] Long press shows delete dialog
- [ ] FAB (+ button) visible
- [ ] Tap FAB opens CollectionEditScreen

**What to check:**
- Grid layout (2 columns)
- Card elevation on swipe
- Colors (teal background)
- Icon displays correctly

**Screenshot:** Take screenshot of collections grid

---

### Screen 3: CollectionEditScreen

**Navigate:** Tap FAB on CollectionListScreen

**Test:**
- [ ] Edit screen displays
- [ ] Title field visible
- [ ] "Category settings" row visible with icons
- [ ] "History" row visible with icons
- [ ] User list displays (if any)
- [ ] Back button works
- [ ] Save button works
- [ ] Validation: Empty title shows error

**What to check:**
- Form layout
- Icons (category, navigate_next, history)
- Validation feedback
- Save functionality

**Screenshot:** Take screenshot of edit screen

---

### Screen 4: PurchaseListScreen

**Navigate:** Tap a collection from list

**Test:**
- [ ] Purchase list displays
- [ ] Search icon visible (top-right)
- [ ] Settings icon visible (top-right)
- [ ] Back button works
- [ ] Each purchase item shows:
  - [ ] Image indicator icon (if enabled)
  - [ ] Purchase name
  - [ ] Count/quantity
  - [ ] Category chip (if assigned)
  - [ ] Photo chip (if has photos)
  - [ ] Checkbox
- [ ] Swipe-to-delete works (0.7f threshold)
- [ ] Search opens and filters
- [ ] Checkbox toggles checked state

**What to check:**
- Swipe threshold (not too sensitive)
- Image indicators visible
- Photo chips display correctly
- Category chips formatted
- Colors match original

**Screenshot:** Take screenshot of purchase list

---

### Screen 5: PurchaseEditScreen

**Navigate:** Tap "+" or edit a purchase

**Test:**
- [ ] Edit form displays
- [ ] Title field (required)
- [ ] Price field (decimal, max 2 decimals)
- [ ] Comment field
- [ ] Checked toggle
- [ ] Category dropdown
- [ ] Back button works
- [ ] Save button works
- [ ] Validation: Empty title prevents save
- [ ] Validation: Invalid price shows error

**What to check:**
- Form validation
- Decimal input
- Dropdown behavior
- Toggle switch

**Screenshot:** Take screenshot of edit form

---

### Screen 6: CategoryScreen

**Navigate:** From CollectionEdit → "Category settings"

**Test:**
- [ ] Category list displays
- [ ] FAB (+ button) visible
- [ ] Each category shows:
  - [ ] Category name
  - [ ] Delete button
- [ ] Add new category dialog opens
- [ ] Delete button works
- [ ] Save button works
- [ ] Back button works

**What to check:**
- List layout
- FAB positioning
- Delete icons
- Dialog functionality

**Screenshot:** Take screenshot of categories

---

### Screen 7: HistoryScreen

**Navigate:** From drawer → "History"

**Test:**
- [ ] History timeline displays
- [ ] Each history item shows:
  - [ ] Event type indicator (emoji or icon)
  - [ ] Purchase name
  - [ ] Time indicator
  - [ ] Date chips
  - [ ] Photo indicator
- [ ] Items sorted by date
- [ ] Back button works
- [ ] Scrolling works

**What to check:**
- Timeline layout
- Event indicators display
- Date formatting
- Icons/emojis

**Screenshot:** Take screenshot of history

---

### Screen 8: ListLaterScreen

**Navigate:** From main menu or drawer

**Test:**
- [ ] "List Later" purchases display
- [ ] Each item shows:
  - [ ] Image indicator (if enabled)
  - [ ] Purchase name
  - [ ] Checkbox
- [ ] Swipe-to-delete works
- [ ] "Create purchase" field at top
- [ ] Can add purchase inline
- [ ] Sort button visible
- [ ] Back button works

**What to check:**
- Image indicators
- Inline creation
- Swipe-to-delete
- Sort functionality

**Screenshot:** Take screenshot of list later

---

### Screen 9: SettingsPurchaseScreen

**Navigate:** Drawer → "Settings" OR PurchaseList → Settings icon

**Test:**
- [ ] Settings screen displays
- [ ] Shape radio buttons (Rounded/Cut)
- [ ] Size radio buttons (Small/Medium/Large)
- [ ] Column count slider
- [ ] "Show image" checkbox
- [ ] "Show sorting" checkbox
- [ ] All settings save on change
- [ ] Back button works
- [ ] Save button works

**What to check:**
- Radio button groups
- Slider behavior
- Checkbox states
- Settings persistence

**Screenshot:** Take screenshot of settings

---

### Screen 10: BiometricScreen

**Navigate:** From settings or security menu

**Test:**
- [ ] Biometric toggle visible
- [ ] Toggle switches on/off
- [ ] Shows availability status
- [ ] Error states (if biometric unavailable)
- [ ] Back button works

**What to check:**
- Toggle functionality
- Status messages
- Error colors (red if unavailable)

**Screenshot:** Take screenshot of biometric screen

---

### Screen 11: SkuListScreen (Shopping List)

**Navigate:** Drawer → "History" (payment icon) OR navigate to SKU section

**Test:**
- [ ] SKU list displays
- [ ] Back button visible
- [ ] Statistics icon visible (chart icon)
- [ ] FAB (+ button) visible
- [ ] Each SKU shows:
  - [ ] Name
  - [ ] Comment
  - [ ] Price
  - [ ] Delete button
- [ ] Delete button works (no swipe)
- [ ] Tap SKU opens edit
- [ ] Back button works
- [ ] Statistics button navigates

**What to check:**
- Delete button (not swipe)
- List layout
- Price formatting
- Icons display

**Screenshot:** Take screenshot of SKU list

---

### Screen 12: SkuEditScreen

**Navigate:** Tap FAB on SkuListScreen or tap a SKU

**Test:**
- [ ] Edit form displays
- [ ] Name field (required)
- [ ] Price field (required, decimal)
- [ ] Comment field
- [ ] Currency display
- [ ] Back button works
- [ ] Save button works
- [ ] Validation: Empty name prevents save
- [ ] Validation: Invalid price shows error

**What to check:**
- Form validation
- Price input
- Currency display
- Save functionality

**Screenshot:** Take screenshot of SKU edit

---

### Screen 13: SkuStatisticsScreen

**Navigate:** SkuList → Statistics icon (chart)

**Test:**
- [ ] Statistics display
- [ ] Shows totals/summaries
- [ ] Date range selection (if applicable)
- [ ] Back button works
- [ ] Data displays correctly

**What to check:**
- Statistics layout
- Number formatting
- Date display

**Screenshot:** Take screenshot of statistics

---

## 🎨 Visual Comparison

### Colors to Verify

**Primary Colors:**
- `colorPrimary`: #1B5E20 (dark green)
- `colorAccent`: #263238 (dark blue-grey)
- `Colors.gr`: #4ACFAC (teal/cyan)
- Collection card: #38A186 (teal-green)

**Check:**
- [ ] AppBar colors correct
- [ ] Card backgrounds correct
- [ ] Button colors correct
- [ ] Icon tints correct

### Spacing to Verify

**Standard Spacing:**
- Card padding: 16.dp horizontal, 8.dp vertical
- List item spacing: 8.dp
- Icon size: 24.dp (standard)
- Chip height: 20.dp

**Check:**
- [ ] Cards not too cramped
- [ ] Spacing feels natural
- [ ] Icons appropriately sized

---

## 🐛 Bug Reporting Template

If you find issues, document them as:

```markdown
### Bug: [Brief Description]

**Screen:** [Screen Name]
**Severity:** [Critical/High/Medium/Low]

**Steps to Reproduce:**
1. Navigate to...
2. Tap on...
3. Observe...

**Expected:**
[What should happen]

**Actual:**
[What actually happens]

**Screenshot:** [Attach if possible]

**Original vs Migrated:**
- Original: [How it works in presentation module]
- Migrated: [How it works in shared module]
```

---

## ✅ Success Criteria

### Must Work:
- [ ] All 13 screens accessible
- [ ] No crashes
- [ ] Navigation works
- [ ] Core functionality works (add, edit, delete)
- [ ] Icons display correctly

### Should Match:
- [ ] Colors match original
- [ ] Spacing matches original
- [ ] Icons match original
- [ ] Behavior matches original

### Nice to Have:
- [ ] Animations smooth
- [ ] Performance good
- [ ] No visual glitches

---

## 📊 Test Results Template

After testing, fill out:

```markdown
## Test Results Summary

**Date:** [Date]
**Tester:** [Name]
**Device:** [Emulator/Device Info]
**Build:** Debug

### Screens Tested: X/13

**Passed:** X screens
**Failed:** X screens
**Blocked:** X screens

### Critical Issues: X
[List critical issues]

### High Priority Issues: X
[List high priority issues]

### Medium/Low Issues: X
[List medium/low priority issues]

### Screenshots
[Attach screenshot comparison document]

### Overall Assessment
[Pass/Fail with notes]

### Recommendation
[Next steps]
```

---

## 🎉 Next Steps After Testing

### If All Tests Pass ✅
1. Document success
2. Take celebration screenshots
3. Move to iOS implementation
4. Plan Phase 4 (real data integration)

### If Issues Found 🔧
1. Prioritize issues (Critical → High → Medium → Low)
2. Fix critical issues first
3. Re-test affected screens
4. Iterate until all pass

---

## 💡 Tips

### Testing Tips
- Test each screen thoroughly before moving to next
- Compare with original app side-by-side if possible
- Pay attention to details (colors, spacing, alignment)
- Test edge cases (empty lists, long text, etc.)

### Screenshot Tips
- Take screenshots in consistent format
- Use same device/emulator for all
- Capture both success and error states
- Organize by screen name

### Bug Reporting Tips
- Be specific and detailed
- Include reproduction steps
- Attach screenshots when relevant
- Categorize severity appropriately

---

## 🚀 Ready to Test!

**Command to start:**
```bash
cd /Users/yuriimelnyk/StudioProjects/Purchase
./gradlew :androidApp:installDebug
```

Then open the app on your emulator and work through the checklist above!

Good luck! 🎉

---

_Created: November 30, 2025_  
_Status: Ready for Testing_  
_Next: Execute testing and document results_

