package com.dingyi.myluaapp.build.modules.android.tasks


import com.android.apksigner.ApkSignerTool
import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.default.DefaultTask
import com.dingyi.myluaapp.build.modules.android.config.BuildConfig
import com.dingyi.myluaapp.common.kts.Paths
import com.dingyi.myluaapp.common.kts.toFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.luaj.vm2.LuaTable
import java.io.File
import java.util.*

class SignApk(private val module: Module) : DefaultTask(module) {

    override val name: String
        get() = getType()

    private lateinit var buildVariants: String


    private fun getType(): String {
        if (this::buildVariants.isInitialized) {
            return "Sign${
                buildVariants.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }
            }Apk"
        }
        return javaClass.simpleName
    }

    private lateinit var inputPath: File

    private val outputPath: String
        get() = "build/outputs/apk/$buildVariants/app-$buildVariants.apk"


    override suspend fun prepare(): Task.State {

        buildVariants =
            module.getCache().getCache<BuildConfig>("${module.name}_build_config").buildVariants

        arrayOf(
            "build/outputs/apk/$buildVariants/app-$buildVariants-unsigned.apk",
            "build/outputs/apk/$buildVariants/app-$buildVariants-zipalign.apk"
        ).forEach {
            val file = module
                .getFileManager()
                .resolveFile(it, module)

            if (file.exists()) {
                inputPath = file
                return@forEach
            }
        }

        return Task.State.DEFAULT
    }


    override suspend fun run() {

        val args = mutableListOf<String>()

        val signerConfig = module
            .getMainBuilderScript()
            .get("android.buildTypes.${buildVariants}.signingConfig")

        args.add("sign")



        if (signerConfig is LuaTable) {
            //TODO
        } else {
            args.add("--key")
            args.add(File(Paths.assetsDir.toFile(), "keys/testkey.pk8").path)

            args.add("--cert")
            args.add(File(Paths.assetsDir.toFile(), "keys/testkey.x509.pem").path)
        }

        args.add("--out")
        args.add(module.getFileManager().resolveFile(outputPath, module).path)
        args.add(inputPath.path)


        withContext(Dispatchers.IO) {
            ApkSignerTool
                .main(args.toTypedArray())
        }



        inputPath.deleteRecursively()

        module
            .getLogger()
            .info("\n")

        module
            .getLogger()
            .info(
                string = "APK generated successfully for project ${module.getProject().name},[install] or [open] APK",
                extra = module.getFileManager().resolveFile(outputPath, module).path
            )

    }
}