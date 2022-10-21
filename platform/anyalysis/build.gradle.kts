plugins {
    id("kotlin-android")
    id("com.android.library")
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
        viewBinding = false
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
    compileOnly(BuildConfig.Libs.Tools.intellij_platform_util)
    //compileOnly(BuildConfig.Libs.AndroidX.appcompat)
    implementation("org.slf4j:slf4j-api:2.0.3")
    compileOnly(project(":platform-common"))
    implementation(project(":platform-api"))
    implementation(BuildConfig.Libs.Default.kotlinx_coroutines_android)
    compileOnly(BuildConfig.Libs.Tools.commons_vfs)

    testImplementation(BuildConfig.Libs.Default.junit)
}