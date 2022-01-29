package com.dingyi.myluaapp.build.modules.android.tasks

import com.dingyi.myluaapp.build.api.project.Module
import com.dingyi.myluaapp.build.api.task.Task
import com.dingyi.myluaapp.build.modules.android.compiler.AAPT2Compiler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.luaj.vm2.LuaValue
import java.util.*

class GenerateResValues(
    private val module: Module
):Task {
    override val name: String
        get() = getType()

    private lateinit var compiler: AAPT2Compiler

    private lateinit var outputDirectory: String

    private lateinit var compileXmlList: List<String>


    private lateinit var type: String

    private fun getType(): String {
        if (this::type.isInitialized) {
            return "Generate${type.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }}ResValues"
        }
        return javaClass.simpleName
    }

    private val compileDirectory: String
        get() = "build/intermediates/merged_res/${type}"


    private val aarResPackDirectory: String
        get() = "build/intermediates/aar_pack/${type}/res"


    override suspend fun prepare() = withContext(Dispatchers.IO) {
        type =
            (module.getProject().getMainBuilderScript().get("build_mode") as LuaValue).tojstring()

        //create compile
        compiler = AAPT2Compiler()
        outputDirectory = module.getFileManager()
            .resolveFile(compileDirectory, module).also {
                it.mkdirs()
            }.path

        val compileXmlList = mutableListOf<String>()

        var status: Task.State? = null


        arrayOf(
            module.getFileManager()
                .resolveFile(aarResPackDirectory, module),
            module.getFileManager()
                .resolveFile("src/main/res", module)
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
                            status = Task.State.`UP-TO-DATE`
                        }

                    }
            }
        }

        if (compileXmlList.isEmpty()) {
            status = Task.State.`UP-TO-DATE`
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