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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    compileOnly(project(":platform-service-api"))
    compileOnly(project(":platform-openapi"))
    compileOnly(project(":platform-annotation"))
    testImplementation(kotlin("test"))
    testImplementation(kotlin("reflect"))
    testImplementation(project(":platform-service-api"))
    testImplementation(project(":platform-openapi"))
    testImplementation(project(":platform-annotation"))

}