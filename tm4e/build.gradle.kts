plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    compileSdk = 30

    defaultConfig {
        minSdk = 21
        targetSdk = 30

        // Required when setting minSdkVersion to 20 or lower
        multiDexEnabled = true

        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        // Flag to enable support for the new language APIs
        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11

    }
    kotlinOptions {
        jvmTarget = "1.11"
    }
}

dependencies {
    api("org.jruby.joni:joni:2.1.11")
    api("org.jruby.jcodings:jcodings:1.0.18")
    api("com.google.code.gson:gson:2.8.8")
    implementation("org.apache.xmlgraphics:batik-css:1.14")
    implementation("org.apache.xmlgraphics:batik-util:1.14")
    implementation("org.w3c:dom:2.3.0-jaxb-1.0.6")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")
}
repositories {
    mavenCentral()
}