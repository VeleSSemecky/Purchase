plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    namespace = "com.veles.purchase.config"

    defaultConfig {
        buildConfigField(
            "String",
            "MESSAGE_AUTHENTICATOR_NAME",
            "\"${Secret.Server.messageAuthenticatorName}\""
        )
        buildConfigField(
            "String",
            "MESSAGE_AUTHENTICATOR_VALUE",
            "\"${Secret.Server.messageAuthenticatorValue}\""
        )
        buildConfigField(
            "String",
            "MESSAGE_API",
            "\"${Secret.Server.messageApi}\""
        )
        buildConfigField(
            "String",
            "SERVER_CLIENT_ID",
            "\"${Secret.Server.serverClientId}\""
        )
    }

    flavorDimensions.add("default")
    productFlavors {
        create("configProduction") {
            buildConfigField("String", "DB_KEY", "\"${Secret.Server.prodDbKey}\"")
        }

        create("configTest") {
            buildConfigField("String", "DB_KEY", "\"${Secret.Server.devDbKey}\"")
        }
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:2.0.0")
}
