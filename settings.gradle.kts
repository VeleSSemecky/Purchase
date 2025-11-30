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
include(":shared")
include(":androidApp")  // ✅ Enabled for Phase 2.4 testing
//include(":iosApp")
include(":config")

// Old modules - disabled during KMP migration (Phase 2)
// These will be replaced by shared + mockDomain
// Will re-enable in Phase 4-6 for gradual migration
//include(":data")        // Will migrate to KMP in Phase 4
//include(":domain")      // Will migrate to KMP in Phase 5
//include(":presentation") // Being migrated to shared in Phase 2

// New KMP modules
include(":mockDomain")  // Phase 1 - Mock data for UI development

// Add ability to use a module name as part of a build script name
rootProject.children.forEach { subProject ->
    subProject.buildFileName = "${subProject.name}.gradle.kts"
}
