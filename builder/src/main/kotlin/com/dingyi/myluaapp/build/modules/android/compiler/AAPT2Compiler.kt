package com.dingyi.myluaapp.build.modules.android.compiler

import com.dingyi.myluaapp.MainApplication
import com.dingyi.myluaapp.build.BuildConfig
import com.dingyi.myluaapp.build.CompileError
import com.dingyi.myluaapp.build.api.logger.ILogger
import com.dingyi.myluaapp.build.command.CommandRunner
import com.dingyi.myluaapp.common.kts.Paths
import com.dingyi.myluaapp.common.kts.toFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AAPT2Compiler(private val logger: ILogger) {


    private val commandRunner = CommandRunner()

    private val aapt2Path = "${Paths.nativeLibraryDir}/libaapt2.so"


    suspend fun compile(
        inputFiles: List<String>? = null,
        outputDirectory: String,
        otherCommand: Array<String>? = null
    ) = withContext(Dispatchers.IO) {

        launch {

            if (inputFiles == null) {
                otherCommand?.let {
                    doCompile(
                        it,
                        outputDirectory
                    )
                }
            } else {
                inputFiles.forEach { compileFile ->
                    launch {
                        otherCommand?.let {
                            doCompile(
                                mutableListOf(compileFile)
                                    .apply { addAll(it) }.toTypedArray(),
                                outputDirectory
                            )
                        }
                    }
                }
            }

        }.join()

    }

    private suspend fun doCompile(otherCommand: Array<String>?, outputDirectory: String) {
        println("$otherCommand $outputDirectory")
        val status = commandRunner.runCommand(
            aapt2Path,
            arrayOf(
                "compile",
                otherCommand?.joinToString(" ") ?: "",
                "-o", outputDirectory
            )
        )



        if (BuildConfig.DEBUG) {
            logger.info("\n")
            logger.info("AAPT2 Compile Message:${status.message}")
            logger.info("\n")
            //logger.info("Source File:${compileFile}")
        }

        if (status.code != 0) {
            throw CompileError(status.message)
        }
    }

    fun close() {
        commandRunner.clear()
    }
}