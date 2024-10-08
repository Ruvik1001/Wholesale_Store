pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "Wholesale Store"
include(":app")
include(":core")
include(":data")
include(":domain")
include(":features")
include(":features:sign-in")
include(":features:sign-up")
include(":features:forgot_password")
include(":features:profile")
include(":features:catalog")
include(":features:basket")
