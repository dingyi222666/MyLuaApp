package com.dingyi.myluaapp.build.modules.android.tasks


import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.default.DefaultTask
import com.dingyi.myluaapp.build.modules.android.config.BuildConfig
import com.dingyi.myluaapp.build.util.getSHA256
import com.dingyi.myluaapp.common.kts.Paths
import com.dingyi.myluaapp.common.kts.toMD5
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class MergeJniLibs(
    private val module: Module
) : DefaultTask(module) {

    override val name: String
        get() = "MergeJniLibs"


    private val mergeDirectory = arrayOf(
        "src/main/jniLibs", //main sources
        "lib" // aar
    )

    private lateinit var buildVariants: String


    private lateinit var mergeAssetsFile: List<Pair<File, File>>

    override suspend fun prepare(): Task.State {

        buildVariants =
            module.getCache().getCache<BuildConfig>("${module.name}_build_config").buildVariants



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
                    }", "jni"
                )
            }.filter {
                it.isDirectory
            }
            .let {
                mergeDirectoryWithLibrary.addAll(it)
            }


        val allMergeJniLibsFile = mergeDirectoryWithLibrary
            .flatMap { directory ->
                directory.walkBottomUp()
                    .filter {
                        it.isFile
                    }.map {
                        directory to it
                    }
                    .toList()
            }



        if (allMergeJniLibsFile.isEmpty()) {
            return Task.State.SKIPPED
        }

        val incrementalMergeJniLibsFile = allMergeJniLibsFile
            .filterNot {
                val mergeAssetsFile = getMergeJniLibsPath(it)
                mergeAssetsFile.isFile && mergeAssetsFile
                    .getSHA256() == it.second.getSHA256()
            }

        mergeAssetsFile = incrementalMergeJniLibsFile

        return when {
            incrementalMergeJniLibsFile.isEmpty() -> Task.State.`UP-TO-DATE`
            incrementalMergeJniLibsFile.size < allMergeJniLibsFile.size -> Task.State.INCREMENT
            incrementalMergeJniLibsFile.size == allMergeJniLibsFile.size -> Task.State.DEFAULT
            else -> Task.State.DEFAULT
        }


    }


    override suspend fun run() {
        mergeAssetsFile
            .forEach {
                withContext(Dispatchers.IO) {
                    it.second.copyTo(getMergeJniLibsPath(it))
                }
            }
    }

    private fun getMergeJniLibsPath(pair: Pair<File, File>): File {
        val (directory, file) = pair

        return module
            .getFileManager()
            .resolveFile("build/intermediates/library_jni/$buildVariants", module)
            .let {
                File(it, file.path.substring(directory.path.length + 1))
            }

    }

}