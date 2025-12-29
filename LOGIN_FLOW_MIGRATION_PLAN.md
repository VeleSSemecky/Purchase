# 🔧 LOGIN FLOW MIGRATION TO KMP - ACTION PLAN

**Date:** December 26, 2025  
**Status:** 🚨 **CRITICAL ISSUE IDENTIFIED**

---

## 🚨 PROBLEM IDENTIFIED

The login flow is **NOT migrated** to KMP. The app is trying to use the old Android-specific modules:
- ❌ `presentation` module (Android-only)
- ❌ `domain` module (Android-only)
- ❌ `data` module (Android-only)

But the androidApp is configured to use:
- ✅ `shared` module (KMP)
- ✅ `androidApp` module (KMP Android target)

**Result:** Login screen doesn't work because it doesn't exist in the KMP shared module!

---

## 📊 Current State Analysis

### ✅ What's Working (KMP):
- Firebase KMP repositories (AuthWithGoogleRepository, etc.)
- Firebase initialization
- Purchase & Collection screens
- Navigation framework (AppNavigation)

### ❌ What's Missing (NOT migrated):
1. **Login Screen** - NO LoginScreen in shared/commonMain
2. **Login ViewModel** - NO LoginViewModel in shared/commonMain
3. **Login Use Case** - Using old domain module version
4. **Google Sign-In** - Android-specific implementation not wrapped for KMP

### 🔀 What's Duplicated:
- AuthWithGoogleRepository exists in BOTH:
  - ✅ `shared/src/commonMain` (KMP - NEW)
  - ❌ `data/src/main` (Android only - OLD)

---

## 🎯 MIGRATION PLAN

### Phase 1: Create Login Screen in KMP ✅

**Files to Create:**
1. `shared/src/commonMain/kotlin/com/veles/purchase/presentation/compose/login/LoginScreen.kt`
2. `shared/src/commonMain/kotlin/com/veles/purchase/presentation/viewmodel/login/LoginViewModel.kt`

**Files to Modify:**
3. `shared/src/commonMain/kotlin/com/veles/purchase/presentation/navigation/AppNavigation.kt` - Add login route
4. `shared/src/commonMain/kotlin/com/veles/purchase/presentation/navigation/Route.kt` - Add login route
5. `shared/src/commonMain/kotlin/com/veles/purchase/di/ViewModelModule.kt` - Add LoginViewModel

### Phase 2: Create Platform-Specific Google Sign-In ✅

**Android:**
6. `shared/src/androidMain/kotlin/com/veles/purchase/platform/auth/GoogleSignInHelper.android.kt`

**iOS:**
7. `shared/src/iosMain/kotlin/com/veles/purchase/platform/auth/GoogleSignInHelper.ios.kt`

**Common:**
8. `shared/src/commonMain/kotlin/com/veles/purchase/platform/auth/GoogleSignInHelper.kt` (expect)

### Phase 3: Update App Entry Point ✅

**Files to Modify:**
9. `shared/src/commonMain/kotlin/com/veles/purchase/App.kt` - Start with Login if not authenticated

### Phase 4: Remove Old Module Dependencies ✅

**Files to Modify:**
10. `androidApp/build.gradle.kts` - Remove presentation/domain/data dependencies (if present)

---

## 📝 DETAILED IMPLEMENTATION

### 1. Login Screen (Compose)

```kotlin
@Composable
fun LoginScreen(
    onNavigateToMain: () -> Unit
) {
    val viewModel: LoginViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()
    
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo
        // Google Sign-In Button
        GoogleSignInButton(
            onClick = { viewModel.signInWithGoogle() },
            isLoading = state.isLoading
        )
        
        // Error handling
        if (state.error != null) {
            ErrorMessage(state.error)
        }
    }
}
```

### 2. Login ViewModel

```kotlin
class LoginViewModel(
    private val authRepository: AuthWithGoogleRepository,
    private val googleSignInHelper: GoogleSignInHelper
) : ViewModel() {
    
    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()
    
    fun signInWithGoogle() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val idToken = googleSignInHelper.signIn()
                authRepository.firebaseAuthWithGoogle(idToken)
                _state.update { it.copy(isLoading = false, isSuccess = true) }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }
}

data class LoginState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)
```

### 3. Google Sign-In Helper (expect/actual)

**Common:**
```kotlin
expect class GoogleSignInHelper {
    suspend fun signIn(): String  // Returns ID token
}
```

**Android:**
```kotlin
actual class GoogleSignInHelper(
    private val activity: Activity
) {
    actual suspend fun signIn(): String {
        // Use Credential Manager API
        val googleIdOption = GetSignInWithGoogleOption.Builder(SERVER_CLIENT_ID).build()
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
            
        val result = CredentialManager.create(activity)
            .getCredential(request, activity)
            
        return extractIdToken(result)
    }
}
```

**iOS:**
```kotlin
actual class GoogleSignInHelper {
    actual suspend fun signIn(): String {
        // Use Google Sign-In iOS SDK
        // TODO: Implement iOS sign-in
        throw NotImplementedError("iOS Google Sign-In not implemented yet")
    }
}
```

### 4. Navigation Updates

**Add to Route.kt:**
```kotlin
@Serializable
object Login : Route()
```

**Add to AppNavigation.kt:**
```kotlin
composable<Route.Login> {
    LoginScreen(
        onNavigateToMain = {
            navController.navigate(Route.Main) {
                popUpTo(Route.Login) { inclusive = true }
            }
        }
    )
}
```

**Update App.kt:**
```kotlin
@Composable
fun App(activity: Any? = null) {
    val authRepository: AuthWithGoogleRepository = koinInject()
    val isLoggedIn = remember { authRepository.isLoggedIn() }
    
    KoinContext {
        MaterialTheme {
            Surface {
                AppNavigation(
                    startDestination = if (isLoggedIn) Route.Main else Route.Login,
                    activity = activity
                )
            }
        }
    }
}
```

---

## ⚠️ CRITICAL DECISIONS

### Decision 1: Google Sign-In Implementation
**Options:**
- A) Use Credential Manager (Android) + Google Sign-In SDK (iOS)
- B) Use Firebase Authentication UI (both platforms)
- C) Custom implementation

**Recommendation:** Option A (best for KMP)

### Decision 2: Old Modules
**Question:** What to do with presentation/domain/data modules?

**Options:**
- A) Keep them but don't use in androidApp
- B) Delete them entirely
- C) Gradually migrate remaining features

**Recommendation:** Option C (safest approach)

---

## 🚀 EXECUTION STEPS

### Step 1: Immediate Fix (30 min)
1. Create LoginScreen in shared
2. Create LoginViewModel in shared
3. Add Login route to navigation
4. Update app entry point

### Step 2: Google Sign-In Android (1 hour)
1. Create GoogleSignInHelper (expect/actual)
2. Implement Android version
3. Test on Android

### Step 3: Google Sign-In iOS (2 hours)
1. Implement iOS version
2. Add iOS Google Sign-In SDK
3. Test on iOS

### Step 4: Testing (1 hour)
1. Test Android login flow
2. Test iOS login flow
3. Test session persistence
4. Test error scenarios

**Total Time:** ~4.5 hours

---

## 📋 VERIFICATION CHECKLIST

After migration, verify:
- [ ] Login screen appears when not authenticated
- [ ] Google Sign-In button works
- [ ] ID token is obtained
- [ ] Firebase authentication succeeds
- [ ] User is redirected to Main screen
- [ ] Session persists across app restarts
- [ ] Error messages display correctly
- [ ] Works on Android
- [ ] Works on iOS

---

## 🎯 SUCCESS CRITERIA

Migration is complete when:
1. ✅ Login screen exists in shared module
2. ✅ Google Sign-In works on Android
3. ✅ Google Sign-In works on iOS
4. ✅ Authentication flow is fully KMP
5. ✅ No dependencies on old modules
6. ✅ App starts correctly
7. ✅ Session management works

---

## 📚 FILES TO CREATE/MODIFY

**Create (5 files):**
1. LoginScreen.kt
2. LoginViewModel.kt
3. GoogleSignInHelper.kt (common)
4. GoogleSignInHelper.android.kt
5. GoogleSignInHelper.ios.kt

**Modify (4 files):**
6. Route.kt
7. AppNavigation.kt
8. ViewModelModule.kt
9. App.kt

**Total:** 9 files

---

## ⏱️ TIMELINE

**Immediate:** Create basic login screen (working on Android)
**Short-term:** Complete iOS Google Sign-In
**Long-term:** Migrate remaining features from old modules

---

_Created: December 26, 2025_  
_Status: Ready to implement_  
_Priority: 🚨 CRITICAL_  
_Estimated Time: 4.5 hours_

