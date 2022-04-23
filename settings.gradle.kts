pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.aliyun.com/repository/google")
        google()
        mavenCentral()
        maven("https://maven.aliyun.com/repository/public")
        maven("https://maven.aliyun.com/repository/gradle-plugin")
        maven("https://jitpack.io")
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven("https://maven.aliyun.com/repository/google")
        google()
        mavenCentral()
        maven("https://maven.aliyun.com/repository/public")
        maven("https://maven.aliyun.com/repository/gradle-plugin")
        maven("https://jitpack.io")
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
        include(":$name")

        project(":$name").projectDir = it

    }

rootProject.name = "MyLuaApp"

