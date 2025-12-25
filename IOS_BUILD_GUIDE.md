# 🍎 iOS Build Setup Guide - Purchase KMP App

**Date:** November 30, 2025  
**Status:** ✅ Ready to build iOS app

---

## 📱 How to Run iOS Build

### Quick Start (3 Steps)

#### Step 1: Build the Shared Framework
```bash
cd /Users/yuriimelnyk/StudioProjects/Purchase
./gradlew :shared:linkDebugFrameworkIosSimulatorArm64
```

#### Step 2: Create/Open Xcode Project
```bash
# Run the setup script (creates Xcode project if needed)
./setup_ios.sh

# OR manually open
open iosApp/iosApp.xcodeproj
```

#### Step 3: Run in Xcode
1. Select **iosApp** scheme
2. Choose **iOS Simulator** (iPhone 15 Pro or similar)
3. Click **Run** button (or press ⌘R)

---

## 🛠️ Manual Setup (If Script Doesn't Work)

### Option 1: Using Xcode Directly

1. **Open Xcode**
   - Launch Xcode application
   - File → New → Project
   - Choose "App" template (iOS)
   - Product Name: `iosApp`
   - Organization Identifier: `com.veles.purchase`
   - Interface: SwiftUI
   - Language: Swift

2. **Add Existing Files**
   - Delete the default ContentView.swift
   - Right-click project → Add Files
   - Navigate to: `/Users/yuriimelnyk/StudioProjects/Purchase/iosApp/iosApp/`
   - Select all `.swift` files
   - Check "Copy items if needed"

3. **Link Shared Framework**
   - Select iosApp target
   - Go to "General" tab
   - Under "Frameworks, Libraries, and Embedded Content"
   - Click + button
   - Click "Add Other..." → "Add Files..."
   - Navigate to: `../shared/build/bin/iosSimulatorArm64/debugFramework/`
   - Select `shared.framework`
   - Set to "Embed & Sign"

4. **Add Build Script**
   - Select iosApp target
   - Go to "Build Phases" tab
   - Click + → New Run Script Phase
   - Drag it BEFORE "Compile Sources"
   - Add script:
     ```bash
     cd "$SRCROOT/.."
     ./gradlew :shared:linkDebugFrameworkIosSimulatorArm64
     ```
   - Uncheck "Based on dependency analysis"

5. **Configure Framework Search Paths**
   - Select iosApp target
   - Go to "Build Settings" tab
   - Search for "Framework Search Paths"
   - Add: `$(SRCROOT)/../shared/build/bin/iosSimulatorArm64/debugFramework`

6. **Disable User Script Sandboxing**
   - In Build Settings
   - Search for "User Script Sandboxing"
   - Set to "No"

### Option 2: Use Android Studio's iOS Run Configuration

Android Studio/IntelliJ IDEA can run iOS apps directly:

1. **Install Kotlin Multiplatform Mobile Plugin**
   - Preferences → Plugins
   - Search for "Kotlin Multiplatform Mobile"
   - Install and restart

2. **Create iOS Run Configuration**
   - Run → Edit Configurations
   - Click + → iOS Application
   - Name: "iosApp"
   - Execution target: Choose iOS Simulator
   - Run: Select iosApp target

3. **Run from IDE**
   - Select "iosApp" configuration
   - Click Run button (or Shift+F10)

---

## 📂 Project Structure

```
Purchase/
├── androidApp/          # Android app (already working)
├── iosApp/
│   ├── iosApp/
│   │   ├── iOSApp.swift        # App entry point
│   │   ├── ContentView.swift   # Main view (uses Compose)
│   │   └── Info.plist
│   └── iosApp.xcodeproj        # Xcode project (will be created)
├── shared/
│   ├── src/
│   │   ├── commonMain/         # Shared Kotlin code
│   │   ├── androidMain/        # Android-specific
│   │   └── iosMain/            # iOS-specific
│   │       └── kotlin/
│   │           └── MainViewController.kt  # iOS entry point
│   └── build/
│       └── bin/
│           └── iosSimulatorArm64/
│               └── debugFramework/
│                   └── shared.framework  # Built by Gradle
└── setup_ios.sh        # Automated setup script
```

---

## 🔧 How It Works

### 1. Kotlin Multiplatform Structure

**Shared Module (`shared/`):**
- Contains all UI code (13 screens)
- ViewModels
- Navigation
- Mock data integration
- Compiles to:
  - `.jar` for Android
  - `.framework` for iOS

### 2. iOS Integration

**MainViewController.kt** (iOS entry point):
```kotlin
fun MainViewController(): UIViewController = ComposeUIViewController {
    App(activity = null)  // Launches KMP app
}
```

**ContentView.swift** (SwiftUI wrapper):
```swift
struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()  // Calls Kotlin
    }
}
```

### 3. Build Process

```
Gradle Build
    ↓
Kotlin/Native Compiler
    ↓
shared.framework (for iOS)
    ↓
Xcode Build
    ↓
iOS App (.app)
```

---

## 🎯 Build Targets

### For Simulator (Development)
```bash
# ARM64 (M1/M2/M3 Macs)
./gradlew :shared:linkDebugFrameworkIosSimulatorArm64

# x64 (Intel Macs)
./gradlew :shared:linkDebugFrameworkIosX64
```

### For Physical Device
```bash
# Release build for App Store
./gradlew :shared:linkReleaseFrameworkIosArm64
```

---

## 🐛 Troubleshooting

### Problem: "shared.framework not found"
**Solution:**
```bash
# Build the framework first
./gradlew :shared:linkDebugFrameworkIosSimulatorArm64

# Check it exists
ls -la shared/build/bin/iosSimulatorArm64/debugFramework/
```

### Problem: "Kotlin compilation errors"
**Solution:**
Check the terminal output from Gradle build:
```bash
./gradlew :shared:compileKotlinIosSimulatorArm64 2>&1 | grep "^e:"
```

### Problem: "Xcode project not found"
**Solution:**
Run the setup script:
```bash
./setup_ios.sh
```

### Problem: "Build script fails in Xcode"
**Solution:**
1. Disable "User Script Sandboxing" in Build Settings
2. Or build framework manually before Xcode build:
```bash
./gradlew :shared:linkDebugFrameworkIosSimulatorArm64
```

### Problem: "Module 'shared' not found"
**Solution:**
1. Check Framework Search Paths in Build Settings
2. Should include: `$(SRCROOT)/../shared/build/bin/iosSimulatorArm64/debugFramework`
3. Clean build folder: Product → Clean Build Folder (⌘⇧K)

### Problem: "iOS Simulator not showing"
**Solution:**
1. Open Xcode → Window → Devices and Simulators
2. Add a new simulator (iPhone 15 Pro recommended)
3. Or run from command line:
```bash
xcrun simctl list devices
xcrun simctl boot "iPhone 15 Pro"
```

---

## ✅ Verification Checklist

Before running iOS app:

- [ ] Shared framework builds successfully
  ```bash
  ./gradlew :shared:linkDebugFrameworkIosSimulatorArm64
  ```
  
- [ ] Framework file exists
  ```bash
  ls shared/build/bin/iosSimulatorArm64/debugFramework/shared.framework
  ```

- [ ] Xcode project exists
  ```bash
  ls iosApp/iosApp.xcodeproj
  ```

- [ ] Swift files are present
  ```bash
  ls iosApp/iosApp/*.swift
  ```

- [ ] iOS Simulator is available
  ```bash
  xcrun simctl list devices | grep iPhone
  ```

---

## 🎬 Running the App

### From Terminal
```bash
# 1. Build framework
./gradlew :shared:linkDebugFrameworkIosSimulatorArm64

# 2. Open in Xcode
open iosApp/iosApp.xcodeproj

# 3. In Xcode: Select simulator and click Run (⌘R)
```

### From Xcode
1. Open `iosApp/iosApp.xcodeproj`
2. Wait for indexing to complete
3. Select scheme: **iosApp**
4. Select destination: **iPhone 15 Pro** (or any simulator)
5. Click **Run** button (▶️) or press **⌘R**

### From Android Studio (with KMM plugin)
1. Install Kotlin Multiplatform Mobile plugin
2. Restart IDE
3. Run → Edit Configurations → + → iOS Application
4. Select iosApp target
5. Run configuration

---

## 📱 What You'll See

When the iOS app launches, you should see:

1. **MainScreen** - Collection list with 3 mock collections:
   - 🏠 Home
   - 🏢 Work
   - 🎁 Gifts

2. **Same UI as Android** - All 13 screens:
   - CollectionListScreen
   - PurchaseListScreen
   - PurchaseEditScreen
   - CategoryScreen
   - HistoryScreen
   - SettingsPurchaseScreen
   - SkuListScreen
   - SkuEditScreen
   - SkuStatisticsScreen
   - And more!

3. **Mock Data** - All test data from mockDomain module

---

## 🚀 Quick Commands Cheat Sheet

```bash
# Build framework
./gradlew :shared:linkDebugFrameworkIosSimulatorArm64

# Open Xcode
open iosApp/iosApp.xcodeproj

# List simulators
xcrun simctl list devices

# Boot simulator
xcrun simctl boot "iPhone 15 Pro"

# Build from command line (after Xcode project setup)
xcodebuild -project iosApp/iosApp.xcodeproj \
  -scheme iosApp \
  -destination 'platform=iOS Simulator,name=iPhone 15 Pro' \
  build

# Run from command line
xcodebuild -project iosApp/iosApp.xcodeproj \
  -scheme iosApp \
  -destination 'platform=iOS Simulator,name=iPhone 15 Pro' \
  run
```

---

## 🎯 Next Steps After First Launch

1. **Test All Screens**
   - Navigate through all 13 screens
   - Verify mock data displays correctly
   - Test search, swipe-to-delete, etc.

2. **Compare with Android**
   - Run both apps side by side
   - Verify UI matches
   - Note any platform-specific differences

3. **Document Issues**
   - Create `IOS_TESTING_RESULTS.md`
   - Screenshot any problems
   - List platform-specific bugs

4. **Complete Phase 3**
   - Mark as 100% complete
   - Update ROADMAP
   - Prepare for Phase 4

---

## 📚 Additional Resources

### Apple Documentation
- [SwiftUI](https://developer.apple.com/xcode/swiftui/)
- [Xcode](https://developer.apple.com/xcode/)
- [iOS Simulator](https://developer.apple.com/documentation/xcode/running-your-app-in-simulator-or-on-a-device)

### Kotlin Multiplatform
- [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)
- [KMP Documentation](https://kotlinlang.org/docs/multiplatform.html)
- [iOS Integration](https://kotlinlang.org/docs/multiplatform-ios-integration.html)

---

## 🎉 Success Criteria

iOS build is successful when:
- ✅ Xcode project opens without errors
- ✅ Scheme "iosApp" is available
- ✅ Build succeeds (⌘B)
- ✅ App launches in simulator (⌘R)
- ✅ MainScreen displays with 3 collections
- ✅ Navigation between screens works
- ✅ Mock data displays correctly

---

**Your iOS app is ready to run! 🍎✨**

Choose your preferred method above and launch your first iOS build!

