# Excluded from Migration

This document lists features/components that were explicitly **excluded from migration** to the new KMP shared module.

## 1. Biometric Authentication

**Decision Date:** December 29, 2024

**Reason:** Biometric authentication was deemed unnecessary for the new application version.

### Removed Components:
- `BiometricScreen.kt` - Compose screen for biometric authentication UI
- `BiometricComposeViewModel.kt` / `BiometricViewModel` - ViewModel for biometric screen
- `BiometricUiState.kt` - UI state data class
- Platform implementations in `platform/biometric/` folder (kept as reference but not used)

### What was NOT removed:
- `Route.Auth.Biometric` - Route is kept but shows placeholder "Biometric - Removed from migration"
- Platform `BiometricAuthenticator` files - kept in case needed for future features

### Impact:
- The biometric authentication flow from the original `presentation` module is not available in the new app
- Any screens that depended on biometric verification need alternative authentication methods

---

## ViewModel Naming Convention

**IMPORTANT:** ViewModels in `shared` module must use the **EXACT SAME NAMES** as in the original modules.

Original module paths:
- `/Users/yuriimelnyk/StudioProjects/Purchase/presentation/` - presentation ViewModels
- `/Users/yuriimelnyk/StudioProjects/Purchase/domain/` - domain layer

### Correct ViewModel Names (DO NOT RENAME):
| Original Name | Location in Shared |
|--------------|-------------------|
| `CollectionPurchaseComposeViewModel` | `mvvm/purchase/collection/` |
| `EditCollectionComposeViewModel` | `mvvm/purchase/collection/` |
| `ListPurchaseViewModel` | `mvvm/purchase/list/` |
| `EditPurchaseViewModel` | `mvvm/purchase/edit/` |
| `ListLaterPurchaseViewModel` | `mvvm/purchase/later/` |
| `HistoryComposeViewModel` | `mvvm/purchase/history/` |
| `CategoryViewModel` | `mvvm/purchase/category/` |
| `SettingPurchaseComposeViewModel` | `mvvm/purchase/setting/` |
| `OutlayGraphViewModel` | `mvvm/sku/statistics/` |
| `SkuEditViewModel` | `mvvm/sku/edit/` |
| `SkuListViewModel` | `mvvm/sku/list/` |
| `LoginViewModel` | `viewmodel/login/` |

---

## How to Add Features Back

If you need to re-enable any excluded feature:

1. Check the original `presentation` module for reference implementation
2. Create UseCase in `shared/src/commonMain/kotlin/com/veles/purchase/domain/usecase/`
3. Create ViewModel in `shared/src/commonMain/kotlin/com/veles/purchase/presentation/mvvm/`
4. Create Screen in `shared/src/commonMain/kotlin/com/veles/purchase/presentation/compose/`
5. Update `AppNavigation.kt` to use the new screen instead of placeholder
6. Register ViewModel and UseCase in DI modules

---

## Notes for Future LLM Agents

When performing migrations on this codebase:

1. **DO NOT** attempt to migrate BiometricViewModel or BiometricScreen - they were intentionally excluded
2. **DO NOT** create use cases for biometric authentication - it's not needed
3. **ALWAYS** use the EXACT same ViewModel names as in the original modules
4. **NEVER** rename ViewModels - check the original paths first
5. If asked about biometric features, refer to this document
6. The `platform/biometric/` folder exists but is NOT actively used in the app

### Quick Reference for Original Files
- ViewModels: `/Users/yuriimelnyk/StudioProjects/Purchase/presentation/src/main/java/com/veles/purchase/presentation/presentation/mvvm/`
- UseCases: `/Users/yuriimelnyk/StudioProjects/Purchase/domain/src/main/java/com/veles/purchase/domain/usecase/`
- Models: `/Users/yuriimelnyk/StudioProjects/Purchase/domain/src/main/java/com/veles/purchase/domain/model/`

