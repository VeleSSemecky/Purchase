plugins {
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

dependencies {
    // DI
    implementation(libs.inject)
    // Coroutines
    implementation(libs.coroutines.core)
}
