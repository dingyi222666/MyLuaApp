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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    //compileOnly(BuildConfig.Libs.AndroidX.appcompat)
    implementation(BuildConfig.Libs.Tools.intellij_platform_util)
    implementation(project(":platform-common"))
    implementation(project(":extensions"))
    implementation(project(":platform-api"))
    compileOnly(BuildConfig.Libs.Tools.commons_vfs)
    testImplementation(BuildConfig.Libs.Default.junit)
}