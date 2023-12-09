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
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.10")
}
