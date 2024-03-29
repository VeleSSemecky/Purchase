
apply(from = rootProject.file("repositories.gradle.kts"))

plugins {
    id("com.github.ben-manes.versions") version "0.45.0"
    id("com.google.devtools.ksp") version "1.9.10-1.0.13" apply false
}

buildscript {

    repositories {
        google()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:8.1.4")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.5")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.21")
        classpath("com.google.gms:google-services:4.4.0")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.9")
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
    compileSdkVersion(34)

    defaultConfig.apply {
        minSdk = Config.Android.minSdkVersion
        targetSdk = 34
        versionCode = Config.Android.versionCode
        versionName = Config.Android.versionName
    }

    compileOptions.apply {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

fun com.android.build.gradle.LibraryExtension.applyCommons() {
    compileSdk = 34

    defaultConfig.apply {
        minSdk = Config.Android.minSdkVersion
        targetSdk = 34
        versionCode = Config.Android.versionCode
        versionName = Config.Android.versionName

        consumerProguardFiles("proguard-rules.pro")
    }

    compileOptions.apply {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
