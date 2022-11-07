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

    buildTypes {

        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            ndk {
                abiFilters.addAll(arrayOf("armeabi-v7a", "arm64-v8a"))
            }
        }
        debug {
            isMinifyEnabled = false
            ndk {
                abiFilters.addAll(arrayOf("armeabi-v7a", "arm64-v8a"))
            }
        }

    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }


    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
}

dependencies {

    implementation(fileTree("dir" to "libs", "include" to arrayOf("*.jar")))//libs jar
    implementation(project(":platform-common"))

    implementation(project(":ide-api"))


    implementation(BuildConfig.Libs.Default.kotlinx_coroutines_android)

    implementation("com.google.guava:listenablefuture:9999.0-empty-to-avoid-conflict-with-guava")
    implementation(BuildConfig.Libs.Tools.zip4j)

    implementation(BuildConfig.Libs.Google.gson)
    implementation(BuildConfig.Libs.Google.guava)


    implementation(BuildConfig.Libs.BuildTools.zip_flinger) {
        exclude(group = "com.android.tools",module = "annotations")
    }
    implementation(BuildConfig.Libs.BuildTools.r8)
    implementation(BuildConfig.Libs.Annotation.build_tools_annotation)


    // OkHttp 框架：https://github.com/square/okhttp
    // noinspection GradleDependency
    implementation(BuildConfig.Libs.Network.okhttp3)
    // 网络请求框架：https://hub.fastgit.org/liangjingkanji/Net
    implementation(BuildConfig.Libs.Network.net)

    implementation(BuildConfig.Libs.BuildTools.conscrypt)


    implementation(BuildConfig.Libs.BuildTools.javapoet)


    // Optional -- Robolectric environment
    testImplementation("androidx.test:core:1.4.0")
    testImplementation("junit:junit:4.13.2")
    // Optional -- Mockito framework
    testImplementation("org.robolectric:robolectric:4.2.1")

}