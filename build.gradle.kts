// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath(BuildConfig.Libs.Plugin.antlr_kotlin_gradle_plugin)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

plugins {
    id("com.android.application") version (BuildConfig.Versions.android_gradle_plugin_version) apply (false)
    id("com.android.library") version (BuildConfig.Versions.android_gradle_plugin_version) apply (false)
    id("org.jetbrains.kotlin.android") version (BuildConfig.Versions.kotlin_version) apply (false)
    id("org.jetbrains.kotlin.kapt") version (BuildConfig.Versions.kotlin_version) apply (false)
    id("org.jetbrains.kotlin.jvm") version (BuildConfig.Versions.kotlin_version) apply false
}

tasks {
    create<Delete>("clean") {
        delete(rootProject.buildDir)
    }
}



