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

        consumerProguardFiles ("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles (getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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

    implementation(project(":luaj"))

    implementation(BuildConfig.Libs.AndroidX.appcompat)
    implementation(BuildConfig.Libs.AndroidX.core_kotlinx)
    implementation(BuildConfig.Libs.Google.material)
    implementation(BuildConfig.Libs.Tools.litepal)
    // OkHttp 框架：https://github.com/square/okhttp
    // noinspection GradleDependency
    implementation(BuildConfig.Libs.Network.okhttp3)
    // 网络请求框架：https://github.com/getActivity/EasyHttp
    implementation(BuildConfig.Libs.Network.easy_http)
    implementation(BuildConfig.Libs.Default.kotlin_stdlib) //kt
    implementation(BuildConfig.Libs.Default.kotlinx_coroutines_android)
    //glide
    implementation(BuildConfig.Libs.Default.glide)
    annotationProcessor(BuildConfig.Libs.Default.glide_compiler)
    implementation(BuildConfig.Libs.Google.gson)

}