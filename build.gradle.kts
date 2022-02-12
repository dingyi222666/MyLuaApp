// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        maven("https://maven.aliyun.com/repository/google")
        //google()
        //mavenCentral()
        maven("https://maven.aliyun.com/repository/public")
        maven("https://maven.aliyun.com/repository/gradle-plugin")
        maven("https://jitpack.io")
        //maven("https://dl.bintray.com/pixplicity/android")


    }

    dependencies {
        classpath(BuildConfig.Libs.Plugin.android_gradle_plugin)
        classpath(BuildConfig.Libs.Plugin.antlr_kotlin_gradle_plugin)
        classpath(BuildConfig.Libs.Plugin.kotlin_gradle_plugin)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        maven("https://maven.aliyun.com/repository/google")
        //google()
        //mavenCentral()
        maven("https://maven.aliyun.com/repository/public")
        maven("https://maven.aliyun.com/repository/gradle-plugin")
        maven("https://jitpack.io")
        //maven("https://dl.bintray.com/pixplicity/android")
    }
}


tasks.register("clean", Delete::class.java) {
    this.delete(rootProject.buildDir)
}
