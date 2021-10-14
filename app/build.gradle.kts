plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {

    compileSdk = BuildConfig.Config.Default.compileSdk
    buildToolsVersion = BuildConfig.Config.Default.buildToolsVersion

    defaultConfig {
        applicationId = BuildConfig.Config.App.packageName
        minSdk = BuildConfig.Config.Default.minSdk
        targetSdk = BuildConfig.Config.Default.targetSdk

        versionCode = BuildConfig.Config.App.versionCode
        versionName = BuildConfig.Config.App.versionName
        multiDexEnabled = true
    }

    sourceSets {
        getByName("main") {
            java.srcDirs("src/main/kotlin")
        }
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        getByName("debug") {
            isMinifyEnabled = false

            ndk {
                abiFilters.addAll(arrayOf("armeabi-v7a", "x86", "arm64-v8a"))
            }
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}


dependencies {
    implementation(project(":editor")) // editor

    implementation(project(":luaj"))
    implementation(fileTree("dir" to "libs", "include" to arrayOf("*.jar")))//libs jar

    //androidx and more

    implementation(BuildConfig.Libs.AndroidX.swiperefreshlayout)
    implementation(BuildConfig.Libs.AndroidX.appcompat)
    implementation(BuildConfig.Libs.AndroidX.lifecycle_viewmodel)
    implementation(BuildConfig.Libs.AndroidX.lifecycle_livedata)
    implementation(BuildConfig.Libs.AndroidX.lifecycle_runtime)
    implementation(BuildConfig.Libs.AndroidX.constraintlayout)
    implementation(BuildConfig.Libs.AndroidX.preference_ktx)

    //google
    implementation(BuildConfig.Libs.Google.material)
    implementation(BuildConfig.Libs.Google.gson)

    implementation(BuildConfig.Libs.Views.code_editor)

    implementation(BuildConfig.Libs.Tools.livedatbus)

    implementation(BuildConfig.Libs.Default.kotlin_stdlib) //kt


    //about
    implementation(BuildConfig.Libs.Views.drakeet_about)
    implementation(BuildConfig.Libs.Views.drakeet_multitype)

    //litepal

    implementation(BuildConfig.Libs.Tools.litepal)

    //glide
    implementation(BuildConfig.Libs.Default.glide)
    annotationProcessor(BuildConfig.Libs.Default.glide_compiler)


    // OkHttp 框架：https://github.com/square/okhttp
    // noinspection GradleDependency
    implementation(BuildConfig.Libs.Network.okhttp3)
    // 网络请求框架：https://github.com/getActivity/EasyHttp
    implementation(BuildConfig.Libs.Network.easy_http)


    coreLibraryDesugaring(BuildConfig.Libs.Tools.desugar_jdk_libs)


}