# 🍎 iOS Setup - Step by Step Instructions

**You're almost there! Just follow these steps:**

---

## Option 1: Quick Setup (Recommended)

### Step 1: Build the Framework
Open Terminal and run:
```bash
cd /Users/yuriimelnyk/StudioProjects/Purchase
./gradlew :shared:linkDebugFrameworkIosSimulatorArm64
```

Wait for "BUILD SUCCESSFUL"

### Step 2: Create Xcode Project

Since you don't have an Xcode project yet, create it:

1. **Open Xcode** (should already be opening)

2. **Create New Project:**
   - File → New → Project
   - Choose **App** template (iOS)
   - Click Next

3. **Configure Project:**
   - Product Name: **iosApp**
   - Team: (your team or leave blank)
   - Organization Identifier: **com.veles.purchase**
   - Interface: **SwiftUI**
   - Language: **Swift**
   - Storage: **None**
   - Click Next

4. **Save Location:**
   - Navigate to: `/Users/yuriimelnyk/StudioProjects/Purchase/iosApp`
   - **IMPORTANT:** Uncheck "Create Git repository"
   - Click Create

5. **Replace Default Files:**
   - Delete the default `ContentView.swift` and `iosAppApp.swift`
   - Drag these files from Finder into Xcode project:
     - `/Users/yuriimelnyk/StudioProjects/Purchase/iosApp/iosApp/iOSApp.swift`
     - `/Users/yuriimelnyk/StudioProjects/Purchase/iosApp/iosApp/ContentView.swift`
     - `/Users/yuriimelnyk/StudioProjects/Purchase/iosApp/iosApp/Info.plist`

6. **Add Shared Framework:**
   - Select **iosApp** target (top of left sidebar)
   - Go to **General** tab
   - Scroll to "Frameworks, Libraries, and Embedded Content"
   - Click **+** button
   - Click "Add Other..." → "Add Files..."
   - Navigate to: `/Users/yuriimelnyk/StudioProjects/Purchase/shared/build/bin/iosSimulatorArm64/debugFramework/`
   - Select **shared.framework**
   - Set "Embed" to **Embed & Sign**

7. **Add Build Script:**
   - Select **iosApp** target
   - Go to **Build Phases** tab
   - Click **+** → **New Run Script Phase**
   - **Drag it BEFORE "Compile Sources"**
   - Expand the script phase
   - Paste this script:
     ```bash
     cd "$SRCROOT/.."
     ./gradlew :shared:linkDebugFrameworkIosSimulatorArm64
     ```
   - Name it: "Build Kotlin Framework"

8. **Configure Build Settings:**
   - Select **iosApp** target
   - Go to **Build Settings** tab
   - Search for: **Framework Search Paths**
   - Double-click the value
   - Add new entry: `$(SRCROOT)/../shared/build/bin/iosSimulatorArm64/debugFramework`
   
   - Search for: **User Script Sandboxing**
   - Set to: **No**

9. **Run the App:**
   - Select simulator: **iPhone 15 Pro** (or any available)
   - Click **Run** button (▶️) or press **⌘R**

---

## Option 2: Use Android Studio (Easier!)

If you have Android Studio with KMM plugin:

1. **Install Plugin:**
   - Android Studio → Preferences → Plugins
   - Search: "Kotlin Multiplatform Mobile"
   - Install and restart

2. **Create iOS Configuration:**
   - Run → Edit Configurations
   - Click **+** → **iOS Application**
   - Name: "iosApp"
   - Execution target: Select iOS Simulator
   - Click OK

3. **Run:**
   - Select "iosApp" configuration from dropdown
   - Click Run button (green ▶️)
   - App will launch in iOS Simulator!

---

## Option 3: Manual Build from Terminal

If you want to build without IDE:

```bash
# 1. Build framework
cd /Users/yuriimelnyk/StudioProjects/Purchase
./gradlew :shared:linkDebugFrameworkIosSimulatorArm64

# 2. List available simulators
xcrun simctl list devices | grep iPhone

# 3. Boot a simulator (choose one from list)
xcrun simctl boot "iPhone 15 Pro"

# 4. Install and run (after Xcode project is set up)
xcodebuild -project iosApp/iosApp.xcodeproj \
  -scheme iosApp \
  -destination 'platform=iOS Simulator,name=iPhone 15 Pro' \
  build install
```

---

## 🆘 Need Help?

### Check Framework Built Successfully
```bash
ls -la /Users/yuriimelnyk/StudioProjects/Purchase/shared/build/bin/iosSimulatorArm64/debugFramework/shared.framework
```

Should show the framework directory.

### Check Swift Files Exist
```bash
ls -la /Users/yuriimelnyk/StudioProjects/Purchase/iosApp/iosApp/*.swift
```

Should show:
- iOSApp.swift
- ContentView.swift

### Check Build Errors
```bash
cd /Users/yuriimelnyk/StudioProjects/Purchase
./gradlew :shared:compileKotlinIosSimulatorArm64 2>&1 | grep -i error
```

---

## 🎯 What You're Building

Your iOS app will:
- ✅ Use 100% shared Kotlin code from `shared/` module
- ✅ Display all 13 screens (same as Android)
- ✅ Show mock data from mockDomain
- ✅ Have identical navigation and features
- ✅ Run on iOS Simulator or real iPhone

---

## 📱 First Time Setup is One-Time Only

After initial setup, you can:
- Run directly from Xcode (just click Run)
- Or from Android Studio (select iosApp configuration)
- Framework rebuilds automatically before each run

---

**Choose your preferred option above and start building!** 🚀

Most developers find **Option 2 (Android Studio with KMM plugin)** the easiest for KMP projects.

