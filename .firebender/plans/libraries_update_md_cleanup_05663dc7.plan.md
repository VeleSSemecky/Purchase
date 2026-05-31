<!--firebender-plan
name: Libraries Update & MD Cleanup
overview: Clean up ~30 obsolete migration MD files and update libraries in `libs.versions.toml`, prioritizing safe minor/patch updates, fixing the critical koinCompose version mismatch, and noting major-version migrations that need code changes.
todos:
  - id: md-cleanup
    content: "Delete ~30 obsolete migration/status MD files, keep only essential reference docs"
  - id: critical-fix-koin
    content: "Fix koinCompose version mismatch: 3.5.6 → 4.2.1 in libs.versions.toml"
  - id: safe-updates
    content: "Apply all safe minor/patch library updates in libs.versions.toml (Kotlin, KSP, nav, Firebase, Room, Ktor, Koin, Dagger, Camera, Lifecycle, Paging, Compose BOM, Biometric, SDK 36)"
  - id: compose-multiplatform
    content: "Update composeMultiplatform 1.9.3 → 1.10.3 and verify build"
  - id: glide-migration
    content: "Update Glide 4→5 and Landscapist 1→2, update all call sites to new API"
  - id: accompanist-migration
    content: "Replace deprecated Accompanist pager and flow layout with official Compose Foundation APIs"
  - id: dokka-update
    content: "Update Dokka 1.9.20 → 2.2.0 with new configuration DSL"
  - id: consolidate-hardcoded
    content: "Move hardcoded versions in shared/shared.gradle.kts into version catalog"
-->


# Libraries Update & MD Cleanup

## Part 1 — MD Files Cleanup

**Remove ~30 obsolete migration/status docs** (completed phases, build success notes, temporary fix guides):

- All `PHASE_*.md` (PHASE_1_COMPLETE, PHASE_5_5_*, PHASE_5_6_*, PHASE_6_*)
- All `*_COMPLETE.md` and `*_SUCCESS.md` (LOGIN_FLOW_*, VIEWMODEL_*, USECASE_*, MIGRATION_*, IOS_SPM_*, FIREBASE_KMP_*, GOOGLE_SIGNIN_*, COCOAPODS_*)
- Redundant process docs: `LOGIN_FLOW_MIGRATION_PLAN.md`, `LOGIN_FLOW_BUILD_SUCCESSFUL.md`
- Fix/debug docs: `QUICK_FIX_IOS_BUNDLES.md`, `IOS_PRIVACY_BUNDLE_FIX.md`, `IOS_PRIVACY_BUNDLE_RESOLUTION.md`, `IOS_BUILD_ERRORS_COMPLETE_RESOLUTION.md`
- Noise docs: `STRUCTURE_VERIFICATION_REPORT.md`, `PROJECT_RESTORED_GUIDE.md`, `DOCUMENTATION_CLEANUP_SUMMARY.md`, `FINAL_COMPLETE_STATUS.md`, `FINAL_MIGRATION_SUMMARY.md`

**Keep** (useful reference):
- `README.md`, `ROADMAP.md`
- `IOS_RUN_GUIDE.md`, `IOS_FILE_INDEX.md`, `IOS_GOOGLE_SIGNIN_IMPLEMENTATION.md`
- `FIREBASE_KMP_MIGRATION_GUIDE.md`, `QUICK_START_GOOGLE_SIGNIN.md`
- `EXCLUDED_FROM_MIGRATION.md`
- `VIEWMODEL_RENAMING.md`, `VIEWMODEL_USECASE_MIGRATION.md`, `USECASE_LAYER_MIGRATION.md` (ongoing reference)
- `mockDomain/README.md`, `mockDomain/QUICKSTART.md`, `mockDomain/STRUCTURE.md`

---

## Part 2 — Library Updates in `libs.versions.toml`

### Critical Fix (bug — version mismatch)
- `koinCompose`: `3.5.6` → **`4.2.1`** (must match `koin` major version, currently broken)

### Safe Minor / Patch Updates
- `kotlin` + `kotlinMultiplatform`: `2.3.0` → **`2.3.21`**
- `ksp`: `2.3.4` → **`2.3.7`** (new independent versioning scheme)
- `navigation`: `2.9.6` → **`2.9.7`**
- `firebaseBom`: `33.16.0` → **`34.12.0`**
- `room`: `2.7.2` → **`2.8.4`**
- `ktor`: `3.0.2` → **`3.4.3`**
- `koin`: `4.1.0` → **`4.2.1`**
- `dagger`: `2.50` → **`2.59.2`**
- `camera`: `1.3.1` → **`1.5.3`**
- `lifecycleRuntime` / `lifecycleViewmodel` / `lifecycleSavedstate`: `2.8.7` → **`2.10.0`**
- `paging`: `3.3.6` → **`3.4.1`**
- `composeBom`: `2025.05.01` → **`2026.03.01`**
- `biometric`: `1.2.0-alpha05` → **`1.4.0-alpha04`**
- `targetSdk` / `compileSdk`: `35` → **`36`** (Android 16 — Play Store requirement from Aug 2026)

### Major Version Updates (require code migration — separate tasks)
- `composeMultiplatform`: `1.9.3` → **`1.10.3`** (unified `@Preview`, Navigation 3 support; generally safe but verify)
- `glide`: `4.16.0` → **`5.0.6`** (new Kotlin-first API, no `GlideApp`, requires call sites update)
- `glideLandscapist`: `1.5.1` → **`2.9.7`** (major API overhaul)
- `dokka`: `1.9.20` → **`2.2.0`** (new configuration DSL, plugin setup changes)

### Hold Separately (high risk)
- `androidGradle` `8.11.2` → `9.2.0` — major AGP upgrade, requires Gradle wrapper update, JDK verification, and testing. Recommend a dedicated branch.

### Deprecation Migrations (code changes needed)
- `composeAccompanistPager` + `composeAccompanistFlowlayout` (`0.36.0`) — **fully removed from Accompanist**. Replace with:
  - `HorizontalPager` / `VerticalPager` from `androidx.compose.foundation`
  - `FlowRow` / `FlowColumn` from `androidx.compose.foundation.layout`

### Consolidate Hardcoded Versions in `shared/shared.gradle.kts`
Multiple hardcoded versions diverge from the catalog (e.g. koin `4.0.0` / `4.1.1`, navigation `2.9.1`, credentials `1.3.0`). Move all to version catalog after updating.
