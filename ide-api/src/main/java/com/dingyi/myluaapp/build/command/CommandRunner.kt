package com.dingyi.myluaapp.build.command

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.lang.RuntimeException

class CommandRunner {


    private val env = mutableMapOf<String, String>()

    init {
        env.putAll(System.getenv())
    }

    private fun transformEnvToArray(): Array<String> {
        return env.map {
            it.key + "=" + it.value
        }.toTypedArray()
    }

    private suspend fun runCommand(command: String): Status = withContext(Dispatchers.IO) {

        val process = Runtime.getRuntime().exec(
            command, transformEnvToArray()
        )

        val status = runCatching {

            process.waitFor()

            val status = process.exitValue()


            Status(
                code = status,
                message = readMessage(process, status)
            )
        }.getOrElse {
            Status(
                code = -1,
                message = it.stackTraceToString()
            )
        }

        process.destroyForcibly().destroy()

        status
    }


    suspend fun runCommand(command: Array<String>): Status {
        return runCommand(command.joinToString(separator = " "))
    }

    suspend fun runCommand(executableFilePath: String, command: String): Status {
        val file = File(executableFilePath)

        if (file.isFile.not()) {
            throw FileNotFoundException("Not Found executable File!")
        }

        file.setExecutable(true)


        return runCommand(arrayOf(executableFilePath, command))

    }


    suspend fun runCommand(executableFilePath: String, command: Array<String>): Status {
        return runCommand(executableFilePath, command.joinToString(separator = " "))
    }

    private fun readMessage(process: Process, status: Int): String {
        return if (status == 0) {
            process.inputStream.use { inputStream ->
                inputStream.bufferedReader().use { it.readText() }
            }
        } else {
            process.errorStream.use { errorStream ->
                errorStream.bufferedReader().use { it.readText() }
            }
        }
    }


    fun getEnv(): Map<String, String> = env


    fun setEnv(key: String, value: String) {
        env[key] = value
    }

    fun clear() {
        env.clear()
    }


    data class Status(
        val code: Int,
        val message: String
    )
}