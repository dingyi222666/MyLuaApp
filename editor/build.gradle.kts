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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(
        fileTree(
            "dir" to "libs",
            "include" to listOf("*.jar", "*.aar")
        )
    )//libs jar

    //editor
    implementation(BuildConfig.Libs.AndroidX.appcompat)
    implementation(BuildConfig.Libs.AndroidX.lifecycle_runtime)
    implementation(BuildConfig.Libs.Google.gson)
    implementation(project(":ide-api"))
    implementation(project(":platform-common"))

    implementation(BuildConfig.Libs.Tools.lsp4j)
    BuildConfig.Libs.Views.sora_editor.forEach {
        implementation(it) {
            exclude("xml-apis","xml-apis")
            exclude("xerces","xercesImpl")
        }
    }

}