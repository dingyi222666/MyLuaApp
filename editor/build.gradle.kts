plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    compileSdk = 30

    defaultConfig {
        minSdk  = 21
        targetSdk  = 30

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

    implementation (fileTree("dir" to "../app/libs", "include" to listOf("*.jar", "*.aar")))//libs jar

    implementation (project(":tm4e"))

    //editor
    implementation (BuildConfig.Libs.AndroidX.appcompat)
    implementation ("io.github.Rosemoe.sora-editor:editor:0.6.0-dev-4")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0-alpha01")

}