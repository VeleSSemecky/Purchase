# iOS Privacy Bundle Error Fix

## Problem
```
lstat(/Users/yuriimelnyk/StudioProjects/Purchase/shared/build/cocoapods/synthetic/ios/build/Debug-iphonesimulator/AppAuth/GoogleUtilities_GoogleUtilities-Network.bundle): No such file or directory (2)
lstat(/Users/yuriimelnyk/StudioProjects/Purchase/shared/build/cocoapods/synthetic/ios/build/Debug-iphonesimulator/AppAuth/GoogleUtilities_GoogleUtilities-UserDefaults.bundle): No such file or directory (2)
lstat(/Users/yuriimelnyk/StudioProjects/Purchase/shared/build/cocoapods/synthetic/ios/build/Debug-iphonesimulator/AppAuth/GoogleUtilities_GoogleUtilities-NSData.bundle): No such file or directory (2)
```

## Root Cause
This error occurs when:
1. **User Script Sandboxing** is enabled in Xcode 15+ which restricts build script access to resources
2. **Privacy manifest bundles** from GoogleUtilities are referenced but not found in the expected path
3. AppAuth pod is trying to copy GoogleUtilities privacy bundles that don't exist in that specific location

## Solution Applied

### 1. Updated Podfile
Added post-install hooks to:
- Disable user script sandboxing: `ENABLE_USER_SCRIPT_SANDBOXING = 'NO'`
- Set `COPY_PHASE_STRIP = 'NO'` to prevent stripping during copy phase
- Remove invalid bundle references from AppAuth target's resource build phase

### 2. Updated shared.podspec
Added xcconfig settings to ensure user script sandboxing is disabled at both levels:
- `spec.xcconfig` level
- `spec.pod_target_xcconfig` level

### 3. Created Fix Script
`fix_ios_privacy_bundles.sh` - Automated cleanup and reinstall script that:
1. Cleans all Gradle build artifacts
2. Removes CocoaPods cache (Pods, Podfile.lock, workspace)
3. Removes shared module CocoaPods build
4. Deintegrates and reinstalls pods

## How to Fix

### Quick Fix (Recommended)
Run the automated fix script:
```bash
./fix_ios_privacy_bundles.sh
```

### Manual Fix
If the script doesn't work, follow these steps:

1. **Clean everything**:
```bash
# Clean Gradle
./gradlew clean

# Clean CocoaPods
cd iosApp
rm -rf Pods Podfile.lock iosApp.xcworkspace

# Clean shared build
cd ../shared
rm -rf build/cocoapods build/bin
```

2. **Reinstall CocoaPods**:
```bash
cd ../iosApp
pod deintegrate
pod install --repo-update
```

3. **Clean Xcode Derived Data**:
```bash
rm -rf ~/Library/Developer/Xcode/DerivedData/iosApp-*
```

4. **Open in Xcode**:
```bash
open iosApp.xcworkspace
```

5. **In Xcode**:
   - Product > Clean Build Folder (Shift+Cmd+K)
   - Build (Cmd+B)

## Verification

After applying the fix, check that:
1. ✅ `pod install` completes without errors
2. ✅ `iosApp.xcworkspace` is created
3. ✅ Build in Xcode succeeds
4. ✅ No lstat errors appear

## Alternative Solutions

If the above doesn't work, try:

### Option A: Update GoogleSignIn Version
In `shared/shared.gradle.kts`, update GoogleSignIn version:
```kotlin
pod("GoogleSignIn") {
    version = "8.0.0"  // or latest stable
}
```

### Option B: Use Direct Framework
Instead of CocoaPods, use XCFramework:
1. Remove GoogleSignIn pod dependency from shared.gradle.kts
2. Manually add GoogleSignIn XCFramework to Xcode project

### Option C: Disable Privacy Manifest Validation (Development Only)
Add to Podfile:
```ruby
post_install do |installer|
  installer.pods_project.targets.each do |target|
    target.build_configurations.each do |config|
      config.build_settings['ENABLE_USER_SCRIPT_SANDBOXING'] = 'NO'
      config.build_settings['ASSETCATALOG_COMPILER_GENERATE_SWIFT_ASSET_SYMBOL_EXTENSIONS'] = 'NO'
    end
  end
end
```

## Related Issues
- [CocoaPods Privacy Manifest Issue](https://github.com/CocoaPods/CocoaPods/issues/11839)
- [Xcode 15 Build Settings](https://developer.apple.com/documentation/xcode-release-notes/xcode-15-release-notes)
- [Google Sign-In iOS SDK](https://github.com/google/GoogleSignIn-iOS)

## Testing
After fix, test:
1. Clean build in Xcode
2. Run on iOS Simulator
3. Run on physical device
4. Archive for distribution (if planning to release)

## Notes
- This is a known issue with Xcode 15+ and CocoaPods
- User Script Sandboxing is a security feature but can cause issues with legacy pods
- The fix is safe for development and production builds
- Apple recommends migrating to Swift Package Manager (SPM) for better Xcode 15+ support

## Next Steps
1. Run the fix script or manual steps
2. Verify build succeeds
3. If issues persist, check Xcode build logs for specific errors
4. Consider migrating to SPM in future (optional)

