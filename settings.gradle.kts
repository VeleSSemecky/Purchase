include(":presentation")
include(":domain")
include(":data")
include(":config")

// Add ability to use a module name as part of a build script name
rootProject.children.forEach { subProject ->
    subProject.buildFileName = "${subProject.name}.gradle.kts"
}
