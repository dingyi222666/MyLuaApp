package com.dingyi.myluaapp.build.modules.android.tasks.build

import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.default.DefaultTask
import com.dingyi.myluaapp.build.modules.android.config.BuildConfig
import com.dingyi.myluaapp.build.modules.android.parser.AndroidManifestSimpleParser
import com.dingyi.myluaapp.common.ktx.getJavaClass
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeSpec
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.lang.model.element.Modifier

class GenerateBuildConfig(
    private val module: Module
) : DefaultTask(module) {
    override val name: String
        get() = getType()


    private lateinit var buildVariants: String

    private fun getType(): String {
        if (this::buildVariants.isInitialized) {
            return "Generate${
                buildVariants.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }
            }BuildConfig"
        }
        return javaClass.simpleName
    }

    private val buildConfigDir: String
        get() = "build/generated/source/buildConfig/${
            module.getCache().getCache<BuildConfig>("${module.name}_build_config").buildVariants
        }"

    private lateinit var buildConfigFile: String


    private lateinit var moduleType: String

    private lateinit var manifestInfo: AndroidManifestSimpleParser.AndroidManifestInfo

    private lateinit var buildConfig: BuildConfig

    private lateinit var buildConfigString: String

    override suspend fun prepare(): Task.State {

        var state: Task.State? = null

        moduleType = if (module.type == "AndroidApplication") {
            "Application"
        } else {
            "Library"
        }

        buildConfig = module.getCache().getCache("${module.name}_build_config")


        manifestInfo = readManifest()


        val versionCode = manifestInfo.versionCode


        val versionName = manifestInfo.versionName


        buildConfigString =
            buildConfig.toString() + if (moduleType == "Application") versionCode.toString() + versionName.toString() else ""

        val lastBuildConfig = module.getCache().readCacheFromDisk("${module.name}_build_config")

        buildConfigFile =
            "${buildConfigDir}/${manifestInfo.packageId?.replace(".", "/")}/BuildConfig.java"

        println("$lastBuildConfig $buildConfig")

        if (lastBuildConfig == buildConfigString) {
            if (module.getFileManager().resolveFile(buildConfigFile, module).isFile) {
                state = Task.State.`UP-TO-DATE`
            }
        }


        return state ?: Task.State.DEFAULT

    }

    private fun readManifest(): AndroidManifestSimpleParser.AndroidManifestInfo {
        return AndroidManifestSimpleParser().parse(
            module.getFileManager()
                .resolveFile("build/intermediates/merged_manifest/AndroidManifest.xml", module).path
        )
    }


    override suspend fun run(): Unit = withContext(Dispatchers.IO) {


        //JavaFile.builder(buildConfig.applicationId.toString(),
        val classBuilder =
            TypeSpec.classBuilder("BuildConfig")
                .addJavadoc("\$N", "Automatically generated file. DO NOT MODIFY")
                //public final
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addField(
                    //application_id
                    FieldSpec
                        .builder(
                            getJavaClass<String>(),
                            if (moduleType == "Application") "APPLICATION_ID" else "LIBRARY_PACKAGE_NAME",
                            Modifier.PUBLIC, Modifier.FINAL
                        )
                        .initializer("\$S", manifestInfo.packageId)
                        .build()
                ).addField(
                    //debug
                    FieldSpec
                        .builder(
                            TypeName.BOOLEAN,
                            "DEBUG",
                            Modifier.PUBLIC, Modifier.FINAL
                        )
                        .initializer("\$L", buildConfig.buildVariants == "debug")
                        .build()
                ).addField(
                    //build type
                    FieldSpec
                        .builder(
                            getJavaClass<String>(),
                            "BUILD_TYPE",
                            Modifier.PUBLIC, Modifier.FINAL
                        )
                        .initializer("\$S", buildConfig.buildVariants)
                        .build()
                )


        if (moduleType == "Application") {
            val versionCode =
                manifestInfo.versionCode


            if (versionCode != null) {


                classBuilder
                    .addField(
                        FieldSpec
                            .builder(
                                TypeName.INT,
                                "VERSION_CODE",
                                Modifier.PUBLIC,
                                Modifier.FINAL
                            )
                            .initializer("\$L", versionCode)
                            .build()
                    )

            }

            val versionName =
                manifestInfo.versionName

            if (versionName != null) {

                classBuilder
                    .addField(
                        FieldSpec
                            .builder(
                                getJavaClass<String>(),
                                "VERSION_NAME",
                                Modifier.PUBLIC,
                                Modifier.FINAL
                            )
                            .initializer("\$S", versionName)
                            .build()
                    )
            }
        }


        val configFile = module.getFileManager().resolveFile(buildConfigFile, module)

        if (!configFile.exists()) {
            configFile.parentFile?.mkdirs()
            runCatching { configFile.createNewFile() }
                .getOrThrow()
        }


        withContext(Dispatchers.IO) {
            runCatching {
                JavaFile
                    .builder(manifestInfo.packageId, classBuilder.build())
                    .build()
                    .writeTo(module.getFileManager().resolveFile(buildConfigDir, module))
            }.getOrThrow()
        }

        module.getCache().saveCacheToDisk("${module.name}_build_config", buildConfigString)


    }
}