package com.dingyi.myluaapp.build.modules.android.symbol

import java.io.File
import java.io.OutputStreamWriter

class SymbolWriter(
    private val packageName: String
) {

    fun write(loader: SymbolLoader, path: File) {


        val file = File(path, packageName.replace(".", "/") + "/R.java")


        file.parentFile?.mkdirs()

        file.createNewFile()

        file.writer()
            .use {

                it.write(
                    """
/* AUTO-GENERATED FILE. DO NOT MODIFY. 
 *
 * This class was automatically generated by the
 * aapt tool from the resource data it found.  It
 * should not be modified by hand.
 */""".trimIndent()
                )
                it.write("\n\n")

                it.write("package ${packageName};")
                it.write("\n\n")
                it.write("public final class R {")
                it.write("\n")

                writeClassContent(it, loader)

                it.write("}")
            }
    }

    private fun writeClassContent(writer: OutputStreamWriter, loader: SymbolLoader) {
        loader
            .getSymbols()
            .groupBy {
                it.innerClass
            }.forEach { (k, v) ->
                writeInnerClass(writer, k, v)
                writer.write("\n")
            }
    }

    private fun writeInnerClass(
        writer: OutputStreamWriter,
        innerClass: String,
        symbolList: List<SymbolLoader.Symbol>
    ) {
        writer.write("    ") // 4 spaces
        writer.write("public static class $innerClass {")
        writer.write("\n")
        symbolList.forEach {
            writeSymbol(writer, it)

        }
        writer.write("\n")
        writer.write("    ") // 3 spaces
        writer.write("}")

    }


    private fun writeSymbol(writer: OutputStreamWriter, symbol: SymbolLoader.Symbol) {

        writer.write("        ") //  8 spaces
        writer.write("public static final ")
        writer.write(symbol.type)
        writer.write(" ")
        writer.write(symbol.name)
        writer.write(" = ")
        writer.write(symbol.value)
        writer.write(";")
        writer.write("\n")
    }

}