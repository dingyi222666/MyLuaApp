/**
 * @author: dingyi
 * @date: 2021/10/13 15:21
 * @description:
 **/

object BuildConfig {


    object Config {

        object Default {

            const val targetSdk = 30
            const val minSdk = 21
            const val buildToolsVersion = "31.0.0"
            const val compileSdk = 31
        }

        object App {
            const val versionCode = 20
            const val versionName = "0.4.0(alpna)"
            const val packageName = "com.dingyi.MyLuaApp"

        }
    }

    object Versions {
        const val kotlin_version = "1.6.10"
        const val android_gradle_plugin_version = "7.0.2"
        const val antlr_kotlin_version = "6304d5c1c4"
        const val gson_version = "2.8.8"
        const val material_version = "1.6.0-alpha01"
        const val appcompat_version = "1.4.0"
        const val code_editor_version = "0.8.4"
        const val lifecycle_version = "2.4.0"
        const val preference_ktx_version = "1.1.1"
        const val constraintlayout_version = "2.0.4"
        const val litepal_version = "3.2.3"
        const val glide_version = "4.11.0"
        const val net_version = "3.0.25"
        const val okhttp3_version = "4.9.0"
        const val channel_version = "1.1.4"
        const val swiperefreshlayout_version = "1.2.0-alpha01"
        const val desugar_jdk_libs_version = "1.1.5"
        const val lsp4j_version = "0.12.0"
        const val joni_version = "2.1.11"
        const val jcodings_version = "1.0.18"
        const val xmlgraphics_version = "1.14"
        const val dom_version = "2.3.0-jaxb-1.0.6"
        const val kotlinx_coroutines_android_version = "1.5.1"
        const val core_ktx_version = "1.7.0-rc01"
        const val brv_version = "1.3.37"
        const val multi_languages_version = "6.9"
    }

    object Libs {

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
            const val constraintlayout =
                "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout_version}"
            const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat_version}"
            const val preference_ktx =
                "androidx.preference:preference-ktx:${Versions.preference_ktx_version}"
            const val swiperefreshlayout =
                "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swiperefreshlayout_version}"

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

            const val desugar_jdk_libs =
                "com.android.tools:desugar_jdk_libs:${Versions.desugar_jdk_libs_version}"

            const val litepal = "org.litepal.guolindev:core:${Versions.litepal_version}"

            const val channel = "com.github.liangjingkanji:Channel:${Versions.channel_version}"

            const val lsp4j = "org.eclipse.lsp4j:org.eclipse.lsp4j:${Versions.lsp4j_version}"

            const val antlr_kotlin_runtime =
                "com.strumenta.antlr-kotlin:antlr-kotlin-runtime:${Versions.antlr_kotlin_version}"

            const val antlr_kotlin_target =
                "com.strumenta.antlr-kotlin:antlr-kotlin-target:${Versions.antlr_kotlin_version}"

            const val joni = "org.jruby.joni:joni:${Versions.joni_version}"

            const val jcodings = "org.jruby.jcodings:jcodings:${Versions.jcodings_version}"

            const val batik_css = "org.apache.xmlgraphics:batik-css:${Versions.xmlgraphics_version}"
            const val batik_util =
                "org.apache.xmlgraphics:batik-util:${Versions.xmlgraphics_version}"
            const val dom = "org.w3c:dom:${Versions.dom_version}"
            const val brv = "com.github.liangjingkanji:BRV:${Versions.brv_version}"
            const val multi_languages =
                "com.github.getActivity:MultiLanguages:${Versions.multi_languages_version}"
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