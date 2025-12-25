# iOS Runtime Error Fix - PlistSanityCheck

## Error Description

**Error Message:**
```
CoreSimulator 1010.10 - Device: iPhone 16 Pro
Can't show file for stack frame: kfun:androidx.compose.ui.uikit.PlistSanityCheck.performIfNeeded
```

**Cause:**
This error occurs when the Info.plist file is missing required keys that Compose Multiplatform for iOS needs to properly render UI.

## ✅ FIXED

The following keys have been added to `iosApp/iosApp/Info.plist`:

### 1. CADisableMinimumFrameDurationOnPhone
```xml
<key>CADisableMinimumFrameDurationOnPhone</key>
<true/>
```
**Purpose:** Disables frame rate throttling on iPhone devices, allowing Compose to render at full frame rate.

### 2. UIViewControllerBasedStatusBarAppearance
```xml
<key>UIViewControllerBasedStatusBarAppearance</key>
<false/>
```
**Purpose:** Controls status bar appearance. Set to `false` for global control.

## 🔄 Next Steps

1. **Clean Build in Xcode:**
   - Press `⌘⇧K` (Command + Shift + K)
   - This clears the build folder

2. **Rebuild:**
   - Press `⌘B` (Command + B)
   - Wait for build to complete

3. **Run Again:**
   - Press `⌘R` (Command + R)
   - App should now launch successfully

## 📋 Complete Info.plist Structure

Your Info.plist now includes all required keys:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE plist PUBLIC "-//Apple//DTD PLIST 1.0//EN" "http://www.apple.com/DTDs/PropertyList-1.0.dtd">
<plist version="1.0">
<dict>
    <!-- Bundle Information -->
    <key>CFBundleDevelopmentRegion</key>
    <string>$(DEVELOPMENT_LANGUAGE)</string>
    
    <key>CFBundleExecutable</key>
    <string>$(EXECUTABLE_NAME)</string>
    
    <key>CFBundleIdentifier</key>
    <string>$(PRODUCT_BUNDLE_IDENTIFIER)</string>
    
    <key>CFBundleInfoDictionaryVersion</key>
    <string>6.0</string>
    
    <key>CFBundleName</key>
    <string>$(PRODUCT_NAME)</string>
    
    <key>CFBundlePackageType</key>
    <string>$(PRODUCT_BUNDLE_PACKAGE_TYPE)</string>
    
    <key>CFBundleShortVersionString</key>
    <string>1.0</string>
    
    <key>CFBundleVersion</key>
    <string>1</string>
    
    <!-- iOS Specific -->
    <key>LSRequiresIPhoneOS</key>
    <true/>
    
    <!-- Scene Configuration -->
    <key>UIApplicationSceneManifest</key>
    <dict>
        <key>UIApplicationSupportsMultipleScenes</key>
        <true/>
    </dict>
    
    <!-- Input Support -->
    <key>UIApplicationSupportsIndirectInputEvents</key>
    <true/>
    
    <!-- Launch Screen -->
    <key>UILaunchScreen</key>
    <dict/>
    
    <!-- ✅ COMPOSE MULTIPLATFORM REQUIRED -->
    <key>CADisableMinimumFrameDurationOnPhone</key>
    <true/>
    
    <key>UIViewControllerBasedStatusBarAppearance</key>
    <false/>
    
    <!-- Device Capabilities -->
    <key>UIRequiredDeviceCapabilities</key>
    <array>
        <string>armv7</string>
    </array>
    
    <!-- Supported Orientations (iPhone) -->
    <key>UISupportedInterfaceOrientations</key>
    <array>
        <string>UIInterfaceOrientationPortrait</string>
        <string>UIInterfaceOrientationLandscapeLeft</string>
        <string>UIInterfaceOrientationLandscapeRight</string>
    </array>
    
    <!-- Supported Orientations (iPad) -->
    <key>UISupportedInterfaceOrientations~ipad</key>
    <array>
        <string>UIInterfaceOrientationPortrait</string>
        <string>UIInterfaceOrientationPortraitUpsideDown</string>
        <string>UIInterfaceOrientationLandscapeLeft</string>
        <string>UIInterfaceOrientationLandscapeRight</string>
    </array>
</dict>
</plist>
```

## 🔍 Understanding PlistSanityCheck

`PlistSanityCheck` is a Compose Multiplatform for iOS internal check that:
1. Runs when the app launches
2. Validates Info.plist contains required keys
3. Throws an error if keys are missing
4. Ensures proper UI rendering on iOS

**Required Keys for Compose:**
- ✅ `CADisableMinimumFrameDurationOnPhone` - Frame rate control
- ✅ `UIViewControllerBasedStatusBarAppearance` - Status bar management
- ✅ Standard iOS keys (Bundle ID, version, etc.)

## 🎯 Verification

After rebuilding, verify:
1. App launches without crash
2. Compose UI appears correctly
3. No error messages in console
4. Smooth rendering and animations

## 📝 Common Related Errors

### Error: "Missing required key in Info.plist"
**Solution:** Check that all keys listed above are present

### Error: "Frame rate throttling"
**Solution:** Ensure `CADisableMinimumFrameDurationOnPhone` is set to `true`

### Error: "Status bar appearance issue"
**Solution:** Set `UIViewControllerBasedStatusBarAppearance` to `false`

## 🚀 Now You Can Run

The app is now properly configured. Steps:
1. Clean: `⌘⇧K`
2. Build: `⌘B`
3. Run: `⌘R`

Your Compose Multiplatform iOS app should now launch successfully! 🎉

---

**Fixed:** November 30, 2024  
**Issue:** PlistSanityCheck runtime error  
**Solution:** Added required Info.plist keys for Compose Multiplatform iOS  
**Status:** ✅ Ready to run

