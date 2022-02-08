package com.dingyi.myluaapp.build.modules.android.tasks

import com.dingyi.myluaapp.build.CompileError
import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.default.DefaultTask
import com.dingyi.myluaapp.build.util.getSHA256
import com.dingyi.myluaapp.common.kts.*
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.lingala.zip4j.ZipFile
import net.lingala.zip4j.model.FileHeader
import java.io.File


class MergeLibraryJavaResources(
    private val module: Module
) : DefaultTask(module) {

    override val name: String
        get() = "MergeLibraryJavaResources"

    private val unpackingJavaResourceFiles = mutableListOf<File>()

    private val unpackingJarResourceFiles = mutableMapOf<ZipFile, List<FileHeader>>()

    private val hashFile = "build/tmp/merge_java_resources/hash.json"

    private val defaultJavaResourceDirectory = "src/main/resources"

    private val skipResourceFileList = arrayOf("META-INF/MANIFEST.MF")

    private val outputDirectory = "build/intermediates/merged_resources"

    override suspend fun prepare(): Task.State {


        val allJavaResources = module
            .getFileManager()
            .resolveFile(defaultJavaResourceDirectory, module)
            .walkBottomUp()
            .filter {
                it.isFile
            }.toList()

        val allJarResources = module
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
            }
            .map { file ->
                val zipFile = ZipFile(file)
                zipFile to zipFile.fileHeaders
                    .filterNot {
                        it.fileName.endsWith("class") || it.isDirectory || skipResourceFileList.contains(
                            it.fileName
                        )
                    }
            }
            .toMap()

        if (allJavaResources.isEmpty() && allJarResources.values.none { it.isNotEmpty() }) {
            return Task.State.SKIPPED
        }

        val outputDirectory = module.getFileManager().resolveFile(outputDirectory, module)

        val defaultJavaResourceDirectory = module
            .getFileManager()
            .resolveFile(defaultJavaResourceDirectory, module)


        val incrementalJavaResources = allJavaResources
            .filter {
                val tmpFile = File(
                    outputDirectory,
                    it.path.substring(defaultJavaResourceDirectory.path.length + 1)
                )
                if (!tmpFile.isFile) true else it.getSHA256() != tmpFile.getSHA256()
            }

        val incrementalJarResources = allJarResources
            .map { (zipFile, zipList) ->
                zipFile to zipList.filter { fileHeader ->
                    val tmpFile = File(outputDirectory, fileHeader.fileName)

                    if (!tmpFile.isFile) true else zipFile.getInputStream(fileHeader)
                        .getSHA256() != tmpFile.getSHA256()
                }
            }
            .filter { (_, v) -> v.isNotEmpty() }
            .toMap()

        unpackingJavaResourceFiles.addAll(incrementalJavaResources)
        unpackingJarResourceFiles.putAll(incrementalJarResources)


        return when {
            incrementalJarResources.values.none { it.isNotEmpty() } && incrementalJavaResources.isEmpty() -> Task.State.`UP-TO-DATE`

            incrementalJarResources.values.size < allJarResources.values.size ||
                    incrementalJavaResources.size < allJavaResources.size -> Task.State.INCREMENT
            else -> Task.State.DEFAULT
        }
    }

    override suspend fun run() = withContext(Dispatchers.IO) {

        launch {
            val outputDirectory = module.getFileManager().resolveFile(outputDirectory, module)

            val defaultJavaResourceDirectory = module
                .getFileManager()
                .resolveFile(defaultJavaResourceDirectory, module)

            unpackingJavaResourceFiles
                .forEach {
                    launch {
                        it.copyTo(
                            File(
                                outputDirectory,
                                it.path.substring(defaultJavaResourceDirectory.path.length + 1)
                            )
                        )
                    }
                }

            unpackingJarResourceFiles.forEach { (zipFile, zipList) ->
                launch {
                    zipList.forEach { fileHeader ->
                        val inputStream = zipFile.getInputStream(fileHeader)

                        val outputFile = File(outputDirectory, fileHeader.fileName)

                        outputFile.parentFile?.mkdirs()

                        outputFile.createNewFile()

                        outputFile.outputStream().use {
                            inputStream.copyTo(it)
                        }

                    }
                }
            }

        }.join()
    }


}