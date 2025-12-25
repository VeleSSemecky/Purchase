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

    androidTarget {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_19)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
            // Export compose resources
            export(compose.components.resources)
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                // mockDomain - mock data for Phase 2-3
                implementation(project(":mockDomain"))

                implementation(compose.runtime)
                implementation(compose.ui)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)
                implementation(compose.foundation)
                implementation(compose.animation)
                api(compose.components.resources)  // API instead of implementation for iOS export
                implementation(libs.coroutines.core)

                // Room KMP Database
                implementation(libs.room.runtime)
                implementation(libs.sqlite.bundled)

                // Date/Time - using version catalog (0.9.0 - latest)
                implementation(libs.kotlinx.datetime)

                // Koin for DI
                implementation("io.insert-koin:koin-core:4.0.0")
                implementation("io.insert-koin:koin-compose:4.0.0")
                implementation("io.insert-koin:koin-compose-viewmodel:4.0.0")

                // Navigation
                implementation("org.jetbrains.androidx.navigation:navigation-compose:2.9.1")

                // Serialization
                implementation(libs.kotlinx.serialization.json)

                // Ktor Client (Network) - 3.0.2
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
                implementation("androidx.constraintlayout:constraintlayout-compose:1.1.0")

                // Lifecycle ViewModel (Android only)
                implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")

                // Koin Android
                implementation("io.insert-koin:koin-android:4.0.0")
                implementation("io.insert-koin:koin-androidx-compose:4.0.0")

                // DateTime - explicit for Android to ensure it's included in APK
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.1")

                // Biometric Authentication
                implementation("androidx.biometric:biometric:1.2.0-alpha05")

                // Firebase (for FCM notifications)
                implementation("com.google.firebase:firebase-messaging-ktx:24.1.0")

                // Room Database (Android)
                implementation(libs.room.runtime)
                implementation(libs.room.ktx)

                // Ktor Android Engine
                implementation(libs.ktor.client.okhttp)

                // Activity Compose
                implementation("androidx.activity:activity-compose:1.9.3")
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
    add("kspCommonMainMetadata", libs.room.compiler)
    add("kspAndroid", libs.room.compiler)
    add("kspIosSimulatorArm64", libs.room.compiler)
    add("kspIosX64", libs.room.compiler)
    add("kspIosArm64", libs.room.compiler)
}

android {
    namespace = "com.example.shared"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_19
        targetCompatibility = JavaVersion.VERSION_19
    }

    buildFeatures {
        compose = true
    }
}

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

    listOf("iosX64", "iosArm64", "iosSimulatorArm64").forEach { target ->
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

