rootProject.name = "Dynamicyouthkmp"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        mavenLocal()
        if (System.getenv("USE_DOMESTIC_MIRROR") == "true") {
            maven("https://maven.aliyun.com/repository/public/")
            maven("https://maven.aliyun.com/repository/google/")
            maven("https://maven.aliyun.com/repository/gradle-plugin")
        }
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenLocal()
        if (System.getenv("USE_DOMESTIC_MIRROR") == "true") {
            maven("https://maven.aliyun.com/repository/public/")
            maven("https://maven.aliyun.com/repository/google/")
        }
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

include(":composeApp")

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}