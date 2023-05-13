object Config {

    object Android {

        const val versionName = "1.0.3"
        const val versionCode = 4
        const val applicationId = "com.veles.kotlinmvvm"

        const val minSdkVersion = 26
        const val targetSdkVersion = 32
        const val compileSdkVersion = 33
    }

    object Versions {

        const val gradlePlugin = "7.1.3"
        const val kotlinLanguage = "1.6.10"
        const val googlePlayServices = "4.3.10"

        // Arch components
        const val acLifecycle = "2.4.0"
        const val acNavigation = "2.3.5"

        // UI
        const val materialComponents = "1.4.0"
        const val constraintLayout = "2.1.2"
        const val swiperefreshLayout = "1.1.0"

        // Firebase
        const val firebaseCrashlyticsGradle = "2.8.1"
        const val firebaseBom = "29.3.1"
        const val firebaseCommon = "20.0.1"
        const val firebaseCrashlytics = "18.2.6"

        // Network
        const val gson = "2.8.9"
        const val loggingInterceptor = "3.1.0"
        const val okHttp = "4.9.2"
        const val retrofit = "2.9.0"

        // Utils
        const val adapterDelegates = "4.3.2"
        const val securityCrypto = "1.0.0"
        const val ktx = "1.5.0"
        const val inject = "1"
        const val dagger = "2.41"
        const val coroutines = "1.6.0"
        const val glide = "4.13.0"
        const val lottie = "5.0.3"
        const val timber = "5.0.1"

        // Development
        const val crashlytics = "2.8.0"
        const val flipper = "0.144.0"
        const val soloader = "0.10.1"
        const val versionsUpdatePlugin = "0.41.0"
    }

    object Libs {

        // UI
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
        const val materialComponents = "com.google.android.material:material:${Versions.materialComponents}"
        const val swiperefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swiperefreshLayout}"

        // Kotlin
        const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlinLanguage}"
        const val ktx = "androidx.core:core-ktx:${Versions.ktx}"

        // Firebase
        const val firebaseBom = "com.google.firebase:firebase-bom:${Versions.firebaseBom}"
        const val firebaseCommon = "com.google.firebase:firebase-common-ktx:${Versions.firebaseCommon}"
        const val firebaseAnalytics = "com.google.firebase:firebase-analytics-ktx"
        const val firebaseCrashlytics = "com.google.firebase:firebase-crashlytics-ktx"

        // Coroutines
        const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

        // Network
        const val gson = "com.google.code.gson:gson:${Versions.gson}"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val retrofitGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"

        // DI
        const val inject = "javax.inject:javax.inject:${Versions.inject}"
        const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
        const val daggerAndroid = "com.google.dagger:dagger-android-support:${Versions.dagger}"
        const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
        const val daggerAndroidProcessor = "com.google.dagger:dagger-android-processor:${Versions.dagger}"

        // Arch components
        const val lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.acLifecycle}"
        const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.acNavigation}"
        const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.acNavigation}"

        // Utils
        const val adapterDelegates = "com.hannesdorfmann:adapterdelegates4:${Versions.adapterDelegates}"
        const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
        const val lottie = "com.airbnb.android:lottie:${Versions.lottie}"
        const val securityCrypto = "androidx.security:security-crypto:${Versions.securityCrypto}"
        const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

        // Development
        const val flipper = "com.facebook.flipper:flipper:${Versions.flipper}"
        const val flipperNetwork = "com.facebook.flipper:flipper-network-plugin:${Versions.flipper}"
        const val soloader = "com.facebook.soloader:soloader:${Versions.soloader}"
    }
}
