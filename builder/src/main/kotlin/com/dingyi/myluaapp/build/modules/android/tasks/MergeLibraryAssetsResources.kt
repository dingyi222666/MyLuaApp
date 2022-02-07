package com.dingyi.myluaapp.build.modules.android.tasks

import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.default.DefaultTask
import com.dingyi.myluaapp.build.util.getSHA256
import com.dingyi.myluaapp.common.kts.Paths
import com.dingyi.myluaapp.common.kts.toMD5
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class MergeLibraryAssetsResources(
    private val module: Module
) : DefaultTask(module) {

    override val name: String
        get() = "MergeLibraryAssetsResources"


    private val mergeDirectory = arrayOf(
        "src/main/assets", //main sources
        "assets" // aar
    )

    private lateinit var mergeAssetsFile: List<Pair<File, File>>

    override suspend fun prepare(): Task.State {

        val mergeDirectoryWithLibrary = mutableListOf(
            module
                .getFileManager()
                .resolveFile(mergeDirectory[0], module)
        )


        module
            .getDependencies()
            .filter { it.type == "aar" }
            .flatMap {
                it.getDependenciesFile()
            }
            .map { file ->
                File(
                    "${Paths.extractAarDir}${File.separator}${
                        file.path.toMD5()
                    }", "assets"
                )
            }.filter {
                it.isDirectory
            }
            .let {
                mergeDirectoryWithLibrary.addAll(it)
            }


        val allMergeAssetsFile = mergeDirectoryWithLibrary
            .flatMap { directory ->
                directory.walkBottomUp()
                    .filter {
                        it.isFile
                    }.map {
                        directory to it
                    }
                    .toList()
            }



        if (allMergeAssetsFile.isEmpty()) {
            return Task.State.SKIPPED
        }

        val incrementalMergeAssetsFile = allMergeAssetsFile
            .filterNot {
                val mergeAssetsFile = getMergeAssetsPath(it)
                mergeAssetsFile.isFile && mergeAssetsFile
                    .getSHA256() == it.second.getSHA256()
            }

        mergeAssetsFile = incrementalMergeAssetsFile

        return when {
            incrementalMergeAssetsFile.isEmpty() -> Task.State.`UP-TO-DATE`
            incrementalMergeAssetsFile.size < allMergeAssetsFile.size -> Task.State.INCREMENT
            incrementalMergeAssetsFile.size == allMergeAssetsFile.size -> Task.State.DEFAULT
            else -> Task.State.DEFAULT
        }


    }


    override suspend fun run() {
        mergeAssetsFile
            .forEach {
                withContext(Dispatchers.IO) {
                    it.second.copyTo(getMergeAssetsPath(it))
                }
            }
    }

    private fun getMergeAssetsPath(pair: Pair<File, File>): File {
        val (directory, file) = pair

        return module
            .getFileManager()
            .resolveFile("build/intermediates/merged_assets", module)
            .let {
                File(it, file.path.substring(directory.path.length + 1))
            }

    }

}