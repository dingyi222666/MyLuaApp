package com.dingyi.myluaapp.build.modules.android.tasks.build

import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.default.DefaultTask
import com.dingyi.myluaapp.build.modules.android.config.BuildConfig
import com.dingyi.myluaapp.common.ktx.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.lingala.zip4j.ZipFile
import java.io.File


class MergeJavaResources(
    private val module: Module
) : DefaultTask(module) {

    override val name: String
        get() = "MergeJavaResources"

    private lateinit var buildVariants: String


    private val defaultJavaResourceDirectory = "src/main/resources"

    private val skipResourceFileList = arrayOf("META-INF/MANIFEST.MF")

    private val outputDirectory: String
        get() = "build/intermediates/library_java_res/$buildVariants"

    override suspend fun prepare(): Task.State {

        buildVariants =
            module.getCache().getCache<BuildConfig>("${module.name}_build_config").buildVariants


        getTaskInput().addInputDirectory(
            module
                .getFileManager()
                .resolveFile(defaultJavaResourceDirectory, module)
        )


        module
            .getDependencies()
            .flatMap { it.getDependenciesFile() }
            .filter {
                it.isFile && it.name.endsWith("aar", "jar")
            }
            .flatMap { aarFile ->
                if (aarFile.name.endsWith("aar")) {
                    "${Paths.extractAarDir}${File.separator}${
                        aarFile.path.toMD5()
                    }".toFile()
                        .walkBottomUp()
                        .filter {
                            it.isFile && it.name.endsWith("jar")
                        }.toList()
                } else {
                    listOf(aarFile)
                }
            }.forEach {
                getTaskInput().addInputFile(it)
            }

        getTaskInput().transformDirectoryToFile { it.isFile }


        return super.prepare()
    }

    override suspend fun run() = withContext(Dispatchers.IO) {

        val outputDirectory = module.getFileManager().resolveFile(outputDirectory, module)

        val defaultJavaResourceDirectory = module
            .getFileManager()
            .resolveFile(defaultJavaResourceDirectory, module)


        getTaskInput().let {
            if (isIncremental) it.getIncrementalInputFile() else it.getAllInputFile()
        }.forEach { inputFile ->
            val file = inputFile.toFile()
            val output = File(
                outputDirectory,
                file.path.substring(defaultJavaResourceDirectory.path.length + 1)
            )

            if (output.isFile) {
                output.delete()
            }

            if (inputFile.getSourceDirectory() == defaultJavaResourceDirectory.path) {
                file.copyTo(
                    output
                )

                getTaskInput()
                    .bindOutputFile(inputFile, output)

            } else {
                ZipFile(inputFile.toFile()).use { zipFile ->

                    zipFile.fileHeaders
                        .filterNot { it.isDirectory }
                        .filterNot {
                            it.fileName.endsWith("class") || skipResourceFileList.contains(
                                it.fileName
                            )
                        }
                        .forEach { header ->
                            zipFile.extractFile(header, defaultJavaResourceDirectory.path)
                            getTaskInput()
                                .bindOutputFile(inputFile, File(outputDirectory, header.fileName))
                        }
                }
            }
        }


        getTaskInput().snapshot()


    }


}