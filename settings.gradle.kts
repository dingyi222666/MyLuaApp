@file:Suppress("UnstableApiUsage")

//enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        //maven("https://maven.aliyun.com/repository/public")
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.aliyun.com/repository/gradle-plugin")
        maven("https://www.jetbrains.com/intellij-repository/releases")

        maven("https://jitpack.io")
        maven("https://packages.jetbrains.team/maven/p/ij/intellij-dependencies")
    }
}


dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        //maven("https://maven.aliyun.com/repository/public")
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.aliyun.com/repository/gradle-plugin")
        maven("https://www.jetbrains.com/intellij-repository/releases")
        maven("https://jitpack.io")
        maven("https://packages.jetbrains.team/maven/p/ij/intellij-dependencies")
    }

    versionCatalogs {
        create("libs") {
            library(
                "annotation.androidBuildTools",
                "com.android.tools",
                "annotations"
            ).version("26.0.0")
        }
    }
}


include(
    ":app",
    ":editor",
    ":builder",
    ":core",
    ":plugin",
    ":treeview",
    ":ide-api",
    ":language-server:lua",
    ":language-server:java"
)

include(":build-tools:build-api")

//foreach platform
file("platform").listFiles()
    ?.forEach {
        val name = it.name
        include(name)
        project(":$name").projectDir = it

    }

rootProject.name = "MyLuaApp"

