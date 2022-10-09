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
    implementation(BuildConfig.Libs.Tools.intellij_platform_xml_util)
    implementation(BuildConfig.Libs.Tools.intellij_util_jdom)
    //compileOnly(BuildConfig.Libs.AndroidX.appcompat)
    compileOnly(BuildConfig.Libs.Tools.commons_vfs)
    compileOnly(project(":platform-common"))
    compileOnly(project(":platform-util"))
    testImplementation(BuildConfig.Libs.Default.junit)
}