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


    compileOnly("org.eclipse.jdt:ecj:3.26.0")
    compileOnly(BuildConfig.Libs.BuildTools.kotlin_compiler)

    compileOnly(files("../builder/libs/javax-tools.jar"))

    compileOnly(BuildConfig.Libs.Google.material)
    compileOnly(BuildConfig.Libs.Tools.androlua_standalone)

    implementation(BuildConfig.Libs.Default.kotlinx_coroutines_android)
    compileOnly(project(":common"))

    compileOnly(BuildConfig.Libs.AndroidX.appcompat)
    compileOnly(BuildConfig.Libs.Tools.mmkv)
    BuildConfig.Libs.Views.sora_editor.forEach {
        compileOnly(it) {
            exclude("xml-apis", "xml-apis")
            exclude("xerces", "xercesImpl")
        }
    }
}