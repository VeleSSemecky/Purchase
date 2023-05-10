plugins {
    id("com.android.application")
    kotlin("android")
    id("org.jetbrains.kotlin.kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.veles.purchase.presentation"

    defaultConfig {
        applicationId = Config.Android.applicationId
    }

    viewBinding.isEnabled = true

    @Suppress("UnstableApiUsage")
    buildFeatures {
        viewBinding = true
        dataBinding = false
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
        @Suppress("UnstableApiUsage")
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        @Suppress("UnstableApiUsage")
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

    @Suppress("UnstableApiUsage")
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
        freeCompilerArgs =
            listOf(*kotlinOptions.freeCompilerArgs.toTypedArray(), "-Xjvm-default=all")
    }

    @Suppress("UnstableApiUsage")
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
        kotlinCompilerExtensionVersion = "1.4.6"
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":config"))

    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.appcompat:appcompat:1.7.0-alpha02")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.6.0-beta01")
    implementation("androidx.navigation:navigation-ui-ktx:2.6.0-beta01")

    // JetPack
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.fragment:fragment-ktx:1.5.7")
    implementation("androidx.activity:activity-ktx:1.7.1")

    // Google
    implementation("com.google.code.gson:gson:2.9.0")

    // Dagger 2
    implementation("com.google.dagger:dagger:2.42")
    implementation("com.google.dagger:dagger-android:2.42")
    implementation("com.google.dagger:dagger-android-support:2.42")
    kapt("com.google.dagger:dagger-compiler:2.42")
    kapt("com.google.dagger:dagger-android-processor:2.42")

    // Biometric
    implementation("androidx.biometric:biometric-ktx:1.2.0-alpha05")

    // Internet
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.2")

    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("androidx.core:core-ktx:1.10.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.4")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.0.0"))
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-messaging-ktx")
    implementation("com.google.firebase:firebase-storage-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")

    implementation("com.google.android.gms:play-services-auth:20.5.0")
    implementation("com.firebaseui:firebase-ui-storage:8.0.1")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.13.1")
    kapt("com.github.bumptech.glide:compiler:4.13.1")
    implementation("com.github.skydoves:landscapist-glide:1.5.1")

    // Room
    implementation("androidx.room:room-runtime:2.5.1")
    implementation("androidx.room:room-ktx:2.5.1")
    kapt("androidx.room:room-compiler:2.5.1")

    // Compose
    implementation("androidx.compose.runtime:runtime:1.5.0-alpha03")
    implementation("androidx.compose.ui:ui:1.5.0-alpha03")
    implementation("androidx.compose.material:material:1.5.0-alpha03")
    implementation("androidx.compose.foundation:foundation:1.5.0-alpha03")
    implementation("androidx.compose.animation:animation:1.5.0-alpha03")
    implementation("androidx.compose.ui:ui-tooling:1.5.0-alpha03")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.0-alpha03")
    implementation("androidx.compose.ui:ui-viewbinding:1.5.0-alpha03")
    implementation("androidx.activity:activity-compose:1.7.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    // Accompanist
    implementation("com.google.accompanist:accompanist-pager:0.16.1")
    implementation("com.google.accompanist:accompanist-flowlayout:0.24.6-alpha")

    // CameraX core library
    implementation("androidx.camera:camera-core:1.2.2")
    implementation("androidx.camera:camera-camera2:1.2.2")
    implementation("androidx.camera:camera-lifecycle:1.2.2")
    implementation("androidx.camera:camera-view:1.2.2")
    implementation("androidx.camera:camera-video:1.2.2")

    implementation("androidx.concurrent:concurrent-futures-ktx:1.1.0")

    implementation("com.google.android.play:app-update-ktx:2.0.1")
    implementation("com.google.android.play:integrity:1.1.0")

    implementation("androidx.datastore:datastore-preferences:1.0.0")
}
