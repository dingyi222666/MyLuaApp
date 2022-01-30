package com.dingyi.myluaapp.build.modules.android.tasks

import com.dingyi.myluaapp.build.CompileError
import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.modules.android.config.BuildConfig
import com.dingyi.myluaapp.build.modules.android.parser.AndroidManifestSimpleParser
import com.dingyi.myluaapp.build.modules.public.generator.SimpleJavaCodeGenerator
import com.dingyi.myluaapp.common.kts.toFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.luaj.vm2.LuaNil
import org.luaj.vm2.LuaValue
import java.util.*
import kotlin.text.StringBuilder

class GenerateBuildConfig(
    private val module: Module
): Task {
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
    override suspend fun prepare(): Task.State {

        var state: Task.State? = null

        moduleType = if (module.type == "AndroidApplication") {
            "Application"
        } else {
            "Library"
        }

        buildConfig = module.getCache().getCache("${module.name}_build_config")


        manifestInfo = readManifest()


        buildConfig = BuildConfig(
            applicationId = manifestInfo.packageId ?: buildConfig.applicationId
            ?: throw CompileError("Not Found applicationId!"),
            buildVariants = buildConfig.buildVariants
        )

        val lastBuildConfig = module.getCache().readCacheFromDisk("${module.name}_build_config")

        buildConfigFile =
            "${buildConfigDir}/${buildConfig.applicationId?.replace(".", "/")}/BuildConfig.java"

        println("$lastBuildConfig $buildConfig")

        if (lastBuildConfig == buildConfig.toString()) {
            if (module.getFileManager().getSnapshotManager()
                    .equalsSnapshot(
                        module.getFileManager().resolveFile(buildConfigFile, module)
                    )
            ) {
                state = Task.State.`UP-TO-DATE`
            }
        }

        module.getLogger().info(getOutputString(module, state))

        return state ?: Task.State.DEFAULT

    }

    private fun readManifest(): AndroidManifestSimpleParser.AndroidManifestInfo {
        return AndroidManifestSimpleParser().parse(
            module.getFileManager()
                .resolveFile("src/main/AndroidManifest.xml", module).path
        )
    }


    override suspend fun run(): Unit = withContext(Dispatchers.IO) {

        val javaCodeGenerator =
            SimpleJavaCodeGenerator(
                accessType = SimpleJavaCodeGenerator.AccessType.PUBLIC,
                isFinal = true,
                className = "BuildConfig"
            )

        javaCodeGenerator.packageName = buildConfig.applicationId.toString()

        //application_id
        javaCodeGenerator.addField(
            SimpleJavaCodeGenerator.Field(
                accessType = SimpleJavaCodeGenerator.AccessType.PUBLIC,
                isStatic = true,
                isFinal = true,
                fieldType = "String",
                fieldName = "\"${if (moduleType == "Application") "APPLICATION_ID" else "LIBRARY_PACKAGE_NAME"}\"",
                fieldValue = javaCodeGenerator.packageName
            )
        )

        //debug+
        javaCodeGenerator.addField(
            SimpleJavaCodeGenerator.Field(
                accessType = SimpleJavaCodeGenerator.AccessType.PUBLIC,
                isStatic = true,
                isFinal = true,
                fieldType = "boolean",
                fieldName = "DEBUG",
                fieldValue = buildConfig.buildVariants == "debug"
            )
        )

        //build_type
        javaCodeGenerator.addField(
            SimpleJavaCodeGenerator.Field(
                accessType = SimpleJavaCodeGenerator.AccessType.PUBLIC,
                isStatic = true,
                isFinal = true,
                fieldType = "String",
                fieldName = "BUILD_TYPE",
                fieldValue = "\"${buildConfig.buildVariants}\""
            )
        )

        if (moduleType == "Application") {
            val versionCode =
                (module.getMainBuilderScript().get("versionCode")) ?: manifestInfo.versionCode


            if (versionCode != null) {


                javaCodeGenerator.addField(
                    SimpleJavaCodeGenerator.Field(
                        accessType = SimpleJavaCodeGenerator.AccessType.PUBLIC,
                        isStatic = true,
                        isFinal = true,
                        fieldType = "int",
                        fieldName = "VERSION_CODE",
                        fieldValue = if (versionCode is LuaValue && versionCode !is LuaNil) {
                            versionCode.toint()
                        } else {
                            versionCode as Int
                        }
                    )
                )
            }

            val versionName =
                module.getMainBuilderScript().get("versionName") ?: manifestInfo.versionName

            if (versionName != null) {
                runCatching {
                    javaCodeGenerator.addField(
                        SimpleJavaCodeGenerator.Field(
                            accessType = SimpleJavaCodeGenerator.AccessType.PUBLIC,
                            isStatic = true,
                            isFinal = true,
                            fieldType = "String",
                            fieldName = "VERSION_NAME",
                            fieldValue = StringBuilder()
                                .append('"')
                                .append(
                                    if (versionName is LuaValue && versionName !is LuaNil) {
                                        versionName.tojstring()
                                    } else {
                                        versionName.toString()
                                    }
                                )
                                .append('"')
                                .toString()
                        )
                    )
                }.getOrThrow()
            }
        }


        val configFile = module.getFileManager().resolveFile(buildConfigFile, module)

        if (!configFile.exists()) {
            configFile.parentFile?.mkdirs()
            runCatching { configFile.createNewFile() }
                .getOrThrow()
        }



        withContext(Dispatchers.IO) {
            configFile.writeText(javaCodeGenerator.generate())
        }

        module.getFileManager().getSnapshotManager()
            .snapshot(configFile)

        module.getCache().saveCacheToDisk("${module.name}_build_config", buildConfig.toString())


    }
}