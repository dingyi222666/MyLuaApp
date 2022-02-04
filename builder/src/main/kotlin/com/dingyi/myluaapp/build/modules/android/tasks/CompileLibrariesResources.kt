package com.dingyi.myluaapp.build.modules.android.tasks

import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.default.DefaultTask
import com.dingyi.myluaapp.build.modules.android.compiler.AAPT2Compiler
import com.dingyi.myluaapp.common.kts.Paths
import com.dingyi.myluaapp.common.kts.toFile
import com.dingyi.myluaapp.common.kts.toMD5
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File


class CompileLibrariesResources(private val module: Module) : DefaultTask(module) {
    override val name: String
        get() = "CompileLibrariesResources"


    private lateinit var libraryList: List<File>

    override suspend fun prepare(): Task.State {

        val libraryFileList = module.getDependencies()
            .flatMap {
                it.getDependenciesFile()
            }.filter {
                it.isFile and it.name.endsWith("aar")
            }.map {
                "${Paths.explodedAarDir}${File.separator}${
                    it.path.toMD5()
                }".toFile()
            }.filter {
                it.exists()
            }.filter {
                File(it, "res").isDirectory
            }

        if (libraryFileList.isEmpty()) {
            return Task.State.SKIPPED
        }

        val incrementalLibraryList = libraryFileList.filter {
            val file = File(it, "compile_res.zip")
            module
                .getFileManager()
                .getSnapshotManager()
                .equalsAndSnapshot(file)
                .not() or (file.exists() && file.length() == 0L)
        }

        this.libraryList = incrementalLibraryList


        return when {
            incrementalLibraryList.isEmpty() -> Task.State.`UP-TO-DATE`
            incrementalLibraryList.size > libraryFileList.size -> Task.State.INCREMENT
            incrementalLibraryList.size == libraryFileList.size -> Task.State.DEFAULT
            else -> Task.State.DEFAULT
        }


    }

    override suspend fun run() = withContext(Dispatchers.IO) {

        val aapt2Compiler = AAPT2Compiler(module.getLogger())


        libraryList.forEach { libraryFile ->

            val compileXmlList = libraryFile.walkBottomUp()
                .filter {
                    it.isFile && it.name.endsWith("xml") && it.name != "AndroidManifest.xml"
                }.toList()


            if (compileXmlList.isNotEmpty()) {
                aapt2Compiler.compile(
                    otherCommand = arrayOf(
                        "--dir",
                        File(libraryFile, "res").absolutePath
                    ),
                    outputDirectory = File(libraryFile, "compile_res.zip").apply {
                        if (exists()) delete()
                        createNewFile()
                    }.absolutePath
                )
            }

        }


        aapt2Compiler.close()

    }

}