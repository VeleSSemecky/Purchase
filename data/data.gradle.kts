plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.parcelize)
    alias(libs.plugins.google.devtools.ksp)
}

android {
    namespace = "com.veles.purchase.data"

    flavorDimensions.add("default")
    productFlavors {
        create("configProduction") {}
        create("configTest") {}
    }
}

dependencies {
    implementation(project(":domain"))
    api(project(":config"))

    // DI
    implementation(libs.inject)

    // Coroutines
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.play.services)

    // Network
    implementation(libs.retrofit)
    implementation(libs.gson)
    implementation(libs.flipper)
    implementation(libs.flipper.network.plugin)

    // Logger
    implementation(libs.timber)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase.data)
    implementation(libs.firebase.ui.auth)
    implementation(libs.firebase.ui.storage)

    // Utils
    implementation(libs.play.services.auth)

    // DataStore
    implementation(libs.datastore.preferences)

    // Credentials
    implementation(libs.credentials)
    implementation(libs.credentials.play.services.auth)
    implementation(libs.identity)
}
