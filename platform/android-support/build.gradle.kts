plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.dingyi.myluaapp.ide.ui.android"

    compileSdk = BuildConfig.Config.Default.compileSdk
    buildToolsVersion = BuildConfig.Config.Default.buildToolsVersion

    defaultConfig {
        applicationId = "com.dingyi.myluaapp.ide.ui.android"
        minSdk = BuildConfig.Config.Default.minSdk
        targetSdk = BuildConfig.Config.Default.targetSdk

        versionCode = BuildConfig.Config.App.versionCode
        versionName = BuildConfig.Config.App.versionName
        multiDexEnabled = true
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
    implementation(BuildConfig.Libs.AndroidX.swiperefreshlayout)
    implementation(BuildConfig.Libs.AndroidX.appcompat)
    implementation(BuildConfig.Libs.AndroidX.lifecycle_viewmodel)
    implementation(BuildConfig.Libs.AndroidX.lifecycle_livedata)
    implementation(BuildConfig.Libs.AndroidX.lifecycle_runtime)
    implementation(BuildConfig.Libs.AndroidX.constraint_layout)
    implementation(BuildConfig.Libs.AndroidX.preference_ktx)
    implementation(BuildConfig.Libs.Google.material)
    implementation(project(":platform-api"))
}