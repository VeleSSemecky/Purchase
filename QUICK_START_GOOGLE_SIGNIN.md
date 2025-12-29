# ⚡ QUICK START - Google Sign-In Setup

**5-Minute Setup Guide** ⏱️

---

## 🎯 Prerequisites

1. ✅ Firebase project created
2. ✅ Google Sign-In enabled in Firebase Console
3. ⬜ Server Client ID (we'll get this)
4. ⬜ google-services.json (Android)
5. ⬜ GoogleService-Info.plist (iOS)

---

## 📝 Step-by-Step Setup

### Step 1: Get Your Server Client ID (2 min)

1. Go to [Firebase Console](https://console.firebase.google.com)
2. Select your project
3. Go to **Authentication** → **Sign-in method**
4. Click on **Google**
5. Expand **Web SDK configuration**
6. Copy the **Web client ID** (looks like: `xxxxx.apps.googleusercontent.com`)

### Step 2: Update EnvironmentConfig (30 seconds)

**File:** `shared/src/commonMain/kotlin/com/veles/purchase/config/EnvironmentConfig.kt`

Replace:
```kotlin
const val SERVER_CLIENT_ID = "YOUR_GOOGLE_OAUTH_CLIENT_ID.apps.googleusercontent.com"
```

With your actual ID:
```kotlin
const val SERVER_CLIENT_ID = "123456789-abc123.apps.googleusercontent.com"
```

---

## 📱 Android Setup (1 min)

### Step 3: Add google-services.json

1. Download from Firebase Console → Project Settings → General → Your apps → Android app
2. Save to: `androidApp/google-services.json`
3. That's it for Android! ✅

### Step 4: Add SHA-1 Fingerprint (Debug)

```bash
# Get SHA-1
keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android | grep SHA1

# Add to Firebase Console → Project Settings → SHA certificate fingerprints
```

### Test Android:

```bash
./gradlew :androidApp:installDebug
```

---

## 🍎 iOS Setup (2 min)

### Step 5: Install CocoaPods

```bash
cd iosApp
pod install
cd ..
```

### Step 6: Add GoogleService-Info.plist

1. Download from Firebase Console → Project Settings → General → Your apps → iOS app
2. Add to: `iosApp/iosApp/GoogleService-Info.plist`

### Step 7: Configure URL Scheme

1. Open `GoogleService-Info.plist`
2. Find `REVERSED_CLIENT_ID` (looks like: `com.googleusercontent.apps.xxxxx`)
3. Add to `iosApp/iosApp/Info.plist`:

```xml
<key>CFBundleURLTypes</key>
<array>
    <dict>
        <key>CFBundleURLSchemes</key>
        <array>
            <string>com.googleusercontent.apps.YOUR_REVERSED_CLIENT_ID_HERE</string>
        </array>
    </dict>
</array>
```

### Test iOS:

```bash
open iosApp/iosApp.xcworkspace
# Then build and run in Xcode (Cmd+R)
```

---

## ✅ Verification Checklist

### Android:
- [ ] `EnvironmentConfig.SERVER_CLIENT_ID` updated
- [ ] `androidApp/google-services.json` exists
- [ ] SHA-1 fingerprint added to Firebase
- [ ] Build successful
- [ ] Login screen appears
- [ ] Google Sign-In works

### iOS:
- [ ] `EnvironmentConfig.SERVER_CLIENT_ID` updated
- [ ] CocoaPods installed
- [ ] `GoogleService-Info.plist` added
- [ ] URL Scheme configured in Info.plist
- [ ] Build successful in Xcode
- [ ] Login screen appears
- [ ] Google Sign-In works

---

## 🐛 Troubleshooting

### Android Issues:

**"Sign-in failed"**
- Check SERVER_CLIENT_ID is correct
- Verify SHA-1 fingerprint is added
- Ensure google-services.json is in androidApp folder

**"No Google accounts found"**
- Add Google account to emulator/device
- Settings → Accounts → Add account → Google

### iOS Issues:

**"Pod install failed"**
```bash
cd iosApp
pod repo update
pod install
```

**"Sign-in modal doesn't appear"**
- Verify GoogleService-Info.plist is added to Xcode project
- Check URL Scheme is configured correctly
- Clean build folder (Cmd+Shift+K)

**"Invalid client ID"**
- Verify SERVER_CLIENT_ID matches Firebase Console
- Check REVERSED_CLIENT_ID in URL Scheme

---

## 🎉 Success!

If you see:
1. Login screen on app launch ✅
2. "Sign in with Google" button ✅
3. Google account picker (Android) or modal (iOS) ✅
4. Successfully sign in ✅
5. Navigate to main screen ✅

**Congratulations! Google Sign-In is working!** 🎊

---

## 📚 Next Steps

- Test on physical devices
- Add error analytics
- Customize login UI
- Add "Sign out" functionality
- Test session persistence

---

## 📖 Full Documentation

See `GOOGLE_SIGNIN_COMPLETE_IMPLEMENTATION.md` for:
- Complete architecture details
- Code explanations
- Advanced configuration
- Security best practices

---

**Total Setup Time:** ~5 minutes  
**Platforms:** Android ✅ | iOS ✅  
**Status:** Ready to test! 🚀

