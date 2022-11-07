plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.dingyi.myluaapp.ide.ui.android"

    compileSdk = BuildConfig.Config.Default.compileSdk
    buildToolsVersion = BuildConfig.Config.Default.buildToolsVersion

    defaultConfig {

        minSdk = BuildConfig.Config.Default.minSdk
        targetSdk = BuildConfig.Config.Default.targetSdk

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
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
}