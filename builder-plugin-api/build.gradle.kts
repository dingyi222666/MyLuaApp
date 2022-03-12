plugins {
    id("com.android.library")
    id("kotlin-android")
}
/**
 * This module is used to generate the api required for the plugin and is not involved in entering the building
 */


android {
    compileSdk = BuildConfig.Config.Default.compileSdk

    buildToolsVersion = BuildConfig.Config.Default.buildToolsVersion


    defaultConfig {
        minSdk = BuildConfig.Config.Default.minSdk
        targetSdk = BuildConfig.Config.Default.targetSdk

        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {

        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            ndk {
                abiFilters.addAll(arrayOf("armeabi-v7a", "arm64-v8a"))
            }
        }
        debug {
            isMinifyEnabled = false
            ndk {
                abiFilters.addAll(arrayOf("armeabi-v7a", "arm64-v8a"))
            }
        }

    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }



    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
}

dependencies {

    implementation(fileTree("dir" to "libs", "include" to arrayOf("*.jar")))//libs jar

    implementation(project(":common"))
    implementation(project(":luaj"))


    implementation(BuildConfig.Libs.Default.kotlinx_coroutines_android)


    BuildConfig.Libs.Views.sora_editor.forEach {
        api(it) {
            exclude("xml-apis", "xml-apis")
            exclude("xerces","xercesImpl")
        }
    }

    implementation(BuildConfig.Libs.BuildTools.javapoet)


}