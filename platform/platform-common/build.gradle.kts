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

    buildFeatures {
        viewBinding = true
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

    api(fileTree("dir" to "libs", "include" to "*.jar"))

    implementation(BuildConfig.Libs.AndroidX.appcompat)
    implementation(BuildConfig.Libs.AndroidX.core_kotlinx)
    implementation(BuildConfig.Libs.Google.material)
    implementation(project(":core"))

    // OkHttp 框架：https://github.com/square/okhttp
    // noinspection GradleDependency
    implementation(BuildConfig.Libs.Network.net)
    implementation(BuildConfig.Libs.Network.okhttp3)
    // 网络请求框架：https://github.com/getActivity/EasyHttp

    implementation(BuildConfig.Libs.Default.kotlin_stdlib) //kt
    implementation(BuildConfig.Libs.Default.kotlinx_coroutines_android)
    //glide
    implementation(BuildConfig.Libs.Default.glide)
    annotationProcessor(BuildConfig.Libs.Default.glide_compiler)
    implementation(BuildConfig.Libs.Google.gson)



}