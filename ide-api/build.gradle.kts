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
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    api(fileTree("dir" to "libs", "include" to arrayOf("*.jar")))//libs jar

    implementation(BuildConfig.Libs.Google.material)

    implementation(BuildConfig.Libs.Default.kotlinx_coroutines_android)
    implementation(project(":common"))
    implementation(BuildConfig.Libs.AndroidX.appcompat)
    implementation(BuildConfig.Libs.Tools.mmkv)
    BuildConfig.Libs.Views.sora_editor.forEach {
        implementation(it) {
            exclude("xml-apis", "xml-apis")
            exclude("xerces", "xercesImpl")
        }
    }
}