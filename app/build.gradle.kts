plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
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
        dataBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            isMinifyEnabled = false

            ndk {
                abiFilters.addAll(arrayOf("armeabi-v7a", "arm64-v8a"))
            }
        }
    }
    packagingOptions {
        resources.excludes.addAll(listOf("META-INF/**","xsd/*","license/*"))
        resources.pickFirsts.add("kotlin/**")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}


dependencies {

    implementation(project(":editor")) // editor
    implementation(project(":platform-common"))
    implementation(project(":core"))


    implementation(fileTree("dir" to "libs", "include" to arrayOf("*.jar")))//libs jar

    //androidx and more

    implementation(BuildConfig.Libs.AndroidX.swiperefreshlayout)
    implementation(BuildConfig.Libs.AndroidX.appcompat)
    implementation(BuildConfig.Libs.AndroidX.lifecycle_viewmodel)
    implementation(BuildConfig.Libs.AndroidX.lifecycle_livedata)
    implementation(BuildConfig.Libs.AndroidX.lifecycle_runtime)
    implementation(BuildConfig.Libs.AndroidX.constraint_layout)
    implementation(BuildConfig.Libs.AndroidX.preference_ktx)

    implementation(BuildConfig.Libs.Views.preference_dsl)

    //google
    implementation(BuildConfig.Libs.Google.material)
    implementation(BuildConfig.Libs.Google.gson)

    BuildConfig.Libs.Views.sora_editor.forEach {
        implementation(it)  {
            exclude("xml-apis","xml-apis")
            exclude("xerces","xercesImpl")
        }
    }

    implementation(BuildConfig.Libs.Tools.channel)
    implementation(BuildConfig.Libs.Tools.brv)
    implementation(BuildConfig.Libs.Tools.zip4j)
    implementation(BuildConfig.Libs.Tools.mmkv)
    implementation(BuildConfig.Libs.Tools.multi_languages)

    implementation(BuildConfig.Libs.Default.kotlin_stdlib) //kt


    //glide
    implementation(BuildConfig.Libs.Default.glide)
    kapt(BuildConfig.Libs.Default.glide_compiler)


    // OkHttp 框架：https://github.com/square/okhttp
    // noinspection GradleDependency
    implementation(BuildConfig.Libs.Network.okhttp3)
    // 网络请求框架：https://hub.fastgit.org/liangjingkanji/Net
    implementation(BuildConfig.Libs.Network.net)




}