pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Purchase"
//include(":shared")
//include(":androidApp")
//include(":iosApp")
include(":config")
include(":data")
include(":domain")
include(":presentation")

// Add ability to use a module name as part of a build script name
rootProject.children.forEach { subProject ->
    subProject.buildFileName = "${subProject.name}.gradle.kts"
}
