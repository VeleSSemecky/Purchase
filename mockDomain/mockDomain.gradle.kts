plugins {
    kotlin("multiplatform")
}

kotlin {
    // JVM target for use in Android and other JVM environments
    jvm {
        compilations.all {
            kotlinOptions {
                jvmTarget = "19"
            }
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
        val commonMain by getting {
            dependencies {
                // Coroutines
                implementation(libs.coroutines.core)
                // DateTime для мок даних - must match version in shared module
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.7.1")
            }
        }

        val jvmMain by getting {
            dependencies {
                // JVM/Android specific if needed
            }
        }

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting

        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)

            dependencies {
                // iOS specific dependencies
            }
        }
    }
}

