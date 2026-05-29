plugins {
    alias(libs.plugins.android.library)
    kotlin("multiplatform")
    alias(libs.plugins.jetbrains.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

compose.resources {
    publicResClass = true
    packageOfResClass = "com.veles.purchase.shared.resources"
    generateResClass = always
}

kotlin {
    // Enable default hierarchy template for proper iOS support
    applyDefaultHierarchyTemplate()

    android {
        namespace = "com.example.shared"
        compileSdk = libs.versions.compileSdk.get().toInt()
        androidResources.enable = true
        experimentalProperties["android.experimental.kmp.enableAndroidResources"] = true
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
        }
    }
//    androidTarget {
//        compilerOptions {
//            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_19)
//        }
//    }

    // iOS targets with framework configuration
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true // Static framework for KMP Compose
            export(libs.compose.multiplatform.resources)
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                // Phase 5 Complete - Using real implementations instead of mocks

                implementation(libs.compose.multiplatform.runtime)
                implementation(libs.compose.multiplatform.ui)
                implementation(libs.compose.multiplatform.material3)
                implementation(libs.compose.multiplatform.materialIconsExtended)
                implementation(libs.compose.multiplatform.foundation)
                implementation(libs.compose.multiplatform.animation)
                api(libs.compose.multiplatform.resources) // API instead of implementation for iOS export
                implementation(libs.coroutines.core)

                // Room KMP Database
                implementation(libs.room.runtime)
                implementation(libs.sqlite.bundled)

                // Date/Time - using version catalog
                implementation(libs.kotlinx.datetime)

                // Koin for DI
                implementation(libs.koin.core)
                implementation(libs.koin.compose)
                implementation(libs.koin.compose.viewmodel)
                implementation(libs.lifecycle.viewmodel.compose.kmp)
                implementation(libs.savedstate.kmp)

                // Navigation
                implementation(libs.nav3.ui)
                implementation(libs.nav3.lifecycle.viewmodel)

                // Serialization
                implementation(libs.kotlinx.serialization.json)

                // Ktor Client (Network)
                implementation(libs.ktor.core)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)
                implementation(libs.ktor.client.logging)
                implementation(libs.ktor.client.auth)

                // Firebase KMP (GitLive)
                implementation(libs.firebase.kmp.common)
                implementation(libs.firebase.kmp.firestore)
                implementation(libs.firebase.kmp.auth)
                implementation(libs.firebase.kmp.storage)
                implementation(libs.firebase.kmp.messaging)
            }
        }

        val androidMain by getting {
            dependencies {
                // Compose
                implementation(libs.compose.ui.tooling.preview)
                implementation(libs.androidx.activity)

                // ConstraintLayout Compose (Android only)
                implementation(libs.compose.constraintlayout)

                // Lifecycle ViewModel (Android only)
                implementation(libs.androidx.lifecycle.viewmodel)

                // Navigation Compose (Android only - iOS has savedstate issues)
                implementation(libs.nav3.ui)

                // Koin Android
                implementation(libs.koin.core)
                implementation(libs.koin.compose)
                implementation(libs.koin.android)
                implementation(libs.koin.androidx.compose)
                implementation(libs.koin.compose.viewmodel)

                // DateTime - explicit for Android to ensure it's included in APK
                implementation(libs.kotlinx.datetime)

                // Biometric Authentication
                implementation(libs.biometric)

                // Google Sign-In / Credential Manager
                implementation(libs.credentials)
                implementation(libs.credentials.play.services.auth)
                implementation(libs.identity)

                // Room Database (Android)
                implementation(libs.room.runtime)
                implementation(libs.room.ktx)

                // Ktor Android Engine
                implementation(libs.ktor.client.okhttp)

                // Activity Compose
                implementation(libs.compose.activity)

                implementation(project.dependencies.platform(libs.firebase.bom))
                implementation(libs.bundles.firebase.data)
                implementation(libs.firebase.ui.auth)
                implementation(libs.firebase.ui.storage)
            }
        }

        val iosMain by getting {
            dependencies {
                // Ktor iOS Engine
                implementation(libs.ktor.client.darwin)
            }
        }

        // iOS source sets are auto-configured by applyDefaultHierarchyTemplate()
        // No manual configuration needed - dependencies from commonMain are inherited
    }
}

dependencies {
    // Phase 5: Enable KSP for all platforms - migrating to real Room database
    add("kspAndroid", libs.room.compiler)
    add("kspIosSimulatorArm64", libs.room.compiler)
    add("kspIosArm64", libs.room.compiler)
}

// android {
//    namespace = "com.example.shared"
//    compileSdk = libs.versions.compileSdk.get().toInt()
//
//    defaultConfig {
//        minSdk = libs.versions.minSdk.get().toInt()
//    }
//
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_19
//        targetCompatibility = JavaVersion.VERSION_19
//    }
//
//    buildFeatures {
//        compose = true
//    }
// }

// Ensure resource generation happens before Kotlin compilation
tasks.configureEach {
    if (name.contains("compileKotlin", ignoreCase = true)) {
        dependsOn("generateComposeResClass")
    }
}

// Copy compose resources to iOS framework with package-aware structure
tasks.register<Copy>("copyComposeResourcesToIosFramework") {
    val buildType = System.getenv("CONFIGURATION") ?: "Debug"
    val buildTypeLower = buildType.lowercase()

    // Depend on all resource preparation tasks
    dependsOn("generateComposeResClass")
    dependsOn("prepareComposeResourcesTaskForCommonMain")
    dependsOn("copyNonXmlValueResourcesForCommonMain")
    dependsOn("convertXmlValueResourcesForCommonMain")

    // Source: prepared resources from commonMain
    from(layout.buildDirectory.dir("generated/compose/resourceGenerator/preparedResources/commonMain"))

    listOf("iosArm64", "iosSimulatorArm64").forEach { target ->
        // Copy to framework compose-resources root
        into(layout.buildDirectory.dir("bin/$target/${buildTypeLower}Framework/shared.framework/compose-resources"))

        // Also create package-based path for compatibility
        doLast {
            val frameworkPath = layout.buildDirectory.dir("bin/$target/${buildTypeLower}Framework/shared.framework").get().asFile
            val resourcesRoot = File(frameworkPath, "compose-resources/composeResources")
            val packagePath = File(resourcesRoot, "com.veles.purchase.shared.resources")

            if (resourcesRoot.exists() && !packagePath.exists()) {
                packagePath.mkdirs()
                // Copy all resources to package path as well
                resourcesRoot.listFiles()?.forEach { file ->
                    if (file.isDirectory && file.name != "com.veles.purchase.shared.resources") {
                        val destDir = File(packagePath, file.name)
                        file.copyRecursively(destDir, overwrite = true)
                    }
                }
            }
        }
    }
}

// Make iOS framework tasks depend on resource copy
tasks.configureEach {
    if (name.contains("linkDebugFramework") || name.contains("linkReleaseFramework")) {
        dependsOn("copyComposeResourcesToIosFramework")
    }
}
