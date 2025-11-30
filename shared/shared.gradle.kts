plugins {
    alias(libs.plugins.android.library)
    kotlin("multiplatform")
    alias(libs.plugins.jetbrains.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "19"
            }
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
                implementation(compose.foundation)
                implementation(compose.animation)
                implementation(libs.coroutines.core)

                implementation(libs.room.runtime)
                implementation(libs.sqlite.bundled)

                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.7.1")

                // Koin for DI
                implementation("io.insert-koin:koin-core:4.0.0")
                implementation("io.insert-koin:koin-compose:4.0.0")
                implementation("io.insert-koin:koin-compose-viewmodel:4.0.0")

                // Navigation
                implementation("org.jetbrains.androidx.navigation:navigation-compose:2.8.0-alpha10")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")

                // ConstraintLayout Compose
                implementation("androidx.constraintlayout:constraintlayout-compose:1.1.0")

                // Lifecycle ViewModel
                implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")
            }
        }

        val androidMain by getting {
            dependencies {
                // Compose
                implementation(libs.compose.ui.tooling.preview)
                implementation(libs.androidx.activity)

                // Koin Android
                implementation("io.insert-koin:koin-android:4.0.0")
                implementation("io.insert-koin:koin-androidx-compose:4.0.0")

                // DateTime - explicit for Android to ensure it's included in APK
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.7.1")

                // Biometric Authentication
                implementation("androidx.biometric:biometric:1.2.0-alpha05")

                // Firebase (for FCM notifications)
                implementation("com.google.firebase:firebase-messaging-ktx:24.1.0")

                // Room Database (Android)
                implementation(libs.room.runtime)
                implementation(libs.room.ktx)

                // Activity Compose
                implementation("androidx.activity:activity-compose:1.9.3")
            }
        }

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting

        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)

            dependencies {
                // iOS specific dependencies
            }
        }
    }
}

dependencies {
    // Тимчасово відключаємо KSP для Room KMP
    // add("kspCommonMainMetadata", libs.room.compiler)
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
