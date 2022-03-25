package com.dingyi.myluaapp.build.modules.public.compiler

import com.dingyi.myluaapp.build.CompileError
import com.dingyi.myluaapp.build.api.logger.ILogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import org.jetbrains.kotlin.cli.jvm.K2JVMCompiler
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.io.PrintWriter

class KotlinCompiler(
    val logger: ILogger
) {

    suspend fun compile(
        inputFiles: List<File>,
        classPaths: List<File>,
        option: Array<String>,
        outputDir: File,
    ) = withContext(Dispatchers.IO) {

        val errorStream = ByteArrayOutputStream()

        val compiler = K2JVMCompiler()

        /**
        val argument = compiler
        .createArguments()

        argument.apply {

        noStdlib = true
        noJdk = true

        useJavac = false

        useFastJarFileSystem = false

        includeRuntime = false

        compileJava = true

        argument
        .

        }
         */


        val exitCode = compiler.exec(
            PrintStream(errorStream),
            *option,
            *inputFiles.map { it.absolutePath }.toTypedArray(),
            "-d",
            outputDir.absolutePath,
            "-cp",
            classPaths.joinToString(File.pathSeparator) { it.absolutePath }
        )

        if (exitCode.code != 0) {
            logger.error("e: ${errorStream.toByteArray().decodeToString()}")
            errorStream.close()
            throw CompileError("Compile Kotlin Error!")
        }
    }
}