# iOS App Setup and Running Guide

## ✅ COMPLETED STEPS

### 1. iOS Framework Build
- Fixed all multiplatform compilation errors
- Successfully built shared.framework for iOS
- Framework location: `/Users/yuriimelnyk/StudioProjects/Purchase/shared/build/bin/iosSimulatorArm64/debugFramework/shared.framework`

### 2. Xcode Project Creation
- Created iosApp.xcodeproj with proper configuration
- Added Run Script Phase to build shared framework automatically
- Configured framework search paths
- Set up SwiftUI app structure with ContentView

## 🎯 CURRENT STEP: Run the iOS App

### Option 1: Run from Xcode (Recommended)

1. **Open the project** (if not already open):
   ```bash
   open /Users/yuriimelnyk/StudioProjects/Purchase/iosApp/iosApp.xcodeproj
   ```

2. **Wait for Xcode to index** the project (usually takes a few seconds)

3. **Select a simulator:**
   - Click on the device selector in the Xcode toolbar (top center)
   - Choose "iPhone 15" or any iOS 15+ simulator
   - If no simulators are available, go to: Xcode > Settings > Platforms > iOS and download simulators

4. **Build and Run:**
   - Press `⌘R` (Command + R)
   - OR click the ▶️ Play button in the toolbar
   - First build will take longer as it needs to build the shared framework

5. **Expected behavior:**
   - Gradle will build the shared framework first (via Run Script Phase)
   - Xcode will compile the Swift code
   - The simulator will launch
   - Your app should appear with the Purchase management UI

### Option 2: Command Line Build

```bash
# Navigate to iosApp directory
cd /Users/yuriimelnyk/StudioProjects/Purchase/iosApp

# Build the project
xcodebuild -project iosApp.xcodeproj \
  -scheme iosApp \
  -configuration Debug \
  -destination 'platform=iOS Simulator,name=iPhone 15,OS=latest'

# Run on simulator
open -a Simulator
xcrun simctl boot "iPhone 15"
xcrun simctl install booted \
  /Users/yuriimelnyk/StudioProjects/Purchase/iosApp/build/Debug-iphonesimulator/iosApp.app
xcrun simctl launch booted com.veles.purchase.iosApp
```

## 🔧 Troubleshooting

### Issue: "No such module 'shared'"

**Solution:**
1. Make sure the shared framework was built:
   ```bash
   cd /Users/yuriimelnyk/StudioProjects/Purchase
   ./gradlew :shared:linkDebugFrameworkIosSimulatorArm64
   ```

2. Check Framework Search Paths in Xcode:
   - Select project in navigator
   - Select iosApp target
   - Go to Build Settings tab
   - Search for "Framework Search Paths"
   - Should include: `$(SRCROOT)/../shared/build/bin/iosSimulatorArm64/debugFramework`

### Issue: Build fails with "Could not find shared.framework"

**Solution:**
The Run Script Phase should build it automatically, but you can:
1. Build manually first:
   ```bash
   ./gradlew :shared:linkDebugFrameworkIosSimulatorArm64
   ```
2. Clean build folder in Xcode: `⌘⇧K` (Command + Shift + K)
3. Build again: `⌘B`

### Issue: "MainViewController not found"

**Solution:**
Check that your shared module exports the MainViewController. Update ContentView.swift if needed:

```swift
import SwiftUI
import shared

struct ContentView: View {
    var body: some View {
        ComposeView()
            .ignoresSafeArea(.keyboard)
    }
}

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        // Adjust this based on your actual shared module export
        MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}
```

### Issue: Simulator not available

**Solution:**
1. Open Xcode > Settings (⌘,)
2. Go to Platforms tab
3. Click the + button or download icon next to iOS
4. Download iOS simulators
5. Wait for download to complete
6. Restart Xcode if needed

### Issue: "Developer cannot be verified"

**Solution:**
1. Open System Settings > Privacy & Security
2. Scroll down to find the message about the blocked app
3. Click "Allow Anyway"
4. Try running again

## 📱 Testing Checklist

Once the app runs successfully, test these features:

- [ ] App launches without crashes
- [ ] Main screen displays
- [ ] Can add new purchases
- [ ] Can view purchase list
- [ ] Can edit purchases
- [ ] Can delete purchases
- [ ] Can manage collections
- [ ] Can view history
- [ ] UI is responsive to touch
- [ ] Keyboard input works
- [ ] Navigation works correctly
- [ ] Date/time displays correctly (using kotlinx-datetime 0.4.1)

## 🎨 UI Customization

The iOS app uses Compose Multiplatform, so the UI is shared with Android. Any UI changes should be made in the `shared` module's Compose code, not in Swift files.

Swift files (`ContentView.swift`, `iOSApp.swift`) are just wrappers that embed the Compose UI.

## 📊 Performance Notes

- **First launch**: May be slow as it compiles everything
- **Subsequent builds**: Much faster due to caching
- **Hot reload**: Not available for iOS (unlike Android)
- **Framework rebuild**: Only happens when shared code changes

## 🔄 Updating the App

When you make changes to the shared module:

1. The Run Script Phase will automatically rebuild the framework
2. Just press `⌘R` in Xcode to rebuild and run
3. No need to manually run Gradle commands

When you make changes to iOS-specific code (Swift files):

1. Just press `⌘R` in Xcode
2. Changes will be reflected immediately

## 🚀 Next Steps After Successful Run

1. **Test all features** on iOS
2. **Compare with Android** to ensure UI consistency
3. **Test on different simulators** (iPhone SE, iPhone 15 Pro Max, iPad)
4. **Test on physical device** (requires Apple Developer account)
5. **Set up CI/CD** for iOS builds
6. **Prepare for App Store** submission

## 📝 Build Configurations

### Debug Configuration
- Used for development
- Includes debug symbols
- Links to debugFramework
- Faster compilation
- No optimizations

### Release Configuration  
- Used for production
- Optimized code
- Smaller binary size
- Links to releaseFramework (needs to be built separately)

To build release framework:
```bash
./gradlew :shared:linkReleaseFrameworkIosSimulatorArm64
```

## 🎉 Success Criteria

Your iOS app setup is complete when:
- ✅ Xcode project opens without errors
- ✅ Project builds successfully (⌘B)
- ✅ App runs in simulator (⌘R)
- ✅ Main UI appears and is interactive
- ✅ No crashes during basic navigation
- ✅ Shared code (Kotlin/Compose) works on iOS

---

**Date:** November 30, 2024  
**Status:** Ready for testing  
**Platform:** iOS 15.0+  
**Architecture:** arm64 (Apple Silicon simulators)

