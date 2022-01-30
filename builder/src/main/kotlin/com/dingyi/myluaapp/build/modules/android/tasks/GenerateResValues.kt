package com.dingyi.myluaapp.build.modules.android.tasks

import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.modules.android.compiler.AAPT2Compiler
import com.dingyi.myluaapp.build.modules.android.config.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class GenerateResValues(
    private val module: Module
) : Task {
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

        buildVariants = module.getProject()
            .getCache().getCache<BuildConfig>("build_config").buildVariants

        //create compile
        compiler = AAPT2Compiler()
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

            println("status:it:$incrementStatus")

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

                if (!compileFile.exists() && !compileFile2.exists()) {
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
        } else if (fileList.isEmpty()) {
            status = Task.State.`NO-SOURCE`
        }

        module.getLogger()
            .info(getOutputString(module, status))

        this@GenerateResValues.compileXmlList = compileXmlList

        status ?: Task.State.DEFAULT
    }


    override suspend fun run() {

        compiler.compile(compileXmlList, outputDirectory)

    }
}