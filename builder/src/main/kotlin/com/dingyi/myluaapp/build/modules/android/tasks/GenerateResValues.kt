package com.dingyi.myluaapp.build.modules.android.tasks

import com.dingyi.myluaapp.build.api.project.Module
import com.dingyi.myluaapp.build.api.task.Task
import com.dingyi.myluaapp.build.modules.android.compiler.AAPT2Compiler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GenerateResValues(
    private val module: Module
):Task {
    override val name: String
        get() = javaClass.name

    private lateinit var compiler: AAPT2Compiler

    private lateinit var outputDirectory: String

    private lateinit var compileXmlList: List<String>

    override suspend fun prepare() = withContext(Dispatchers.IO) {
        //create compile
        compiler = AAPT2Compiler()
        outputDirectory = module.getFileManager()
            .resolveFile("build/res/compile_cache", module).also {
                it.mkdirs()
            }.path

        val compileXmlList = mutableListOf<String>()

        var status = ""


        arrayOf(
            module.getFileManager()
                .resolveFile("src/main/res", module),
            module.getFileManager()
                .resolveFile("build/res/compile_aar", module)
        ).forEach { dir ->
            if (dir.isDirectory) {
                dir.walkBottomUp()
                    .filter { file ->
                        file.isFile && file.name.endsWith("xml")
                    }
                    .forEach {
                        var incrementStatus = module.getFileManager().getSnapshotManager()
                            .equalsAndSnapshot(it)

                        println("status:it:$incrementStatus")

                        if (incrementStatus) {
                            val compileFile =
                                module.getFileManager()
                                    .resolveFile(
                                        "build/res/compile_cache/${it.parentFile?.name}_${
                                            it.name
                                        }.flat", module
                                    )

                            val compileFile2 =
                                module.getFileManager()
                                    .resolveFile(
                                        "build/res/compile_cache/${it.parentFile?.name}-${
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
                            status = "UP-TO-DATE"
                        }

                    }
            }
        }

        if (compileXmlList.isEmpty()) {
            status = "NO-SOURCE"
        }

        module.getLogger()
            .info(getOutputString(module, status))

        this@GenerateResValues.compileXmlList = compileXmlList
    }


    override suspend fun run() {
        if (compileXmlList.isNotEmpty()) {
            compiler.compile(compileXmlList, outputDirectory)
        }
    }
}