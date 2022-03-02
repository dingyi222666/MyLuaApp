package com.dingyi.myluaapp.build.modules.android.tasks.build

import com.android.manifmerger.ManifestMerger2
import com.android.manifmerger.ManifestSystemProperty
import com.android.manifmerger.MergingReport

import com.dingyi.myluaapp.build.CompileError
import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.default.DefaultTask
import com.dingyi.myluaapp.build.modules.android.config.BuildConfig
import com.dingyi.myluaapp.common.ktx.Paths
import com.dingyi.myluaapp.common.ktx.toFile
import com.dingyi.myluaapp.common.ktx.toMD5
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.luaj.vm2.LuaValue
import java.io.File

import java.util.*

class MergeManifest(private val applicationModule: Module) : DefaultTask(applicationModule) {
    override val name: String
        get() = getType()

    private lateinit var buildVariants: String


    private fun getType(): String {
        if (this::buildVariants.isInitialized) {
            return "Merge${
                buildVariants.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }
            }AndroidManifest"
        }
        return javaClass.simpleName
    }

    private val mainManifestFile = "src/main/AndroidManifest.xml"


    private lateinit var buildConfig: BuildConfig

    override suspend fun prepare(): Task.State {

        buildConfig =
            applicationModule.getCache().getCache("${applicationModule.name}_build_config")


        return Task.State.DEFAULT
    }

    override suspend fun run() = withContext(Dispatchers.IO) {

        val manifestMergerInvoker = ManifestMerger2.newMerger(
            applicationModule.getFileManager().resolveFile(mainManifestFile, applicationModule),
            applicationModule.getLogger(),
            ManifestMerger2.MergeType.APPLICATION
        ).withFeatures(ManifestMerger2.Invoker.Feature.REMOVE_TOOLS_DECLARATIONS)
            .withFeatures(ManifestMerger2.Invoker.Feature.MAKE_AAPT_SAFE)


        val buildScript = applicationModule.getMainBuilderScript()

        applicationModule.getProject()
            .getAllModule()
            .filterNot { it == applicationModule }
            .mapNotNull {
                findManifestFile(it)
            }.forEach {
                manifestMergerInvoker.addLibraryManifest(it)
            }


        applicationModule
            .getProject()
            .getAllDependency()
            .filter {
                it.type == "aar"
            }
            .flatMap {
                it.getDependenciesFile()
            }
            .toSet()
            .map {
                File(
                    "${Paths.extractAarDir}${File.separator}${
                        it.path.toMD5()
                    }".toFile(), "AndroidManifest.xml"
                )
            }
            .filter {
                it.isFile
            }.forEach {
                manifestMergerInvoker.addLibraryManifest(it)
            }


        if (buildConfig.buildVariants == "debug") {
            manifestMergerInvoker.withFeatures(ManifestMerger2.Invoker.Feature.DEBUGGABLE)
        }


        applyModule.getLogger()
            .info("\n")


        arrayOf(
            (buildScript.get("android.defaultConfig.applicationId")
                    as LuaValue).tojstring() to ManifestSystemProperty.PACKAGE,
            (buildScript.get("android.defaultConfig.versionCode")
                    as LuaValue).tojstring() to ManifestSystemProperty.VERSION_CODE,
            (buildScript.get("android.defaultConfig.versionName")
                    as LuaValue).tojstring() to ManifestSystemProperty.VERSION_NAME,
            (buildScript.get("android.defaultConfig.minSdkVersion")
                    as LuaValue).tojstring() to ManifestSystemProperty.MIN_SDK_VERSION,
            (buildScript.get("android.defaultConfig.targetSdkVersion")
                    as LuaValue).tojstring() to ManifestSystemProperty.TARGET_SDK_VERSION,
            (buildScript.get("android.defaultConfig.maxSdkVersion")
                    as LuaValue).tojstring() to ManifestSystemProperty.MAX_SDK_VERSION

        ).forEach {
            if (it.first != "nil") {
                manifestMergerInvoker
                    .setOverride(it.second, it.first)
            } else {
                applicationModule.getLogger()
                    .warning("w:build script property android.defaultConfig.${it.second.toCamelCase()} is null")

            }

        }




        val outMergedManifestLocation = applicationModule
            .getFileManager()
            .resolveFile(
                "build/intermediates/merged_manifest/AndroidManifest.xml",
                applicationModule
            )

        applyModule.getLogger()
            .info("\n")

        val mergingReport = manifestMergerInvoker.merge()

        if (mergingReport.result == MergingReport.Result.ERROR) {
            mergingReport.log(applicationModule.getLogger())
            throw RuntimeException(mergingReport.reportString)
        }
        if (mergingReport.result == MergingReport.Result.WARNING) {
            mergingReport.log(applicationModule.getLogger())
        }

        val annotatedDocument =
            mergingReport.getMergedDocument(MergingReport.MergedManifestKind.BLAME)
        if (annotatedDocument != null) {
            applicationModule.getLogger().verbose(annotatedDocument)
        }
        applicationModule.getLogger()
            .verbose("Merged manifest saved to ${outMergedManifestLocation.path}")

        val outString = mergingReport.getMergedDocument(MergingReport.MergedManifestKind.MERGED)

        outMergedManifestLocation.parentFile?.mkdirs()
        outMergedManifestLocation.createNewFile()

        if (outString != null) {
            outMergedManifestLocation.writeText(outString)
        }

        applyModule.getLogger()
            .info("\n")

    }


    private fun findManifestFile(module: Module): File? {

        if (module.type == "AndroidLibrary") {

            val file = module
                .getFileManager()
                .resolveFile("build/intermediates/merged_manifest/AndroidManifest.xml", module)

            if (!file.exists()) {
                throw CompileError("missing manifest for ${module.name}")
            }

            return file
        }

        return null


    }


}