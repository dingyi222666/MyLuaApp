/**
 * @author: dingyi
 * @date: 2021/10/13 15:21
 * @description:
 **/

object BuildConfig {


    object Config {

        object Default {

            const val targetSdk = 26
            const val minSdk = 30
            const val buildToolsVersion = "31.0.0"
            const val compileSdk = 31
        }

        object App {
            const val versionCode = 21
            const val versionName = "0.4.1(alpha)"
            const val packageName = "com.dingyi.MyLuaApp"

        }
    }

    object Versions {
        const val kotlin_version = "1.6.10"
        const val android_gradle_plugin_version = "7.0.3"
        const val antlr_kotlin_version = "6304d5c1c4"
        const val gson_version = "2.8.8"
        const val material_version = "1.5.0"
        const val appcompat_version = "1.4.1"
        const val code_editor_version = "0.8.4"
        const val lifecycle_version = "2.4.0"
        const val preference_ktx_version = "1.1.1"
        const val constraint_layout_version = "2.0.4"

        const val glide_version = "4.11.0"
        const val net_version = "3.1.2"
        const val okhttp3_version = "4.9.1"
        const val channel_version = "1.1.4"
        const val swipe_refresh_layout_version = "1.2.0-alpha01"
        const val lsp4j_version = "0.12.0"

        const val kotlinx_coroutines_android_version = "1.5.1"
        const val core_ktx_version = "1.7.0"
        const val brv_version = "1.3.51"
        const val multi_languages_version = "6.9"

        const val zip4j_version = "2.9.1"
        const val javapoet_version = "1.13.0"


        const val guava_version = "24.0-android"

        const val r8_version = "3.0.73"


        const val android_build_tool_version = "7.1.1"

    }

    object Libs {

        object Annotation {
            const val build_tools_annotation = "com.android.tools:annotations:26.0.0"
        }

        object Plugin {
            const val kotlin_gradle_plugin =
                "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin_version}"
            const val android_gradle_plugin =
                "com.android.tools.build:gradle:${Versions.android_gradle_plugin_version}"
            const val antlr_kotlin_gradle_plugin =
                "com.strumenta.antlr-kotlin:antlr-kotlin-gradle-plugin:${Versions.antlr_kotlin_version}"
        }

        object Google {
            const val gson = "com.google.code.gson:gson:${Versions.gson_version}"
            const val guava = "com.google.guava:guava:${Versions.guava_version}"
            const val material = "com.google.android.material:material:${Versions.material_version}"
        }

        object AndroidX {
            const val core_kotlinx = "androidx.core:core-ktx:${Versions.core_ktx_version}"
            const val lifecycle_livedata =
                "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle_version}"
            const val lifecycle_viewmodel =
                "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle_version}"
            const val lifecycle_runtime =
                "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle_version}"
            const val constraint_layout =
                "androidx.constraintlayout:constraintlayout:${Versions.constraint_layout_version}"
            const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat_version}"
            const val preference_ktx =
                "androidx.preference:preference-ktx:${Versions.preference_ktx_version}"
            const val swiperefreshlayout =
                "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipe_refresh_layout_version}"
            const val viewpager2 = "androidx.viewpager2:viewpager2:1.0.0"

        }

        object Default {

            const val kotlin_stdlib =
                "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin_version}"
            const val glide_compiler =
                "com.github.bumptech.glide:compiler:${Versions.glide_version}"
            const val glide = "com.github.bumptech.glide:glide:${Versions.glide_version}"
            const val kotlinx_coroutines_android =
                "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinx_coroutines_android_version}"
        }

        object Tools {


            const val channel = "com.github.liangjingkanji:Channel:${Versions.channel_version}"

            const val lsp4j = "org.eclipse.lsp4j:org.eclipse.lsp4j:${Versions.lsp4j_version}"

            const val antlr_kotlin_runtime =
                "com.strumenta.antlr-kotlin:antlr-kotlin-runtime:${Versions.antlr_kotlin_version}"

            const val antlr_kotlin_target =
                "com.strumenta.antlr-kotlin:antlr-kotlin-target:${Versions.antlr_kotlin_version}"

            const val brv = "com.github.liangjingkanji:BRV:${Versions.brv_version}"
            const val multi_languages =
                "com.github.getActivity:MultiLanguages:${Versions.multi_languages_version}"


            const val zip4j = "net.lingala.zip4j:zip4j:${Versions.zip4j_version}"


        }

        object BuildTools {
            const val conscrypt = "org.conscrypt:conscrypt-android:2.5.2"
            const val javapoet = "com.squareup:javapoet:${Versions.javapoet_version}"

            const val r8 =
                "com.android.tools:r8:${Versions.r8_version}"


            const val zip_flinger = "com.android:zipflinger:${Versions.android_build_tool_version}"


        }

        object Views {
            const val code_editor =
                "io.github.Rosemoe.sora-editor:editor:${Versions.code_editor_version}"

           
        }

        object Network {
            const val net = "com.github.liangjingkanji:Net:${Versions.net_version}"
            const val okhttp3 = "com.squareup.okhttp3:okhttp:${Versions.okhttp3_version}"
        }

    }


}