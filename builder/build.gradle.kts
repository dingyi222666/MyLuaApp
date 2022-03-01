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
                abiFilters.addAll(arrayOf("armeabi-v7a", "x86", "arm64-v8a"))
            }
        }
        debug {
            isMinifyEnabled = false
            ndk {
                abiFilters.addAll(arrayOf("armeabi-v7a", "x86", "arm64-v8a"))
            }
        }

    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }



    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
}

dependencies {

    implementation(fileTree("dir" to "libs", "include" to arrayOf("*.jar")))//libs jar

    implementation(project(":common"))
    implementation(project(":luaj"))


    implementation("com.google.guava:listenablefuture:9999.0-empty-to-avoid-conflict-with-guava")
    implementation(BuildConfig.Libs.Tools.zip4j)

    implementation(BuildConfig.Libs.Google.gson)
    implementation(BuildConfig.Libs.Google.guava)


    implementation(BuildConfig.Libs.BuildTools.zip_flinger) {
        exclude(group = "com.android.tools",module = "annotations")
    }
    implementation(BuildConfig.Libs.BuildTools.r8)
    implementation(BuildConfig.Libs.Annotation.build_tools_annotation)

    implementation(BuildConfig.Libs.Default.kotlinx_coroutines_android)

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