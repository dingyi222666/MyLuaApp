package com.dingyi.myluaapp.build.modules.public.compiler


import com.dingyi.myluaapp.build.CompileError
import com.dingyi.myluaapp.build.api.logger.ILogger
import com.dingyi.myluaapp.common.kts.Paths

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.eclipse.jdt.internal.compiler.tool.EclipseCompiler
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintWriter
import java.nio.charset.Charset
import java.util.*
import javax.tools.*

class JavaCompiler(
    val logger: ILogger
) {


    suspend fun compile(
        inputFiles: List<File>,
        classPaths: List<File>,
        option: Option,
        outputDir: File,
    ) = withContext(Dispatchers.IO) {



        val javaCompiler = EclipseCompiler()

        val diagnosticListener = DiagnosticListener<JavaFileObject> { diagnostic ->
            when (diagnostic.kind) {
                Diagnostic.Kind.ERROR -> {
                    logger.error(wrapDiagnostic(diagnostic))
                    throw CompileError("Java Compile Error: ${diagnostic.getMessage(Locale.getDefault())}")
                }
                Diagnostic.Kind.WARNING -> {
                    logger.warning(wrapDiagnostic(diagnostic))
                }
                Diagnostic.Kind.NOTE -> {
                    logger.debug(wrapDiagnostic(diagnostic))
                }
                else -> {
                    logger.info(wrapDiagnostic(diagnostic))
                }
            }
        }

        val standardJavaFileManager = javaCompiler.getStandardFileManager(
            diagnosticListener,
            Locale.getDefault(),
            Charset.defaultCharset()
        )


        standardJavaFileManager.setLocation(
            StandardLocation.CLASS_OUTPUT, listOf(outputDir)
        )

        standardJavaFileManager.setLocation(
            StandardLocation.SOURCE_PATH, classPaths
        )


        standardJavaFileManager.setLocation(
            StandardLocation.PLATFORM_CLASS_PATH,
            mutableListOf(
                File(Paths.buildPath, "jar/android.jar")
            ).apply {
                //default target version == java 8
                if ((option.findOption("target")?.toInt() ?: 8) >= 8) {
                    add(File(Paths.buildPath, "jar/core-lambda-stubs.jar"))
                }
            }
        )


        standardJavaFileManager.setLocation(
            StandardLocation.CLASS_PATH,
            classPaths
        )


        val errorStream = ByteArrayOutputStream()

        val task = javaCompiler.getTask(
            PrintWriter(errorStream),
            standardJavaFileManager, diagnosticListener,
            option.toList(),
            null,
            standardJavaFileManager.getJavaFileObjectsFromFiles(inputFiles)
        )


        val result = task.call()

        if (!result) {
            logger.error("e: ${errorStream.toByteArray().decodeToString()}")
            errorStream.close()
            throw CompileError("Compile Java Error!")
        }

        errorStream.close()

    }

    private fun wrapDiagnostic(diagnostic: Diagnostic<out JavaFileObject>): String {
        return "${diagnostic.source?.name}: (${diagnostic.lineNumber},${diagnostic.startPosition}): ${
            diagnostic.getMessage(
                Locale.getDefault()
            )
        }"
    }

    class Option {
        private val map = mutableMapOf<String, String>()

        fun addOption(key: String, value: String = "") {
            map[key] = value
        }

        fun findOption(key: String): String? {
            return map[key]
        }

        fun toList(): List<String> {
            val list = mutableListOf<String>()

            map.forEach { (t, u) ->
                list.add("-$t")
                list.add(u)
            }

            return list
        }
    }
}