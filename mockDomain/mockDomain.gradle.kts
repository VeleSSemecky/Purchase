plugins {
    kotlin("multiplatform")
}

kotlin {
    // Enable default hierarchy template for proper iOS support
    applyDefaultHierarchyTemplate()

    // JVM target for use in Android and other JVM environments
    jvm {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_19)
        }
    }

    // iOS targets for multiplatform
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "mockDomain"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            // Coroutines
            implementation(libs.coroutines.core)
        }
    }
}

