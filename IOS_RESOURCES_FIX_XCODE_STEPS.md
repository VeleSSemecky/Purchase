# 🎯 iOS Resources Not Found - Complete Fix Guide

## Problem

```
MissingResourceException: Missing resource with path:
.../iosApp.app/compose-resources/composeResources/com.veles.purchase.shared.resources/drawable/ic_baseline_payment_24.xml
```

**Root Cause:** Compose Multiplatform resources are NOT automatically copied from the framework to the iOS app bundle. They need an explicit copy step.

---

## Solution Overview

1. ✅ XML drawables are iOS-compatible (already fixed)
2. ✅ Resources are exported from shared framework (already configured)
3. ⚠️ **Need to copy resources from framework to app bundle** (THIS IS THE FIX!)

---

## Step-by-Step Fix in Xcode

### Step 1: Open Xcode Project
```bash
open /Users/yuriimelnyk/StudioProjects/Purchase/iosApp/iosApp.xcodeproj
```

### Step 2: Add "Copy Compose Resources" Build Phase

1. In Xcode, select the **iosApp** project in the navigator
2. Select the **iosApp** target
3. Go to the **Build Phases** tab
4. You should see: "Build Shared Framework" as the first phase
5. Click the **"+" button** above the build phases list
6. Select **"New Run Script Phase"**
7. Drag the new phase to be AFTER "Build Shared Framework" but BEFORE "Compile Sources"
8. Rename it to: **"Copy Compose Resources"**
9. In the script box, paste:

```bash
"$SRCROOT/Scripts/copy-compose-resources.sh"
```

10. Click the dropdown next to "Input Files" and verify it's empty (that's OK)
11. Click the dropdown next to "Output Files" and verify it's empty (that's OK)

### Step 3: Verify the Script File Exists

The script has already been created at:
```
/Users/yuriimelnyk/StudioProjects/Purchase/iosApp/Scripts/copy-compose-resources.sh
```

To verify it's executable:
```bash
ls -l /Users/yuriimelnyk/StudioProjects/Purchase/iosApp/Scripts/copy-compose-resources.sh
```

Should show: `-rwxr-xr-x` (the `x` means executable)

If not executable, run:
```bash
chmod +x /Users/yuriimelnyk/StudioProjects/Purchase/iosApp/Scripts/copy-compose-resources.sh
```

---

## Alternative: Manual Xcode Configuration (If Above Doesn't Work)

If you can't see the script running or get errors, manually configure in Xcode:

### Option A: Embedded Resources in Framework

1. Select the iosApp target → Build Phases
2. Expand "Embed Frameworks" section
3. Find `shared.framework`
4. Check **"Code Sign On Copy"**
5. Check **"Embed & Sign"** (not just "Do Not Embed")

### Option B: Direct Resource Bundling

Add this script AFTER "Build Shared Framework":

```bash
#!/bin/bash
set -e

# Configuration
FRAMEWORK_PATH="${BUILD_DIR}/${CONFIGURATION}${EFFECTIVE_PLATFORM_NAME}/shared.framework"
APP_RESOURCES="${BUILT_PRODUCTS_DIR}/${PRODUCT_NAME}.app/compose-resources"

echo "📦 Copying Compose resources..."
echo "  From: ${FRAMEWORK_PATH}/compose-resources"
echo "  To: ${APP_RESOURCES}"

# Remove old resources
rm -rf "${APP_RESOURCES}"

# Copy new resources
if [ -d "${FRAMEWORK_PATH}/compose-resources" ]; then
    cp -R "${FRAMEWORK_PATH}/compose-resources" "${APP_RESOURCES}"
    echo "✅ Resources copied successfully"
else
    echo "⚠️  No resources found in framework"
    echo "    This will cause MissingResourceException!"
    echo "    Run: ./gradlew :shared:copyComposeResourcesToIosFramework"
fi
```

---

## Verification Steps

### 1. Clean Build Everything
```bash
cd /Users/yuriimelnyk/StudioProjects/Purchase

# Clean Gradle
./gradlew clean

# Clean Xcode (in Xcode: Product → Clean Build Folder or Cmd+Shift+K)
```

### 2. Build iOS Framework with Resources
```bash
./gradlew :shared:linkDebugFrameworkIosSimulatorArm64
```

### 3. Verify Framework Has Resources
```bash
ls -R shared/build/bin/iosSimulatorArm64/debugFramework/shared.framework/compose-resources/
```

Should show:
```
compose-resources/
└── composeResources/
    └── com.veles.purchase.shared.resources/
        └── drawable/
            ├── ic_baseline_payment_24.xml
            ├── ic_baseline_settings_24.xml
            ├── ic_baseline_history_24.xml
            └── ... (all 28 XML files)
```

### 4. Build in Xcode
1. Open Xcode
2. Select iPhone Simulator
3. Product → Build (Cmd+B)
4. **Watch the build log** for:
   ```
   📦 Copying Compose resources...
   ✅ Resources copied successfully
   ```

### 5. Verify App Bundle Has Resources
```bash
# After building in Xcode, check the app bundle
find ~/Library/Developer/Xcode/DerivedData -name "iosApp.app" -type d | \
  xargs -I{} find {} -name "compose-resources" -type d
```

Should find the directory in the app bundle.

### 6. Run the App
1. In Xcode, press Cmd+R to run
2. Open the drawer menu
3. ✅ Icons should display without `MissingResourceException`!

---

## Troubleshooting

### Resources Still Missing After Build

**Problem:** Script ran but resources not in app bundle

**Solution:**
```bash
# Manually copy for testing
FRAMEWORK="/Users/yuriimelnyk/StudioProjects/Purchase/shared/build/bin/iosSimulatorArm64/debugFramework/shared.framework"
APP=$(find ~/Library/Developer/Xcode/DerivedData -name "iosApp.app" | head -1)

cp -R "$FRAMEWORK/compose-resources" "$APP/"
```

Then run the app again (without rebuilding).

### Script Permission Denied

**Problem:** `/bin/sh: permission denied`

**Solution:**
```bash
chmod +x iosApp/Scripts/copy-compose-resources.sh
```

### Framework Resources Empty

**Problem:** Framework built but has no compose-resources folder

**Solution:**
```bash
# Force rebuild resources
./gradlew :shared:clean
./gradlew :shared:prepareComposeResourcesTaskForCommonMain
./gradlew :shared:copyComposeResourcesToIosFramework
./gradlew :shared:linkDebugFrameworkIosSimulatorArm64
```

### Wrong Build Configuration

**Problem:** Resources copied for Debug but running Release (or vice versa)

**Solution:**
Update the script to handle both:
```bash
# In copy-compose-resources.sh, use:
CONFIGURATION="${CONFIGURATION:-Debug}"
FRAMEWORK_PATH="${BUILD_DIR}/${CONFIGURATION}${EFFECTIVE_PLATFORM_NAME}/shared.framework"
```

---

## Why This Happens

1. **Gradle Build:** Builds shared.framework with compose-resources inside
2. **Xcode Build:** Embeds shared.framework in app, but doesn't copy internal resources
3. **Runtime:** App looks for resources in app bundle, not framework bundle
4. **Result:** MissingResourceException! 💥

**The Fix:** Explicitly copy compose-resources from framework to app bundle during Xcode build.

---

## Files Modified

### Created:
- `/Users/yuriimelnyk/StudioProjects/Purchase/iosApp/Scripts/copy-compose-resources.sh` ✅
- `/Users/yuriimelnyk/StudioProjects/Purchase/iosApp/iosApp.xcodeproj/project.pbxproj` (added build phase) ✅

### Already Configured:
- `shared/shared.gradle.kts` - export(compose.components.resources) ✅
- `shared/shared.gradle.kts` - api(compose.components.resources) ✅
- `shared/shared.gradle.kts` - copyComposeResourcesToIosFramework task ✅
- All drawable XML files - iOS-compatible (fillColor=#000000, no tint) ✅

---

## Expected Result

After implementing this fix:

✅ **Before App Launch:**
- Framework builds with resources
- Xcode copies resources to app bundle
- Resources visible in app bundle at: `iosApp.app/compose-resources/`

✅ **At Runtime:**
- App finds resources in bundle
- Icons load and display correctly
- No MissingResourceException
- Drawer menu shows all icons with white color

---

## Quick Reference Commands

```bash
# Rebuild framework
./gradlew :shared:linkDebugFrameworkIosSimulatorArm64

# Check framework resources
ls shared/build/bin/iosSimulatorArm64/debugFramework/shared.framework/compose-resources/

# Build in Xcode (after opening project)
# Cmd+B (build) or Cmd+R (run)

# Check app bundle resources (after Xcode build)
find ~/Library/Developer/Xcode/DerivedData -name "iosApp.app" -exec ls -R {}/compose-resources \;
```

---

**Status:** ⚠️ **ACTION REQUIRED - Must add build phase in Xcode**  
**Next Step:** Open Xcode and add the "Copy Compose Resources" build phase as described above  
**Expected Time:** 5 minutes  
**Result:** All icons will work on iOS! 🎉

