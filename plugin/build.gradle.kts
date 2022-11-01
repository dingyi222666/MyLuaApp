plugins {
    id("com.android.library")
    id("kotlin-android")
}

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
                abiFilters.addAll(arrayOf("armeabi-v7a", "x86", "arm64-v8a"))
            }
        }
        debug {
            isMinifyEnabled = false
            ndk {
                abiFilters.addAll(arrayOf("armeabi-v7a", "x86", "arm64-v8a"))
            }
        }

    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }



    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
}

dependencies {

    implementation(fileTree("dir" to "libs", "include" to arrayOf("*.jar")))//libs jar

    implementation(project(":platform-common"))

    implementation(project(":core"))

    implementation(project(":editor"))

    implementation(project(":builder"))

    implementation(project(":language-server:lua"))

    implementation(project(":language-server:java"))



    BuildConfig.Libs.Views.sora_editor.forEach {
        implementation(it) {
            exclude("xerces","xercesImpl")
            exclude("xml-apis", "xml-apis")
        }
    }

    implementation(project(":ide-api"))

    implementation(BuildConfig.Libs.Tools.mmkv)

    implementation(BuildConfig.Libs.Tools.zip4j)
    compileOnly(BuildConfig.Libs.Tools.lsp4j)
    implementation(BuildConfig.Libs.Google.gson)

    implementation(BuildConfig.Libs.Tools.androlua_standalone)

    implementation(BuildConfig.Libs.Default.kotlinx_coroutines_android)

    implementation(BuildConfig.Libs.AndroidX.appcompat)

    implementation(BuildConfig.Libs.Google.material)


}