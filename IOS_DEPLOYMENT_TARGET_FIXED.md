# ✅ iOS Deployment Target Fixed - 17.2

## Problem
```
Object file was built for newer 'iOS-simulator' version (17.2) 
than being linked (15.0)
```

## Root Cause
The shared framework was compiled with **iOS 17.2** as minimum target, but the Xcode project was set to **iOS 15.0**, causing a linker mismatch.

## Solution Applied

### 1. Updated gradle.properties
```properties
kotlin.native.binary.deploymentTarget=17.2
```

### 2. Updated Xcode project.pbxproj
Changed all 4 occurrences of:
```
IPHONEOS_DEPLOYMENT_TARGET = 15.0
```
To:
```
IPHONEOS_DEPLOYMENT_TARGET = 17.2
```

**Locations updated:**
- Line 208: Debug build settings (global)
- Line 265: Release build settings (global)
- Line 295: Debug target settings
- Line 334: Release target settings

## Verification
```bash
grep -n "IPHONEOS_DEPLOYMENT_TARGET" project.pbxproj
```
**Result:**
```
208:  IPHONEOS_DEPLOYMENT_TARGET = 17.2;
265:  IPHONEOS_DEPLOYMENT_TARGET = 17.2;
295:  IPHONEOS_DEPLOYMENT_TARGET = 17.2;
334:  IPHONEOS_DEPLOYMENT_TARGET = 17.2;
```

✅ All deployment targets set to 17.2

---

## NEW ISSUE: Missing Compose Resources

### Error
```
MissingResourceException: Missing resource with path:
...compose-resources/.../drawable/ic_baseline_payment_24.xml
```

### Cause
Compose Resources (drawable XML files) are not being bundled into the iOS app.

### This is a separate issue from deployment target!

The deployment target mismatch is now **FIXED**. The resource bundling issue needs separate investigation.

---

**Date:** November 30, 2025  
**iOS Target:** 17.2 (was 15.0)  
**Status:** Deployment target FIXED ✅  
**Next Issue:** Compose Resources bundling

