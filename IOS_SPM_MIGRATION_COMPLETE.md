# iOS Build Fix - CocoaPods to SPM Migration Complete

## Date: December 29, 2025

## IMPORTANT: Working Configuration

### iOS Simulator
- **Use iOS 18.4 or earlier** (stable versions)
- **DO NOT use iOS 26.x beta** - causes "Failed to iterate on macho slices" errors

### Kotlin/Native iOS Framework Configuration
The simple configuration works best - **DO NOT use explicit framework configuration**:

```kotlin
kotlin {
    applyDefaultHierarchyTemplate()
    
    // Simple iOS targets - let Kotlin/Native handle framework defaults
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    
    // DO NOT USE explicit framework configuration:
    // listOf(iosX64(), iosArm64(), iosSimulatorArm64()).forEach {
    //     it.binaries.framework {
    //         baseName = "shared"
    //         isStatic = true  // This causes issues!
    //     }
    // }
}
```

## Problem
iOS build was failing with errors:
- `framework 'Pods_iosApp' not found`
- `Search path '/Users/.../shared/build/cocoapods/framework' not found`
- `Linker command failed with exit code 1`

## Root Cause
The project had a mixed configuration:
- Firebase and GoogleSignIn were added via Swift Package Manager (SPM)
- The shared Kotlin framework was configured via CocoaPods
- The CocoaPods installation was incomplete, causing linker errors

## Solution Applied

### 1. Removed CocoaPods Completely
- Deleted `/iosApp/Pods/` directory
- Deleted `/iosApp/Podfile.lock`
- Updated `Podfile` to just documentation (CocoaPods no longer used)

### 2. Updated Xcode Project (`project.pbxproj`)
**Removed:**
- All references to `Pods_iosApp.framework`
- All references to `Pods-iosApp.debug.xcconfig` and `Pods-iosApp.release.xcconfig`
- `[CP] Check Pods Manifest.lock` build phase
- Pods group and file references
- `baseConfigurationReference` pointing to Pods xcconfig files

**Added:**
- `Build Shared Framework` build phase - builds Kotlin shared framework via Gradle
- `Embed Frameworks` build phase - copies framework to app bundle
- Correct `FRAMEWORK_SEARCH_PATHS` pointing to:
  - `$(SRCROOT)/Frameworks`
  - `$(PROJECT_DIR)/../shared/build/bin/iosSimulatorArm64/{debug,release}Framework`
  - `$(PROJECT_DIR)/../shared/build/bin/iosArm64/{debug,release}Framework`
  - `$(PROJECT_DIR)/../shared/build/bin/iosX64/{debug,release}Framework`

### 3. Created Frameworks Directory
- Created `/iosApp/Frameworks/` directory
- Pre-copied `shared.framework` for initial build

## Current Configuration

### Dependencies via Swift Package Manager:
- Firebase iOS SDK (v12.7.0+)
  - FirebaseAnalytics
  - FirebaseAuth
  - FirebaseCore
  - FirebaseCrashlytics
  - FirebaseDatabase
  - FirebaseFirestore
  - FirebaseFunctions
  - FirebaseMessaging
  - FirebaseStorage
- GoogleSignIn-iOS (v9.0.0+)
  - GoogleSignIn
  - GoogleSignInSwift

### Kotlin Shared Framework:
- Built via Gradle during Xcode build
- Integrated directly without CocoaPods
- Static framework (`isStatic = true`)

## Build Script Details

### Build Shared Framework (runs before Sources)
```bash
cd "$SRCROOT/.."

# Determine architecture
if [ "${PLATFORM_NAME}" = "iphonesimulator" ]; then
    if [ "${ARCHS}" = "arm64" ]; then
        TARGET="iosSimulatorArm64"
    else
        TARGET="iosX64"
    fi
else
    TARGET="iosArm64"
fi

# Build configuration
GRADLE_TASK="link${CONFIGURATION}Framework${TARGET}"

echo "Building shared framework for $TARGET..."
./gradlew :shared:$GRADLE_TASK

# Copy framework to accessible location
FRAMEWORK_SRC="shared/build/bin/$TARGET/${CONFIGURATION,,}Framework/shared.framework"
FRAMEWORK_DST="$SRCROOT/Frameworks"

mkdir -p "$FRAMEWORK_DST"
if [ -d "$FRAMEWORK_SRC" ]; then
    cp -R "$FRAMEWORK_SRC" "$FRAMEWORK_DST/"
fi
```

### Embed Frameworks (runs after Resources)
```bash
FRAMEWORK_SRC="$SRCROOT/Frameworks/shared.framework"
FRAMEWORK_DST="${BUILT_PRODUCTS_DIR}/${FRAMEWORKS_FOLDER_PATH}"

if [ -d "$FRAMEWORK_SRC" ]; then
    mkdir -p "$FRAMEWORK_DST"
    cp -R "$FRAMEWORK_SRC" "$FRAMEWORK_DST/"
fi
```

## Warnings (Can Be Ignored)
The following warnings are expected and can be ignored:
- "Run script build phase 'Build Shared Framework' will be run during every build..."
- "Run script build phase 'Embed Frameworks' will be run during every build..."

These scripts don't have output file specifications, so Xcode runs them every build to ensure the framework is up-to-date.

## How to Build

### From Terminal:
```bash
cd /Users/yuriimelnyk/StudioProjects/Purchase/iosApp
xcodebuild -project iosApp.xcodeproj -scheme iosApp \
    -destination 'platform=iOS Simulator,name=iPhone 16' \
    -configuration Debug build
```

### From Xcode:
1. Open `iosApp.xcodeproj` (NOT `.xcworkspace`)
2. Select target device/simulator
3. Build (Cmd+B) or Run (Cmd+R)

## Files Changed
- `/iosApp/iosApp.xcodeproj/project.pbxproj` - Complete rebuild without CocoaPods
- `/iosApp/Podfile` - Updated to documentation only
- `/iosApp/Podfile.lock` - Deleted
- `/iosApp/Pods/` - Deleted
- `/iosApp/Frameworks/` - Created, contains `shared.framework`

## Notes for Future LLM Agents
- This project uses **Swift Package Manager** for all external dependencies
- The Kotlin shared framework is built by Gradle and integrated directly
- Do NOT run `pod install` - CocoaPods is no longer used
- If build fails with "framework not found shared", run:
  ```bash
  cd /path/to/project
  ./gradlew :shared:linkDebugFrameworkIosSimulatorArm64
  cp -R shared/build/bin/iosSimulatorArm64/debugFramework/shared.framework iosApp/Frameworks/
  ```

