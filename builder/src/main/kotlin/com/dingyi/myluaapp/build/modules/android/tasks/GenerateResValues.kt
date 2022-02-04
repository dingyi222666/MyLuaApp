package com.dingyi.myluaapp.build.modules.android.tasks

import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.default.DefaultTask
import com.dingyi.myluaapp.build.modules.android.compiler.AAPT2Compiler
import com.dingyi.myluaapp.build.modules.android.config.BuildConfig
import com.dingyi.myluaapp.common.kts.toFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

@Deprecated("Migrate to another build task")
//TODO Delete it
class GenerateResValues(
    private val module: Module
) : DefaultTask(module) {
    override val name: String
        get() = getType()

    private lateinit var compiler: AAPT2Compiler

    private lateinit var outputDirectory: String

    private lateinit var compileXmlList: List<String>


    private lateinit var buildVariants: String

    private fun getType(): String {
        if (this::buildVariants.isInitialized) {
            return "Generate${buildVariants.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }}ResValues"
        }
        return javaClass.simpleName
    }

    private val compileDirectory: String
        get() = "build/intermediates/merged_res/${buildVariants}"


    private val aarResPackDirectory: String
        get() = "build/intermediates/aar_pack/${buildVariants}/res"


    override suspend fun prepare() = withContext(Dispatchers.IO) {

        buildVariants = module.getCache().getCache<BuildConfig>("${module.name}_build_config").buildVariants

        //create compile
        compiler = AAPT2Compiler(module.getLogger())
        outputDirectory = module.getFileManager()
            .resolveFile(compileDirectory, module).also {
                it.mkdirs()
            }.path

        val compileXmlList = mutableListOf<String>()

        var status: Task.State? = null


        val fileList =
            arrayOf(
                module.getFileManager()
                    .resolveFile(aarResPackDirectory, module),
                module.getFileManager()
                    .resolveFile("src/main/res", module)
            ).flatMap { dir ->
                if (dir.isDirectory) {
                    dir.walkBottomUp()
                        .filter { file ->
                            file.isFile && file.name.endsWith("xml")
                        }.toList()
                } else {
                    listOf()
                }
            }

        fileList.forEach {
            var incrementStatus = module.getFileManager().getSnapshotManager()
                .equalsAndSnapshot(it)

            if (incrementStatus) {
                val compileFile =
                    module.getFileManager()
                        .resolveFile(
                            "$compileDirectory/${it.parentFile?.name}_${
                                it.name
                            }.flat", module
                        )

                val compileFile2 =
                    module.getFileManager()
                        .resolveFile(
                            "$compileDirectory/${it.parentFile?.name}-${
                                it.name
                            }.flat", module
                        )

                val snapshotManager = module.getFileManager().getSnapshotManager()
                if (snapshotManager.equalsSnapshot(compileFile) || snapshotManager.equalsSnapshot(
                        compileFile2
                    )
                ) {
                    incrementStatus = false
                }
            }

            if (!incrementStatus) {
                compileXmlList.add(it.path)
            } else {
                status = Task.State.INCREMENT
            }

        }


        if (fileList.isNotEmpty() && compileXmlList.isEmpty()) {
            status = Task.State.`UP-TO-DATE`
        } else if (fileList.isEmpty() && compileXmlList.isEmpty()) {
            status = Task.State.`NO-SOURCE`
        }


        this@GenerateResValues.compileXmlList = compileXmlList

        status ?: Task.State.DEFAULT
    }


    override suspend fun run() {

        compiler.compile(compileXmlList, outputDirectory)

        withContext(Dispatchers.IO) {
            outputDirectory.toFile().walkBottomUp()
                .filter { it.isFile }
                .forEach {
                    module.getFileManager().getSnapshotManager().snapshot(it)
                }
        }


    }
}