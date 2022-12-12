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
    compileOnly(BuildConfig.Libs.Tools.intellij_platform_util)
    compileOnly(BuildConfig.Libs.AndroidX.appcompat)
    compileOnly(BuildConfig.Libs.Tools.commons_vfs)
    implementation("org.lz4:lz4-java:1.8.0")
    implementation("org.json:json:20220924")
    compileOnly(project(":platform-common"))
    compileOnly(project(":platform-api"))
    compileOnly(project(":platform-util"))
    compileOnly(project(":platform-dsl"))
    compileOnly(project(":core-imp"))
    compileOnly(project(":service-container"))
    compileOnly(project(":extensions"))
    testImplementation(project(":extensions"))
    testImplementation(project(":platform-api"))
    testImplementation(BuildConfig.Libs.Default.junit)
}