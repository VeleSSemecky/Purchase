# Quick Fix Commands for iOS Privacy Bundle Error

Run these commands in order:

## 1. Clean Gradle Build
```bash
cd /Users/yuriimelnyk/StudioProjects/Purchase
./gradlew clean
```

## 2. Remove CocoaPods Cache
```bash
cd iosApp
rm -rf Pods Podfile.lock iosApp.xcworkspace .build
```

## 3. Clean Shared Module CocoaPods Build
```bash
cd ../shared
rm -rf build/cocoapods build/bin
```

## 4. Clean Xcode Derived Data (Optional but Recommended)
```bash
rm -rf ~/Library/Developer/Xcode/DerivedData/iosApp-*
```

## 5. Reinstall CocoaPods
```bash
cd ../iosApp
pod deintegrate
pod install --repo-update
```

## 6. Open Workspace in Xcode
```bash
open iosApp.xcworkspace
```

## 7. In Xcode
- Go to **Product > Clean Build Folder** (Shift+Cmd+K)
- Then **Product > Build** (Cmd+B)
- If successful, **Product > Run** (Cmd+R)

## Alternative: Use the Automated Script
```bash
cd /Users/yuriimelnyk/StudioProjects/Purchase
./fix_ios_privacy_bundles.sh
```

Then open the workspace in Xcode:
```bash
cd iosApp
open iosApp.xcworkspace
```

## What Was Fixed
1. **Podfile**: Added `ENABLE_USER_SCRIPT_SANDBOXING = 'NO'` to fix Xcode 15+ sandboxing issues
2. **Podfile**: Added logic to remove invalid GoogleUtilities bundle references from AppAuth target
3. **shared.podspec**: Added sandboxing disable at pod target level
4. **Created**: Automated cleanup script `fix_ios_privacy_bundles.sh`
5. **Created**: Documentation `IOS_PRIVACY_BUNDLE_FIX.md`

## Verification
After running these commands, you should see:
- ✅ `pod install` completes successfully
- ✅ `iosApp.xcworkspace` is created
- ✅ Xcode build succeeds without lstat errors
- ✅ App runs on simulator/device

## If Issues Persist
See `IOS_PRIVACY_BUNDLE_FIX.md` for detailed troubleshooting and alternative solutions.

