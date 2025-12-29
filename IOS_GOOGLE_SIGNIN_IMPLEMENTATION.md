# iOS Google Sign-In Implementation

## Date: December 29, 2025

## Overview
This document describes the implementation of Google Sign-In for iOS in the KMP project.

## Architecture

### Components

1. **Swift Side (iosApp)**
   - `GoogleSignInBridge.swift` - Swift wrapper for GoogleSignIn SDK
   - `ContentView.swift` - Initializes the sign-in handler and passes it to Kotlin

2. **Kotlin Side (shared/iosMain)**
   - `IosGoogleSignInProvider.kt` - Stores UIViewController and sign-in handler (set from Swift)
   - `GoogleSignInHelper.ios.kt` - Actual implementation that uses the handler from IosGoogleSignInProvider
   - `MainViewController.kt` - Creates ComposeUIViewController and stores it in IosGoogleSignInProvider

### Flow

1. App starts → `ContentView.swift` creates `MainViewController` from Kotlin
2. `MainViewController` stores UIViewController in `IosGoogleSignInProvider`
3. `ContentView` sets the sign-in handler closure that calls `GoogleSignInBridge`
4. User taps "Sign In with Google" → `LoginViewModel.signInWithGoogle()` is called
5. `GoogleSignInHelper.signIn()` uses the handler from `IosGoogleSignInProvider`
6. The handler calls Swift `GoogleSignInBridge.performSignIn()` which presents Google Sign-In UI
7. User completes sign-in → ID token is returned to Kotlin via completion callback
8. `AuthWithGoogleRepository.firebaseAuthWithGoogle()` authenticates with Firebase

## Configuration

### Info.plist
Required keys:
```xml
<key>GIDClientID</key>
<string>YOUR_IOS_CLIENT_ID.apps.googleusercontent.com</string>
<key>CFBundleURLTypes</key>
<array>
    <dict>
        <key>CFBundleURLSchemes</key>
        <array>
            <string>com.googleusercontent.apps.YOUR_IOS_CLIENT_ID</string>
        </array>
    </dict>
</array>
```

### GoogleService-Info.plist
Should contain `CLIENT_ID` and `REVERSED_CLIENT_ID` from Firebase Console.

## Dependencies
- GoogleSignIn SDK (via Swift Package Manager)
- FirebaseAuth SDK (via Swift Package Manager)

## Troubleshooting

### "UIViewController not available"
Make sure `IosGoogleSignInProvider.setViewController()` or the sign-in handler is set before attempting sign-in.

### "Google Sign-In handler not initialized"
The handler must be set from Swift on app startup. Check `ContentView.swift`.

### Sign-in cancelled/failed
1. Verify GIDClientID is correct in Info.plist
2. Verify URL scheme is properly configured
3. Check that GoogleService-Info.plist is included in the app bundle

## Files Modified

- `/iosApp/iosApp/GoogleSignInBridge.swift` - New file
- `/iosApp/iosApp/ContentView.swift` - Added sign-in handler initialization
- `/iosApp/iosApp/Info.plist` - Added GIDClientID and URL schemes
- `/shared/src/iosMain/kotlin/com/veles/purchase/platform/auth/GoogleSignInHelper.ios.kt` - Implemented
- `/shared/src/iosMain/kotlin/com/veles/purchase/platform/auth/IosGoogleSignInProvider.kt` - New file
- `/shared/src/iosMain/kotlin/com/example/shared/MainViewController.kt` - Updated

