import com.android.build.gradle.AppExtension
import com.android.build.gradle.LibraryExtension
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

// apply(from = rootProject.file("repositories.gradle.kts"))

plugins {
//    id("com.github.ben-manes.versions") version "0.45.0"

    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.navigation.safeargs.kotlin) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.jetbrains.kotlin.kapt) apply false
    alias(libs.plugins.jetbrains.kotlin.parcelize) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.jetbrains.kotlin.dokka)
    alias(libs.plugins.google.devtools.ksp) apply false
    alias(libs.plugins.google.gms.google.services) apply false
    alias(libs.plugins.google.firebase.crashlytics) apply false
    alias(libs.plugins.google.firebase.appdistribution) apply false
    alias(libs.plugins.compose.compiler) apply false
//    id("com.android.application") version "7.3.0" apply false
//
//    // Make sure that you have the Google services Gradle plugin dependency
//    id("com.google.gms.google-services") version "4.4.0" apply false
//
//    // Add the dependency for the App Distribution Gradle plugin

//    id("com.android.application") version "7.2.1" apply false
//    id("com.android.library") version "7.2.1" apply false
//    id("org.jetbrains.kotlin.android") version "1.6.20" apply false
//    id("com.google.gms.google-services") version "4.3.10" apply false
//    id("com.google.firebase.appdistribution") version "4.0.0" apply false
}

// buildscript {
//
//    repositories {
//        google()
//    }
//
// //    dependencies {
// //        classpath("com.android.tools.build:gradle:8.2.2")
// //        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.5")
// //        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.21")
// //        classpath("com.google.gms:google-services:4.4.0")
// //        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.9")
// //        classpath("com.google.firebase:firebase-appdistribution-gradle:4.0.1")
// //    }
// }

// afterEvaluate {
//    tasks.withType<DokkaTask>().configureEach {
//        outputDirectory.set(file("${project.rootDir}/docs"))
// //        outputDirectory.set(file(project.layout.buildDirectory.dir("docs")))
//    }
// }

tasks.dokkaGfmMultiModule {
    outputDirectory.set(file("${project.rootDir}/docs"))
}

subprojects {
    project.plugins.configure(project)

    apply(plugin = "org.jetbrains.dokka")

    buildscript {
        apply(from = rootProject.file("repositories.gradle.kts"))
    }

//    tasks.withType<DokkaTaskPartial>().configureEach {
//        outputDirectory.set(file("${project.rootDir}/docs"))
//    }
//
//    tasks.withType<DokkaMultiModuleTask>().configureEach {
//        outputDirectory.set(file("${project.rootDir}/docs"))
//    }

//    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
//        kotlinOptions {
//            jvmTarget = JavaVersion.VERSION_19.toString()
//        }
//    }
    tasks.withType<KotlinJvmCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_19)
        }
    }
}

fun PluginContainer.configure(project: Project) {
    whenPluginAdded {
        when (this) {
            is com.android.build.gradle.AppPlugin ->
                project.extensions
                    .getByType<AppExtension>()
                    .applyCommons()

            is com.android.build.gradle.LibraryPlugin ->
                project.extensions
                    .getByType<LibraryExtension>()
                    .applyCommons()
        }
    }
}

fun AppExtension.applyCommons() {
    compileSdkVersion(libs.versions.compileSdk.get().toInt())

    defaultConfig.apply {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()
    }

    compileOptions.apply {
        sourceCompatibility = JavaVersion.VERSION_19
        targetCompatibility = JavaVersion.VERSION_19
    }
}

fun LibraryExtension.applyCommons() {
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig.apply {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()

        consumerProguardFiles("proguard-rules.pro")
    }

    compileOptions.apply {
        sourceCompatibility = JavaVersion.VERSION_19
        targetCompatibility = JavaVersion.VERSION_19
    }
}
