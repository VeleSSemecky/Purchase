
apply(from = rootProject.file("repositories.gradle.kts"))

plugins {
    id("com.github.ben-manes.versions") version "0.45.0"
}

buildscript {

    repositories {
        google()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:8.0.1")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.6.0-beta01")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.20")
        classpath("com.google.gms:google-services:4.3.15")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.5")
    }
}

subprojects {
    project.plugins.configure(project)

    buildscript {
        apply(from = rootProject.file("repositories.gradle.kts"))
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_17.toString()
        }
    }
}

fun PluginContainer.configure(project: Project) {
    whenPluginAdded {
        when (this) {
            is com.android.build.gradle.AppPlugin -> project.extensions.getByType<com.android.build.gradle.AppExtension>()
                .applyCommons()
            is com.android.build.gradle.LibraryPlugin -> project.extensions.getByType<com.android.build.gradle.LibraryExtension>()
                .applyCommons()
        }
    }
}

fun com.android.build.gradle.AppExtension.applyCommons() {
    compileSdkVersion(33)

    defaultConfig.apply {
        minSdk = Config.Android.minSdkVersion
        targetSdk = 33
        versionCode = Config.Android.versionCode
        versionName = Config.Android.versionName
    }

    compileOptions.apply {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

fun com.android.build.gradle.LibraryExtension.applyCommons() {
    compileSdk = 33

    defaultConfig.apply {
        minSdk = Config.Android.minSdkVersion
        targetSdk = 33
        versionCode = Config.Android.versionCode
        versionName = Config.Android.versionName

        consumerProguardFiles("proguard-rules.pro")
    }

    compileOptions.apply {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
