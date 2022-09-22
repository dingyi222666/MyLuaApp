/**
 * @author: dingyi
 * @date: 2021/10/13 15:21
 * @description:
 **/

object BuildConfig {


    object Config {

        object Default {

            const val targetSdk = 31
            const val minSdk = 26
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
        const val kotlin_version = "1.6.21"
        const val android_gradle_plugin_version = "7.1.3"
        const val antlr_kotlin_version = "6cf22fb195"
        const val gson_version = "2.8.8"
        const val material_version = "1.6.1"
        const val appcompat_version = "1.5.1"
        const val sora_editor_version = "0.11.0"
        const val lifecycle_version = "2.4.0"
        const val preference_ktx_version = "1.1.1"
        const val constraint_layout_version = "2.1.3"

        const val glide_version = "4.11.0"
        const val net_version = "3.4.12"
        const val okhttp3_version = "4.9.1"
        const val channel_version = "1.1.4"
        const val swipe_refresh_layout_version = "1.2.0-alpha01"
        const val lsp4j_version = "0.12.0"

        const val kotlinx_coroutines_android_version = "1.6.0"
        const val core_ktx_version = "1.7.0"
        const val brv_version = "1.3.79"
        const val multi_languages_version = "6.9"

        const val zip4j_version = "2.9.1"
        const val javapoet_version = "1.13.0"


        const val guava_version = "30.1.1-jre"

        const val r8_version = "3.0.73"

        const val mmkv_version = "1.2.12"

        const val android_build_tool_version = "7.1.1"

        const val preferences_dsl_version = "2.2.0"

        const val androlua_standalone_version = "1.0.4"

        const val java_parser_version = "3.24.2"

        const val jdt_version = "3.29.0"


        const val commons_vfs_version = "2.9.0"

        const val junit_version = "4.13.2"


        const val intellij_util_version = "222.4167.33"
    }

    object Libs {

        object Annotation {
            const val build_tools_annotation = "com.android.tools:annotations:26.0.0"
        }

        object Plugin {
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

        }

        object Default {

            const val kotlin_stdlib =
                "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin_version}"
            const val glide_compiler =
                "com.github.bumptech.glide:compiler:${Versions.glide_version}"
            const val glide = "com.github.bumptech.glide:glide:${Versions.glide_version}"
            const val kotlinx_coroutines_android =
                "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinx_coroutines_android_version}"

            const val junit = "junit:junit:${Versions.junit_version}"
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

            const val mmkv = "com.tencent:mmkv:${Versions.mmkv_version}"

            const val androlua_standalone =
                "io.github.dingyi222666:androlua-standlone:${Versions.androlua_standalone_version}"

            const val java_parser =
                "com.github.javaparser:javaparser-symbol-solver-core:${Versions.java_parser_version}"


            const val commons_vfs =
                "org.apache.commons:commons-vfs2${Versions.commons_vfs_version}"


            const val intellij_platform_util = "com.jetbrains.intellij.platform:util:${Versions.intellij_util_version}"



        }

        object BuildTools {
            const val ecj_compiler = "org.eclipse.jdt:ecj:${Versions.jdt_version}"
            const val conscrypt = "org.conscrypt:conscrypt-android:2.5.2"

            const val javapoet = "com.squareup:javapoet:${Versions.javapoet_version}"

            const val r8 =
                "com.android.tools:r8:${Versions.r8_version}"


            const val zip_flinger = "com.android:zipflinger:${Versions.android_build_tool_version}"

            const val kotlin_compiler =
                "org.jetbrains.kotlin:kotlin-compiler-embeddable:${Versions.kotlin_version}"

        }

        object Views {
            val sora_editor = arrayOf(
                "io.github.Rosemoe.sora-editor:editor:${Versions.sora_editor_version}",
                "io.github.Rosemoe.sora-editor:editor-kt:${Versions.sora_editor_version}",
                "io.github.Rosemoe.sora-editor:language-textmate:${Versions.sora_editor_version}",
            )

            const val preference_dsl =
                "de.maxr1998:modernandroidpreferences:${Versions.preferences_dsl_version}"


        }

        object Network {
            const val net = "com.github.liangjingkanji:Net:${Versions.net_version}"
            const val okhttp3 = "com.squareup.okhttp3:okhttp:${Versions.okhttp3_version}"
        }

    }


}