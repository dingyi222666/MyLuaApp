package com.dingyi.myluaapp.build.default


import com.dingyi.myluaapp.build.api.file.InputFile

import com.dingyi.myluaapp.build.api.file.TaskInput
import com.dingyi.myluaapp.common.kts.getJavaClass
import com.dingyi.myluaapp.common.kts.println
import com.dingyi.myluaapp.common.kts.toFile
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import java.io.File

class DefaultTaskInput<T : DefaultTask>(private val task: T) : TaskInput {

    private val inputSnapShotFile =
        "build/cache/${task.javaClass.simpleName.lowercase()}-task-input-snapshot.json"

    private val outputSnapShotFile =
        "build/cache/${task.javaClass.simpleName.lowercase()}-task-output-snapshot.json"

    private val inputMappingOutputFile =
        "build/cache/${task.javaClass.simpleName.lowercase()}-task-mapping.json"


    private val gson = Gson()


    private val inputSnapShotMap = mutableMapOf<String, String>()

    private val outputSnapShotMap = mutableMapOf<String, String>()

    private val inputMappingOutputMap = mutableMapOf<String, String>()

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
        return inputFileList.find { it.getPath().path == file.path } ?: DefaultInputFile(
            this,
            file,
            file.parentFile.path
        )
    }

    override fun getInputFileFormDirectory(file: File): List<InputFile> {
        val path = file.path
        return inputFileList.filter {
            it.getPath().path.substring(path.length) == path
        }
    }

    override fun transformDirectoryToFile(block: (File) -> Boolean) {
        inputFileDirectory.forEach { directory ->
            directory.walkBottomUp()
                .filter { it.isFile }
                .filter(block)
                .forEach {
                    inputFileList.add(
                        DefaultInputFile(this, it, directory.path)
                    )
                }
        }
    }

    override fun bindOutputFile(inputFile: InputFile, outputFile: File) {
        inputMappingOutputMap[inputFile.getPath().path] = outputFile.path
    }

    override fun bindOutputDirectory(
        inputDirectory: File,
        outputDirectory: File,
        match: (File, File) -> Boolean
    ) {
        val inputFileList = inputDirectory.walkBottomUp()
            .filter { it.isFile }
            .toList()

        val outputFileList = outputDirectory.walkBottomUp()
            .filter { it.isFile }
            .toList()

        for (input in inputFileList) {
            for (output in outputFileList) {
                if (match(input, output)) {
                    inputMappingOutputMap[input.path] = output.path
                    break
                }
            }
        }


    }

    override suspend fun isIncremental(): Boolean = withContext(Dispatchers.IO) {

        readConfig()

        inputMappingOutputMap.forEach { (t, u) ->
            val (file1, file2) = t.toFile() to u.toFile()
            if (!file1.exists() || getSnapShotHash(file1) != file1.lastModified().toString()) {
                file2.deleteRecursively()
            }
        }

        val incrementalInputFile = getIncrementalInputFile()


         incrementalInputFile
            .size < inputFileList.size
    }

    override fun getIncrementalInputFile(): List<InputFile> {
        return inputFileList.filter {

            println(inputMappingOutputMap,it,it.getBindOutputFile()?.path,"test")

            val outputFile = it.getBindOutputFile()
            it.getFileHash() != it.getSnapShotHash() &&
                    (outputFile?.isFile ?: false) && (outputFile?.lastModified() ?: "00")
                .toString() != outputFile?.let { it1 -> getSnapShotHash(it1) }

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
            val (file1, file2) = t.toFile() to u.toFile()
            inputSnapShotMap[t] = file1.lastModified().toString()
            outputSnapShotMap[u] = file2.lastModified().toString()
        }

        inputSnapShotFile.writeText(gson.toJson(inputSnapShotMap))
        outputSnapShotFile.writeText(gson.toJson(outputSnapShotMap))
        inputMappingOutputFile.writeText(gson.toJson(inputMappingOutputMap))

    }

    fun getSnapShotHash(path: File): String {

        if (!path.exists()) {
            return "0"
        }

        println(path,"hash",inputSnapShotMap,outputSnapShotMap)

        return inputSnapShotMap[path.path] ?: outputSnapShotMap[path.path] ?: "0"
    }

    fun getBindOutputFile(defaultInputFile: DefaultInputFile): File? {
        return defaultInputFile
            .getPath().let {
                inputMappingOutputMap[it.path]?.toFile()
            }

    }

    override fun toString(): String {
        return "DefaultTaskInput(task=$task, inputSnapShotFile='$inputSnapShotFile', outputSnapShotFile='$outputSnapShotFile', inputMappingOutputFile='$inputMappingOutputFile', inputSnapShotMap=$inputSnapShotMap, outputSnapShotMap=$outputSnapShotMap, inputMappingOutputMap=$inputMappingOutputMap, inputFileList=$inputFileList, inputFileDirectory=$inputFileDirectory, outputFileDirectory=$outputFileDirectory)"
    }


}