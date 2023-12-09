plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp")
    kotlin("android")
}

android {
    namespace = "com.veles.purchase.data"

    lint {
        abortOnError = false
    }

    defaultConfig {
        buildConfigField("int", "VERSION_CODE", "${Config.Android.versionCode}")
        buildConfigField("String", "VERSION_NAME", "\"${Config.Android.versionName}\"")
    }

    flavorDimensions.add("default")
    productFlavors {
        create("configProduction") {}
        create("configTest") {}
    }
}

dependencies {
    implementation(project(":domain"))
    api(project(":config"))
    implementation("javax.inject:javax.inject:1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.google.code.gson:gson:2.9.0")
    implementation("com.facebook.flipper:flipper:0.162.0")
    implementation("com.facebook.flipper:flipper-network-plugin:0.162.0")
    implementation("com.jakewharton.timber:timber:5.0.1")

    // Room
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation("com.google.firebase:firebase-database-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-messaging-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-storage-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")

    implementation("com.google.android.gms:play-services-auth:20.7.0")
    implementation("com.firebaseui:firebase-ui-auth:8.0.1")
    implementation("com.firebaseui:firebase-ui-storage:8.0.1")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.1")

    implementation("androidx.datastore:datastore-preferences:1.0.0")
}
