package com.dingyi.myluaapp.build.modules.android.tasks

import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.command.CommandRunner
import com.dingyi.myluaapp.build.default.DefaultTask
import com.dingyi.myluaapp.build.modules.android.config.BuildConfig
import com.dingyi.myluaapp.common.kts.Paths
import org.luaj.vm2.LuaBoolean
import java.io.File
import java.util.*

class ZipAlignApk(private val module: Module) : DefaultTask(module) {

    override val name: String
        get() = getType()

    private lateinit var buildVariants: String


    private fun getType(): String {
        if (this::buildVariants.isInitialized) {
            return "ZipAlign${
                buildVariants.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }
            }Apk"
        }
        return javaClass.simpleName
    }


    private val zipAlignPath = "${Paths.nativeLibraryDir}/libzipalign.so"


    private val inputPath: String
        get() = "build/outputs/apk/$buildVariants/app-$buildVariants-unsigned.apk"

    private val outputPath: String
        get() = "build/outputs/apk/$buildVariants/app-$buildVariants-zipalign.apk"


    override suspend fun prepare(): Task.State {

        buildVariants =
            module.getCache().getCache<BuildConfig>("${module.name}_build_config").buildVariants

        val zipAlignConfig = module
            .getMainBuilderScript()
            .get("android.buildTypes.${buildVariants}.zipAlignEnabled")


        if (zipAlignConfig is LuaBoolean) {
            if (!zipAlignConfig.booleanValue()) {
                return Task.State.SKIPPED
            }
        }

        return Task.State.DEFAULT
    }

    override suspend fun run() {

        val runner = CommandRunner()

        runner.runCommand(
            zipAlignPath, arrayOf(
                "-p -f -v 4",
                module.getFileManager().resolveFile(inputPath, module).path,
                module.getFileManager().resolveFile(outputPath, module).path
            )
        )

        runner.clear()

        module.getFileManager().resolveFile(inputPath, module).delete()

    }
}