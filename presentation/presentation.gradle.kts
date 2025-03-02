import java.util.Locale

    plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.parcelize)
    alias(libs.plugins.navigation.safeargs.kotlin)
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.google.firebase.crashlytics)
    alias(libs.plugins.google.firebase.appdistribution)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.veles.purchase.presentation"

    defaultConfig {
        applicationId = libs.versions.applicationId.get()
    }

    viewBinding.isEnabled = true

    buildFeatures {
        viewBinding = true
        dataBinding = false
    }

    androidComponents {
        // This is a workaround for https://issuetracker.google.com/301245705 which depends on internal
        // implementations of the android gradle plugin and the ksp gradle plugin which might change in the future
        // in an unpredictable way.
        onVariants(selector().all()) { variant ->
            afterEvaluate {
                val variantName =
                    variant.name.replaceFirstChar {
                        if (it.isLowerCase()) {
                            it.titlecase(
                                Locale.getDefault(),
                            )
                        } else {
                            it.toString()
                        }
                    }
                val ksp = "ksp${variantName}Kotlin"
                val viewBinding = "dataBindingGenBaseClasses$variantName"
                val buildConfig = "generate${variantName}BuildConfig"
                val safeArgs = "generateSafeArgs$variantName"
                val aidl = "compile${variantName}Aidl"

                val kspTask =
                    project.tasks.findByName(ksp)
                        as? org.jetbrains.kotlin.gradle.tasks.AbstractKotlinCompileTool<*>
                val viewBindingTask =
                    project.tasks.findByName(viewBinding)
                        as? com.android.build.gradle.internal.tasks.databinding.DataBindingGenBaseClassesTask
                val buildConfigTask =
                    project.tasks.findByName(buildConfig)
                        as? com.android.build.gradle.tasks.GenerateBuildConfig
                val aidlTask =
                    project.tasks.findByName(aidl)
                        as? com.android.build.gradle.tasks.AidlCompile
                val safeArgsTask =
                    project.tasks.findByName(safeArgs)
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
                "proguard-rules.pro",
            )
        }

        debug {
            signingConfig = signingConfigs.getByName("debug")
            isDebuggable = true
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
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
        jvmTarget = JavaVersion.VERSION_19.toString()
    }

    buildFeatures {
        compose = true
        buildConfig = true
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
                    "META-INF/NOTICE.txt",
                ),
            )
        }
    }

//    composeOptions {
//        kotlinCompilerExtensionVersion = "1.5.14"
//    }
}

composeCompiler {
//    enableStrongSkippingMode = true

//    reportsDestination = layout.buildDirectory.dir("compose_compiler")
//    stabilityConfigurationFile = rootProject.layout.projectDirectory.file("stability_config.conf")
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":config"))

    // AndroidCore
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.lifecycle.extensions)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.savedstate)

    // UI
    implementation(libs.constraint.layout)
    implementation(libs.app.compat)
    implementation(libs.material.components)

    // Navigation
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    // DI
    implementation(libs.dagger)
    implementation(libs.dagger.android)
    ksp(libs.dagger.compiler)
    ksp(libs.dagger.android.processor)

    // Network
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.gson)
    implementation(libs.logging.interceptor)

    // Coroutines
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)
    implementation(libs.coroutines.play.services)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase.presentation)
    implementation(libs.firebase.ui.storage)

    // Image
    implementation(libs.glide)
    implementation(libs.glide.okhttp3)
    ksp(libs.glide.compiler)
    implementation(libs.glide.landscapist)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.compose.activity)
    implementation(libs.compose.fragment)
    implementation(libs.compose.lifecycle.viewmodel)
    implementation(libs.compose.constraintlayout)
    implementation(libs.compose.accompanist.pager)
    implementation(libs.compose.accompanist.flowlayout)

    // CameraX
    implementation(libs.camera.core)
    implementation(libs.camera.camera2)
    implementation(libs.camera.lifecycle)
    implementation(libs.camera.view)
    implementation(libs.camera.video)

    // Credentials
    implementation(libs.credentials)
    implementation(libs.credentials.play.services.auth)
    implementation(libs.identity)

    // DataStore
    implementation(libs.datastore.preferences)

    // Utils
    implementation(libs.security.crypto)
    implementation(libs.play.services.auth)
    implementation(libs.play.services.location)
    implementation(libs.biometric)
    implementation(libs.play.app.update)
    implementation(libs.play.integrity)
    implementation(libs.concurrent.futures)

    // Test
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.core.testing )
}
