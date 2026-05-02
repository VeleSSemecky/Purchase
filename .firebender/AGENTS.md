# Purchase App — KMP Migration

This is a Kotlin Multiplatform (KMP) project migrating from a legacy Android-only architecture.

## Project Structure

- **`androidApp/`** — KMP Android application module (entry point)
- **`iosApp/`** — KMP iOS application module (entry point)
- **`shared/`** — KMP shared module (business logic, ViewModels, Compose UI)
- **`mockDomain/`** — Mock implementations for testing
- **`presentation/`** — ⚠️ LEGACY REFERENCE ONLY (see below)
- **`domain/`** — Domain layer (use cases, models)
- **`data/`** — Data layer (repositories, Firebase, Room)

## ⚠️ CRITICAL: `presentation` Module is READ-ONLY

The `presentation/` module is the **old Android-only implementation** kept strictly as a **reference**.

**NEVER modify any files inside `presentation/`.**

This includes:
- `presentation/src/main/**` — all Kotlin source files
- `presentation/src/main/res/**` — all Android resources
- `presentation/presentation.gradle.kts` — the module build script
- Any other file under `presentation/`

**Why it exists:** It serves as a reference for the original UI, business logic, and resource implementations during the KMP migration. Consult it to understand legacy behaviour, copy patterns into `shared/`, but never edit it directly.

## Tech Stack

- **Language**: Kotlin (KMP + Android)
- **UI**: Jetpack Compose Multiplatform (`shared/`)
- **DI**: Koin 4.x (KMP-compatible)
- **Networking**: Ktor 3.x
- **Database**: Room (Android), SQLDelight or in-memory (iOS)
- **Auth**: Firebase Auth + Google Sign-In (KMP via GitLive)
- **Navigation**: JetBrains Navigation Compose KMP
- **Image loading**: Landscapist Glide (Android)
- **Async**: Kotlin Coroutines + Flow

## Architecture

- **MVVM** — ViewModels in `shared/commonMain`, platform-specific in `androidApp/`
- **Clean Architecture** — domain → data → presentation layers
- **KMP-first** — all new code goes into `shared/commonMain` where possible

## Code Style

- Use Kotlin idiomatic code (extension functions, data classes, sealed classes)
- Prefer `Flow` over callbacks for async operations
- Use `@Composable` functions for all UI — no XML layouts in new code
- Follow Material Design 3 guidelines
- Use `libs.versions.toml` version catalog for all dependencies — no hardcoded versions

## Migration Rules

- New features → implement in `shared/commonMain` (KMP-compatible)
- Android-only features (camera, biometrics) → `androidApp/` or `shared/androidMain`
- iOS-specific → `shared/iosMain` or `iosApp/`
- When in doubt, check the `presentation/` reference but **write new code in `shared/`**
