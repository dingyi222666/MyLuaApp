package com.dingyi.myluaapp.build.modules.android.compiler

import com.dingyi.myluaapp.MainApplication
import com.dingyi.myluaapp.build.CompileError
import com.dingyi.myluaapp.build.command.CommandRunner
import com.dingyi.myluaapp.common.kts.Paths
import com.dingyi.myluaapp.common.kts.toFile

class AAPT2Compiler {


    private val commandRunner = CommandRunner()

    private val aapt2Path = "${Paths.nativeLibraryDir}/libaapt2.so"


    suspend fun compile(
        inputFiles: List<String>,
        outputDirectory: String,
        otherCommand: Array<String>? = null
    ) {

        inputFiles.forEach { compileFile ->
            val status = commandRunner.runCommand(
                aapt2Path,
                arrayOf(
                    "compile",
                    //compile File
                    compileFile,
                    otherCommand?.joinToString(" ") ?: "",
                    "-o", outputDirectory
                )
            )

            if (status.code!=0) {
                throw CompileError(status.message)
            }
        }
    }


    fun close() {
        commandRunner.clear()
    }
}