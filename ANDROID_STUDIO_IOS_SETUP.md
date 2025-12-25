# 📱 Android Studio - iOS Setup Visual Guide

**Follow these screenshots/instructions to add iOS run configuration**

---

## Step 1: Open Run Configurations

**Method 1:**
- Click the dropdown next to the Run button (top toolbar)
- Currently shows: "androidApp" or "Android"
- Click "Edit Configurations..."

**Method 2:**
- Menu: Run → Edit Configurations...

**Method 3:**
- Keyboard shortcut: No direct shortcut, use menu

---

## Step 2: Add New iOS Configuration

In the "Run/Debug Configurations" dialog:

```
┌─────────────────────────────────────────────────┐
│ Run/Debug Configurations                        │
├─────────────────────────────────────────────────┤
│ ┌─────────────┐  ┌──────────────────────────┐  │
│ │ [+] Add New │  │                          │  │
│ │ [-] Remove  │  │                          │  │
│ │ [ ] Copy    │  │                          │  │
│ │             │  │                          │  │
│ │ Configs:    │  │   Configuration Details  │  │
│ │ Android App │  │                          │  │
│ │   androidApp│  │                          │  │
│ │             │  │                          │  │
│ │             │  │                          │  │
│ └─────────────┘  └──────────────────────────┘  │
└─────────────────────────────────────────────────┘
```

1. Click the **[+]** button (top left)
2. A menu will appear
3. Scroll down to find: **"iOS Application"**
   - If you DON'T see this option, you need to install the KMM plugin first!
4. Click **"iOS Application"**

---

## Step 3: Configure iOS Application

After clicking "iOS Application", you'll see:

```
┌─────────────────────────────────────────────────────────────┐
│ iOS Application Configuration                               │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│ Name: [iosApp                                  ]            │
│                                                             │
│ Execution target: [Choose Device ▼]                        │
│                   Options:                                  │
│                   • iPhone 15 Pro                           │
│                   • iPhone 15                               │
│                   • iPhone 14 Pro                           │
│                   • iPad Pro 12.9-inch                      │
│                   • ...                                     │
│                                                             │
│ [ ] Allow running multiple instances                       │
│                                                             │
│ [Apply]  [OK]  [Cancel]                                     │
└─────────────────────────────────────────────────────────────┘
```

**Fill in:**
1. **Name:** `iosApp` (or any name you prefer)
2. **Execution target:** Select any iPhone simulator
   - Recommended: "iPhone 15 Pro"
   - Any simulator will work
3. Click **OK**

---

## Step 4: Select and Run

Back in the main window:

```
┌────────────────────────────────────────────────────────────┐
│ Android Studio                                    ⚙  ≡  ×  │
├────────────────────────────────────────────────────────────┤
│ File  Edit  View  Navigate  Code  Analyze  Refactor  ...  │
├────────────────────────────────────────────────────────────┤
│ [iosApp ▼] [▶ Run] [🐛 Debug] [⏹ Stop]                    │
│  ^^^^^^^                                                   │
│  Select this!                                              │
└────────────────────────────────────────────────────────────┘
```

1. **Select Configuration:**
   - Click the dropdown (currently shows "androidApp")
   - Select **"iosApp"** from the list

2. **Run the App:**
   - Click the green **Run** button (▶)
   - Or press keyboard shortcut for run

3. **Watch the Magic:**
   - Gradle builds shared framework
   - iOS Simulator launches
   - App installs and runs
   - You see your app on iOS! 🎉

---

## 📸 What You'll See

### Before Installation (Terminal Output):
```
> Task :shared:compileKotlinIosSimulatorArm64
> Task :shared:linkDebugFrameworkIosSimulatorArm64

BUILD SUCCESSFUL in 15s
```

### Simulator Launching:
- iOS Simulator window opens
- Shows iPhone home screen
- App icon appears
- App launches automatically

### Your App Running:
- MainScreen with 3 collections
- Exactly like Android version
- Same navigation, same data
- Just on iOS! 🍎

---

## 🆘 Troubleshooting

### Issue: Don't See "iOS Application" Option

**Problem:** KMM plugin not installed

**Solution:**
1. Go to: Android Studio → Preferences (⌘,)
2. Select: Plugins
3. Click: Marketplace tab
4. Search: "Kotlin Multiplatform Mobile"
5. Click: Install
6. Click: Restart IDE
7. Try Step 2 again

### Issue: "No iOS Simulators Available"

**Problem:** Xcode or simulators not installed

**Solution:**
1. Install Xcode from App Store
2. Open Xcode once (to accept license)
3. Xcode → Preferences → Platforms
4. Download iOS platform if needed
5. Close Xcode
6. Try again in Android Studio

### Issue: Build Fails "Framework not found"

**Problem:** First build issue

**Solution:**
1. Build manually first:
   ```bash
   cd /Users/yuriimelnyk/StudioProjects/Purchase
   ./gradlew :shared:linkDebugFrameworkIosSimulatorArm64
   ```
2. Then run from Android Studio

### Issue: "Execution target empty"

**Problem:** No simulators detected

**Solution:**
1. Run in terminal:
   ```bash
   xcrun simctl list devices
   ```
2. If empty, install Xcode
3. Restart Android Studio

---

## 🎯 Success Indicators

You'll know it's working when:

1. **Configuration Dropdown Shows:**
   ```
   [iosApp ▼]  <- You can select this
   ```

2. **Run Button is Green:**
   ```
   [▶ Run]  <- Clickable and active
   ```

3. **Terminal Shows:**
   ```
   BUILD SUCCESSFUL
   Launching iPhone 15 Pro...
   ```

4. **Simulator Opens:**
   - iOS Simulator app launches
   - Shows iPhone interface
   - Your app starts

5. **App Appears:**
   - MainScreen with collections
   - Same as Android version
   - iOS status bar at top

---

## 📊 Where Things Are

```
Android Studio Interface
┌────────────────────────────────────────────────┐
│ [iosApp ▼] [▶ Run]  <-- Select & Run here     │
├────────────────────────────────────────────────┤
│                                                │
│ Project Structure (left sidebar):              │
│ └── Purchase                                   │
│     ├── androidApp    <-- Android stuff       │
│     ├── iosApp        <-- iOS stuff           │
│     ├── shared        <-- Shared code         │
│     ├── mockDomain    <-- Mock data           │
│     └── ...                                    │
│                                                │
│ Run Tool Window (bottom):                     │
│ Shows build output and logs                   │
│                                                │
└────────────────────────────────────────────────┘
```

---

## ⏱️ Timeline

**First time:**
- Plugin install: ~2 minutes
- IDE restart: ~30 seconds
- Configuration setup: ~1 minute
- First build: ~30-60 seconds
- Simulator launch: ~10-20 seconds
- **Total:** ~5-10 minutes

**Subsequent runs:**
- Build: ~10-30 seconds
- Launch: ~5-10 seconds
- **Total:** ~15-40 seconds

---

## 🎉 You're Done!

After this initial setup:
- Just select "iosApp" from dropdown
- Click Run
- App builds and launches
- No manual steps needed!

**Both Android and iOS run from the same IDE!** 🚀

---

## 📝 Quick Reference Card

```
┌──────────────────────────────────────────────┐
│ iOS RUN QUICK REFERENCE                      │
├──────────────────────────────────────────────┤
│                                              │
│ 1. Install KMM Plugin                        │
│    Preferences → Plugins → Install           │
│                                              │
│ 2. Create iOS Config                         │
│    Run → Edit Configurations → + → iOS App  │
│                                              │
│ 3. Select & Run                              │
│    [iosApp ▼] → [▶ Run]                      │
│                                              │
│ That's it!                                   │
│                                              │
└──────────────────────────────────────────────┘
```

---

**Now you know exactly where to click!** 🎯

Follow these steps and you'll have iOS running in minutes.

