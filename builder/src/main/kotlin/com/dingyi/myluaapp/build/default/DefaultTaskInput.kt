package com.dingyi.myluaapp.build.default


import com.dingyi.myluaapp.build.api.file.InputFile

import com.dingyi.myluaapp.build.api.file.TaskInput
import com.dingyi.myluaapp.common.kts.getJavaClass
import com.dingyi.myluaapp.common.kts.toFile
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import java.io.File

class DefaultTaskInput<T : DefaultTask>(private val task: T) : TaskInput {

    private val inputSnapShotFile =
        "build/cache/${task.javaClass.simpleName}-InputSnapshot.json"

    private val outputSnapShotFile =
        "build/cache/${task.javaClass.simpleName}-OutputSnapshot.json"

    private val inputMappingOutputFile =
        "build/cache/${task.javaClass.simpleName}-Mapping.json"


    private val gson = Gson()


    private val inputSnapShotMap = mutableMapOf<String, String>()

    private val outputSnapShotMap = mutableMapOf<String, String>()

    private val inputMappingOutputMap = mutableMapOf<String, MutableList<String>>()

    private val inputFileList = mutableListOf<InputFile>()


    private val inputFileDirectory = mutableListOf<File>()

    private val outputFileDirectory = mutableListOf<File>()


    private fun readConfig() {

        val inputSnapShotFile = task.applyModule
            .getFileManager()
            .resolveFile(inputSnapShotFile, task.applyModule)

        if (inputSnapShotFile.isFile) {
            inputSnapShotFile.reader().use {
                inputSnapShotMap.putAll(
                    gson.fromJson(it, getJavaClass())
                )
            }
        }

        val outputSnapShotFile = task.applyModule
            .getFileManager()
            .resolveFile(outputSnapShotFile, task.applyModule)

        if (outputSnapShotFile.isFile) {
            outputSnapShotFile.reader().use {
                outputSnapShotMap.putAll(
                    gson.fromJson(it, getJavaClass())
                )
            }
        }

        val inputMappingOutputFile = task.applyModule
            .getFileManager()
            .resolveFile(inputMappingOutputFile, task.applyModule)

        if (inputMappingOutputFile.isFile) {
            inputMappingOutputFile.reader().use {
                inputMappingOutputMap.putAll(
                    gson.fromJson(it, getJavaClass())
                )
            }
        }

    }

    override fun addInputFile(file: File) {
        inputFileList.add(DefaultInputFile(this, file, file.parentFile.path))
    }

    override fun addInputDirectory(file: File) {
        inputFileDirectory.add(file)
    }

    override fun getAllInputFile(): List<InputFile> {
        return inputFileList
    }

    override fun getInputFile(file: File): InputFile {
        return inputFileList.find { it.toFile().path == file.path } ?: DefaultInputFile(
            this,
            file,
            file.parentFile.path
        )
    }

    override fun getInputFileFormDirectory(file: File): List<InputFile> {
        val path = file.path
        return inputFileList.filter {
            it.toFile().path.substring(path.length) == path
        }
    }

    override fun transformDirectoryToFile(block: (File) -> Boolean) {
        inputFileDirectory.forEach { directory ->
            directory.walkBottomUp()
                .filter(block)
                .forEach {
                    inputFileList.add(
                        DefaultInputFile(this, it, directory.path)
                    )
                }
        }
        inputFileDirectory.clear()
    }

    override fun transformDirectoryToFile() {
        inputFileDirectory.forEach { directory ->
            inputFileList.add(
                DefaultInputFile(this, directory, directory.path)
            )
        }
        inputFileDirectory.clear()
    }

    override fun bindOutputFile(inputFile: InputFile, outputFile: File) {
        inputMappingOutputMap.getOrDefault(
            inputFile.toFile().path,
            mutableListOf()
        ).apply {
            add(outputFile.path)
        }.let {
            inputMappingOutputMap[inputFile.toFile().path] = it
        }
    }

    override fun bindOutputFiles(inputFile: InputFile, outputFile: List<File>) {
        inputMappingOutputMap.getOrDefault(
            inputFile.toFile().path,
            mutableListOf()
        ).apply {
            addAll(outputFile.map { it.path })
        }.let {
            inputMappingOutputMap[inputFile.toFile().path] = it
        }
    }


    override suspend fun isIncremental(): Boolean = withContext(Dispatchers.IO) {

        readConfig()

        inputMappingOutputMap.forEach { (t, u) ->
            val file1 = t.toFile()
            if (!file1.exists() || getSnapShotHash(file1) != file1.lastModified().toString()) {
                u.forEach {
                    it.toFile().deleteRecursively()
                }
            }
        }

        val incrementalInputFile = getIncrementalInputFile()


         incrementalInputFile
            .size < inputFileList.size
    }

    override fun getIncrementalInputFile(): List<InputFile> {
        return inputFileList.filter { inputFile ->
            val outputFile = inputFile.getBindOutputFile() ?: return@filter true

            val all = outputFile.filterNot {
                it.exists().not() || it.lastModified().toString() != getSnapShotHash(it)
            }

            inputFile.getFileHash() != inputFile.getSnapShotHash() ||
                    all.isEmpty()

        }
    }


    override fun addOutputDirectory(file: File) {
        outputFileDirectory.add(file)
    }

    override fun getOutputDirectory(): List<File> {
        return outputFileDirectory
    }

    override fun snapshot() {
        val inputSnapShotFile = task.applyModule
            .getFileManager()
            .resolveFile(inputSnapShotFile, task.applyModule)

        inputSnapShotFile.parentFile?.mkdirs()
        inputSnapShotFile.createNewFile()

        val outputSnapShotFile = task.applyModule
            .getFileManager()
            .resolveFile(outputSnapShotFile, task.applyModule)

        outputSnapShotFile.parentFile?.mkdirs()
        outputSnapShotFile.createNewFile()


        val inputMappingOutputFile = task.applyModule
            .getFileManager()
            .resolveFile(inputMappingOutputFile, task.applyModule)

        inputMappingOutputFile.parentFile?.mkdirs()
        inputMappingOutputFile.createNewFile()


        inputMappingOutputMap.forEach { (t, u) ->
            val file1 = t.toFile()
            inputSnapShotMap[t] = file1.lastModified().toString()
            u.forEach {
                val file2 = it.toFile()
                outputSnapShotMap[it] = file2.lastModified().toString()
            }
        }

        inputSnapShotFile.writeText(gson.toJson(inputSnapShotMap))
        outputSnapShotFile.writeText(gson.toJson(outputSnapShotMap))
        inputMappingOutputFile.writeText(gson.toJson(inputMappingOutputMap))

        inputMappingOutputMap.clear()
        inputFileDirectory.clear()
        inputSnapShotMap.clear()
        outputSnapShotMap.clear()

    }

    fun getSnapShotHash(path: File): String {

        if (!path.exists()) {
            return "0"
        }

        return inputSnapShotMap[path.path] ?: outputSnapShotMap[path.path] ?: "0"
    }

    fun getBindOutputFile(defaultInputFile: DefaultInputFile): List<File>? {
        return defaultInputFile
            .toFile().let {
                inputMappingOutputMap[it.path]?.map { it.toFile() }
            }

    }

    override fun toString(): String {
        return "DefaultTaskInput(task=$task, inputSnapShotFile='$inputSnapShotFile', outputSnapShotFile='$outputSnapShotFile', inputMappingOutputFile='$inputMappingOutputFile', inputSnapShotMap=$inputSnapShotMap, outputSnapShotMap=$outputSnapShotMap, inputMappingOutputMap=$inputMappingOutputMap, inputFileList=$inputFileList, inputFileDirectory=$inputFileDirectory, outputFileDirectory=$outputFileDirectory)"
    }


}