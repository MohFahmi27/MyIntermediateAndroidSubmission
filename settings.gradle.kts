@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url =  uri("https://jitpack.io") }
    }
}
rootProject.name = "StoryApp"
include(":app")
include(":features:auth")
include(":core")
include(":features:home")
include(":features:detail")
include(":features:map-story")
include(":features:add-story")

