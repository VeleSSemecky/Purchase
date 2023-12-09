import java.util.Locale

plugins {
    id("com.android.application")
    kotlin("android")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.veles.purchase.presentation"

    defaultConfig {
        applicationId = Config.Android.applicationId
    }

    viewBinding.isEnabled = true

    buildFeatures {
        viewBinding = true
        dataBinding = false
    }

    androidComponents{
        // This is a workaround for https://issuetracker.google.com/301245705 which depends on internal
        // implementations of the android gradle plugin and the ksp gradle plugin which might change in the future
        // in an unpredictable way.
        onVariants(selector().all()) { variant ->
            afterEvaluate {
                val variantName = variant.name.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }
                val ksp = "ksp${variantName}Kotlin"
                val viewBinding = "dataBindingGenBaseClasses$variantName"
                val buildConfig = "generate${variantName}BuildConfig"
                val safeArgs = "generateSafeArgs$variantName"
                val aidl = "compile${variantName}Aidl"

                val kspTask = project.tasks.findByName(ksp)
                        as? org.jetbrains.kotlin.gradle.tasks.AbstractKotlinCompileTool<*>
                val viewBindingTask = project.tasks.findByName(viewBinding)
                        as? com.android.build.gradle.internal.tasks.databinding.DataBindingGenBaseClassesTask
                val buildConfigTask = project.tasks.findByName(buildConfig)
                        as? com.android.build.gradle.tasks.GenerateBuildConfig
                val aidlTask = project.tasks.findByName(aidl)
                        as? com.android.build.gradle.tasks.AidlCompile
                val safeArgsTask = project.tasks.findByName(safeArgs)
                        as? androidx.navigation.safeargs.gradle.ArgumentsGenerationTask

                kspTask?.run {
                    viewBindingTask?.let { setSource(it.sourceOutFolder) }
                    buildConfigTask?.let { setSource(it.sourceOutputDir) }
                    aidlTask?.let { setSource(it.sourceOutputDir) }
                    safeArgsTask?.let { setSource(it.outputDir) }
                }
            }
        }
    }

    signingConfigs {
        getByName("debug") {
            storeFile = file(Secret.SignIn.debugKeyStoreFile)
            storePassword = Secret.SignIn.debugKeyStorePassword
            keyAlias = Secret.SignIn.debugKeyAlias
            keyPassword = Secret.SignIn.debugKeyPassword
        }

        create("release") {
            storeFile = file(Secret.SignIn.releaseKeyStoreFile)
            storePassword = Secret.SignIn.releaseKeyStorePassword
            keyAlias = Secret.SignIn.releaseKeyAlias
            keyPassword = Secret.SignIn.releaseKeyPassword
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            signingConfig = signingConfigs.getByName("debug")
            isDebuggable = true
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    flavorDimensions.add("default")
    productFlavors {
        create("configProduction") {
            applicationIdSuffix = ".configProduction"
        }
        create("configTest") {
            applicationIdSuffix = ".configTest"
        }
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes.addAll(
                listOf(
                    *packagingOptions.resources.excludes.toTypedArray(),
                    "META-INF/DEPENDENCIES",
                    "META-INF/NOTICE",
                    "META-INF/LICENSE",
                    "META-INF/LICENSE.txt",
                    "META-INF/NOTICE.txt"
                )
            )
        }
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.6"
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":config"))

    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.appcompat:appcompat:1.7.0-alpha03")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.5")

    // JetPack
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    implementation("androidx.activity:activity-ktx:1.8.1")

    // Google
    implementation("com.google.code.gson:gson:2.9.0")

    // Dagger 2
    implementation("com.google.dagger:dagger:2.49")
    implementation("com.google.dagger:dagger-android:2.49")
    implementation("com.google.dagger:dagger-android-support:2.49")
    ksp("com.google.dagger:dagger-compiler:2.49")
    ksp("com.google.dagger:dagger-android-processor:2.49")

    // Biometric
    implementation("androidx.biometric:biometric-ktx:1.2.0-alpha05")

    // Internet
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.2")

    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("androidx.core:core-ktx:1.12.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.1")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-messaging-ktx")
    implementation("com.google.firebase:firebase-storage-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")

    implementation("com.google.android.gms:play-services-auth:20.7.0")
    implementation("com.firebaseui:firebase-ui-storage:8.0.1")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.13.1")
    ksp("com.github.bumptech.glide:compiler:4.13.1")
    implementation("com.github.skydoves:landscapist-glide:1.5.1")

    // Room
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")

    // Compose
    implementation("androidx.compose.runtime:runtime:1.6.0-beta02")
    implementation("androidx.compose.ui:ui:1.6.0-beta02")
    implementation("androidx.compose.material:material:1.6.0-beta02")
    implementation("androidx.compose.foundation:foundation:1.6.0-beta02")
    implementation("androidx.compose.animation:animation:1.6.0-beta02")
    implementation("androidx.compose.ui:ui-tooling:1.6.0-beta02")
    implementation("androidx.compose.ui:ui-tooling-preview:1.6.0-beta02")
    implementation("androidx.compose.ui:ui-viewbinding:1.6.0-beta02")
    implementation("androidx.activity:activity-compose:1.8.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    // Accompanist
    implementation("com.google.accompanist:accompanist-pager:0.16.1")
    implementation("com.google.accompanist:accompanist-flowlayout:0.24.6-alpha")

    // CameraX core library
    implementation("androidx.camera:camera-core:1.3.0")
    implementation("androidx.camera:camera-camera2:1.3.0")
    implementation("androidx.camera:camera-lifecycle:1.3.0")
    implementation("androidx.camera:camera-view:1.3.0")
    implementation("androidx.camera:camera-video:1.3.0")

    implementation("androidx.concurrent:concurrent-futures-ktx:1.1.0")

    implementation("com.google.android.play:app-update-ktx:2.1.0")
    implementation("com.google.android.play:integrity:1.3.0")

    implementation("androidx.datastore:datastore-preferences:1.0.0")
}
