# iOS Build Errors - Complete Resolution Guide

## Errors Encountered

```
1. Unable to open base configuration reference file '/Users/yuriimelnyk/StudioProjects/Purchase/iosApp/Pods/Target Support Files/Pods-iosApp/Pods-iosApp.debug.xcconfig'
2. Unable to load contents of file list: '/Target Support Files/Pods-iosApp/Pods-iosApp-frameworks-Debug-input-files.xcfilelist'
3. lstat(...GoogleUtilities_GoogleUtilities-Network.bundle): No such file or directory
4. lstat(...Firebase_FirebaseFirestore.bundle): No such file or directory
5. Missing package product 'FirebaseMessaging', 'FirebaseDatabase', 'FirebaseStorage', etc.
```

## Root Causes

### Issue #1: CocoaPods Not Installed
**Error**: Unable to open xcconfig files, xcfilelist files  
**Cause**: `pod install` has never been run, so the `Pods/` directory doesn't exist

### Issue #2: Firebase SPM Conflicts with Firebase KMP
**Error**: Missing package product 'FirebaseMessaging', etc.  
**Cause**: Xcode project has Firebase Swift Package Manager dependencies that conflict with Firebase KMP (Kotlin Multiplatform)

### Issue #3: Privacy Bundle Errors
**Error**: lstat(...bundle): No such file or directory  
**Cause**: Xcode 15+ User Script Sandboxing prevents CocoaPods from accessing privacy manifest bundles

---

## ✅ SOLUTION APPLIED

### Step 1: Removed Firebase SPM Dependencies ✅
**File Modified**: `iosApp/iosApp.xcodeproj/project.pbxproj`

- Removed all `FirebaseAnalytics`, `FirebaseAuth`, `FirebaseCore`, `FirebaseCrashlytics`, `FirebaseDatabase`, `FirebaseFirestore`, `FirebaseFunctions`, `FirebaseMessaging`, `FirebaseStorage` package references
- Removed `XCRemoteSwiftPackageReference` and `XCSwiftPackageProductDependency` sections
- Firebase is now provided via Firebase KMP (GitLive) through the shared module

**Why**: You're using Firebase KMP in the shared module, so iOS-specific Firebase SPM dependencies create conflicts.

### Step 2: Fixed Privacy Bundle Issues ✅
**Files Modified**:
- `iosApp/Podfile`
- `shared/shared.podspec`

**Changes**:
- Added `ENABLE_USER_SCRIPT_SANDBOXING = 'NO'` to disable Xcode 15+ sandboxing
- Added `COPY_PHASE_STRIP = 'NO'` to prevent resource stripping
- Added post_install hook to remove invalid GoogleUtilities bundle references

### Step 3: Created Setup Scripts ✅

#### `setup_ios_complete.sh` ⭐ **USE THIS ONE**
Complete automated setup that:
1. Cleans Gradle build
2. Cleans shared module
3. Cleans iosApp directory
4. Cleans Xcode derived data
5. Generates dummy KMP framework
6. Runs `pod install`

#### Other Scripts:
- `fix_xcode_project.sh` - Alternative with Python-based cleanup
- `quick_fix_ios.sh` - Quick cleanup script
- `fix_ios_privacy_bundles.sh` - Privacy bundle specific fix
- `remove_firebase_spm.py` - Python script to clean project file

---

## 🚀 HOW TO FIX (Step-by-Step)

### Quick Fix (Recommended)

Run the complete setup script:
```bash
cd /Users/yuriimelnyk/StudioProjects/Purchase
./setup_ios_complete.sh
```

This will:
- ✅ Clean all build artifacts
- ✅ Generate the KMP framework
- ✅ Install CocoaPods dependencies
- ✅ Create the Xcode workspace

### Manual Fix (If Script Fails)

If the script fails or you prefer manual steps:

#### 1. Clean Everything
```bash
cd /Users/yuriimelnyk/StudioProjects/Purchase

# Clean Gradle
./gradlew clean

# Clean shared module
cd shared
rm -rf build/cocoapods build/bin

# Clean iosApp
cd ../iosApp
rm -rf Pods Podfile.lock iosApp.xcworkspace

# Clean Xcode derived data
rm -rf ~/Library/Developer/Xcode/DerivedData/iosApp-*
```

#### 2. Generate Dummy Framework
```bash
cd /Users/yuriimelnyk/StudioProjects/Purchase
./gradlew :shared:generateDummyFramework
```

Or alternatively:
```bash
./gradlew :shared:podInstall
```

#### 3. Install CocoaPods
```bash
cd iosApp
pod deintegrate
pod install --repo-update
```

#### 4. Open in Xcode
```bash
open iosApp.xcworkspace
```

**⚠️ IMPORTANT**: Always open `iosApp.xcworkspace`, NOT `iosApp.xcodeproj`

#### 5. Build in Xcode
- Select a simulator or device
- Product > Clean Build Folder (⇧⌘K)
- Product > Build (⌘B)
- Product > Run (⌘R)

---

## ✅ Verification Checklist

After running the fix, verify:

- [ ] `Pods/` directory exists in `iosApp/`
- [ ] `iosApp.xcworkspace` file exists
- [ ] `Podfile.lock` file exists
- [ ] Opening workspace shows no "Missing Package" errors
- [ ] Build in Xcode completes successfully
- [ ] No lstat errors appear

---

## 🔍 Understanding the Architecture

### Firebase KMP vs Firebase SPM

**Firebase KMP** (What You're Using):
- GitLive Firebase Kotlin Multiplatform library
- Defined in `shared/shared.gradle.kts`
- Provides Firebase APIs in common Kotlin code
- iOS implementation uses native Firebase SDK under the hood

**Firebase SPM** (What Was Removed):
- Swift Package Manager dependencies
- iOS-specific Firebase libraries
- Not needed when using Firebase KMP
- Caused "Missing package product" errors

### CocoaPods Structure

```
iosApp/
├── Podfile                          # CocoaPods dependency specification
├── Podfile.lock                     # Locked versions
├── iosApp.xcworkspace               # Xcode workspace (OPEN THIS)
├── iosApp.xcodeproj                 # Xcode project (DON'T OPEN DIRECTLY)
└── Pods/                            # Downloaded dependencies
    ├── GoogleSignIn/
    ├── AppAuth/
    └── Target Support Files/        # Build configurations
```

### Kotlin Multiplatform Framework

```
shared/
├── shared.podspec                   # CocoaPods spec for KMP framework
├── shared.gradle.kts                # KMP configuration
└── build/
    └── cocoapods/
        └── framework/
            └── shared.framework      # Built KMP framework
```

---

## 🛠️ Troubleshooting

### If CocoaPods Is Not Installed

```bash
# Check installation
pod --version

# If not installed, install via RubyGems
sudo gem install cocoapods

# Or via Homebrew
brew install cocoapods
```

### If Pod Install Fails

```bash
# Update CocoaPods
sudo gem install cocoapods

# Clear cache
pod cache clean --all
pod repo update

# Try with verbose output
cd iosApp
pod install --repo-update --verbose
```

### If Gradle Tasks Fail

```bash
# Make gradlew executable
chmod +x ./gradlew

# Clean and try again
./gradlew clean
./gradlew :shared:generateDummyFramework
```

### If Still Getting "Missing Package" Errors

The Firebase SPM references might still be in the project:

```bash
# Run the Python cleanup script
python3 remove_firebase_spm.py

# Or manually check project.pbxproj
grep -i "firebase" iosApp/iosApp.xcodeproj/project.pbxproj

# Should return no results (except for GoogleService-Info.plist)
```

### If Getting Privacy Bundle Errors

Make sure the Podfile has the post_install hook:

```ruby
post_install do |installer|
  installer.pods_project.targets.each do |target|
    target.build_configurations.each do |config|
      config.build_settings['ENABLE_USER_SCRIPT_SANDBOXING'] = 'NO'
      config.build_settings['COPY_PHASE_STRIP'] = 'NO'
      config.build_settings['IPHONEOS_DEPLOYMENT_TARGET'] = '17.2'
    end
  end
  
  # Fix privacy bundle paths
  installer.pods_project.targets.each do |target|
    if target.name.include?('AppAuth')
      target.build_phases.each do |build_phase|
        if build_phase.is_a?(Xcodeproj::Project::Object::PBXResourcesBuildPhase)
          build_phase.files.delete_if do |file|
            file.file_ref && file.file_ref.path && 
            (file.file_ref.path.include?('GoogleUtilities') ||
             file.file_ref.path.include?('Firebase_') ||
             file.file_ref.path.include?('nanopb_') ||
             file.file_ref.path.include?('leveldb_') ||
             file.file_ref.path.include?('gRPC_'))
          end
        end
      end
    end
  end
end
```

---

## 📱 Running on iOS

### Simulator
1. Open `iosApp.xcworkspace`
2. Select an iOS simulator from the device menu
3. Click Run (⌘R)

### Physical Device
1. Connect your iPhone/iPad
2. Select it from the device menu
3. You may need to sign the app:
   - Select project in navigator
   - Go to Signing & Capabilities
   - Select your team
4. Click Run (⌘R)

---

## 📚 Related Documentation

- `IOS_PRIVACY_BUNDLE_FIX.md` - Privacy bundle detailed guide
- `QUICK_FIX_IOS_BUNDLES.md` - Quick command reference
- `IOS_PRIVACY_BUNDLE_RESOLUTION.md` - Previous fix summary
- `FIREBASE_KMP_MIGRATION_COMPLETE.md` - Firebase KMP migration details

---

## 🎯 Summary

**What Was Wrong**:
1. CocoaPods never installed (no Pods/ directory)
2. Firebase SPM conflicting with Firebase KMP
3. Privacy bundle path issues with Xcode 15+

**What We Fixed**:
1. ✅ Removed Firebase SPM dependencies from Xcode project
2. ✅ Updated Podfile to disable user script sandboxing
3. ✅ Updated shared.podspec with proper build settings
4. ✅ Created automated setup scripts

**Next Steps**:
1. Run `./setup_ios_complete.sh`
2. Open `iosApp.xcworkspace` in Xcode
3. Build and run

---

**Status**: ✅ READY TO BUILD  
**Last Updated**: December 28, 2025  
**iOS Version**: 17.2+  
**Xcode Version**: 15+  
**CocoaPods**: 1.12+

