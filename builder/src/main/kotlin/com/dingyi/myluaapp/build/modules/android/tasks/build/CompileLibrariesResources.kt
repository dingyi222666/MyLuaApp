package com.dingyi.myluaapp.build.modules.android.tasks.build

import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.default.DefaultTask
import com.dingyi.myluaapp.build.modules.android.compiler.AAPT2Compiler
import com.dingyi.myluaapp.common.kts.Paths
import com.dingyi.myluaapp.common.kts.toFile
import com.dingyi.myluaapp.common.kts.toMD5
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File


class CompileLibrariesResources(private val module: Module) : DefaultTask(module) {
    override val name: String
        get() = "CompileLibrariesResources"


    override suspend fun prepare(): Task.State {
        getTaskInput().let { taskInput ->


            module.getDependencies()
                .flatMap {
                    it.getDependenciesFile()
                }.filter {
                    it.isFile and it.name.endsWith("aar")
                }.mapNotNull { file ->
                    "${Paths.extractAarDir}${File.separator}${
                        file.path.toMD5()
                    }".toFile()
                        .let { if (it.isDirectory && File(it, "res").isDirectory) it else null }
                }.forEach {
                    taskInput.addInputDirectory(it)
                }

            taskInput.transformDirectoryToFile()

        }

        return super.prepare()
    }

    override suspend fun run() = withContext(Dispatchers.IO) {

        val aapt2Compiler = AAPT2Compiler(module.getLogger())


        getTaskInput().let {
            if (isIncremental) it.getIncrementalInputFile() else it.getAllInputFile()
        }.forEach { file ->
            val libraryFile = file.toFile()
            val compileXmlList = libraryFile.walkBottomUp()
                .filter {
                    it.isFile && it.name != "AndroidManifest.xml"
                }.toList()


            if (compileXmlList.isNotEmpty()) {
                aapt2Compiler.compile(
                    otherCommand = arrayOf(
                        "--dir",
                        File(libraryFile, "res").absolutePath,
                        "--legacy"
                    ),
                    outputDirectory = File(libraryFile, "compile_res.zip").apply {
                        if (exists()) delete()
                        createNewFile()
                    }.absolutePath
                )

                getTaskInput().bindOutputFile(file,File(libraryFile, "compile_res.zip"))

            }

        }

        getTaskInput().snapshot()

        aapt2Compiler.close()

    }

}