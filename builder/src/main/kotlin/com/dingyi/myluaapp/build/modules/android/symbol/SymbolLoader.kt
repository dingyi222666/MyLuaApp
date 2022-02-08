package com.dingyi.myluaapp.build.modules.android.symbol

import com.dingyi.myluaapp.common.kts.println
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeSpec
import java.io.File
import java.lang.reflect.Type
import javax.lang.model.type.TypeMirror

class SymbolLoader(
    private val loadFile: File
) {

    private val symbols = mutableListOf<Symbol>()

    private fun String.splitSymbol(): List<String> {
        val pos = indexOf(' ')
        val type = substring(0, pos)
        val pos2 = indexOf(' ', pos + 1)
        val className = substring(pos + 1, pos2)
        val pos3 = indexOf(' ', pos2 + 1)
        val name = substring(pos2 + 1, pos3)
        val value = substring(pos3 + 1)

        return listOf(type, className, name, value)
    }

    fun load(): SymbolLoader {

        loadFile.useLines { lines ->
            lines.forEach {
                val splitArray = it.splitSymbol()

                val symbol = if (defaultSymbolTypeMap[splitArray[0]] == null) {
                    readDefaultSymbol(splitArray)
                } else {
                    readSymbol(splitArray)
                }
                symbols.add(symbol)
            }
        }

        return this
    }

    private fun readSymbol(splitArray: List<String>): Symbol {
        return Symbol(
            type = splitArray[0],
            innerClass = splitArray[1],
            name = splitArray[2].replace(".", "_"),
            value = splitArray[3]
        )
    }

    private val defaultSymbolTypeMap = mapOf(
        "int" to "0",
        "string" to "",
        "int[]" to "{}"
    )

    private fun readDefaultSymbol(splitArray: List<String>): Symbol {
        return Symbol(
            type = splitArray[1],
            innerClass = splitArray[2],
            name = splitArray[3].replace(".", "_"),
            value = defaultSymbolTypeMap[splitArray[1]]
        )
    }

    fun merge(path: File) {
        merge(load(path))
    }

    private fun merge(loader: SymbolLoader) {
        loader
            .symbols
            .forEach { symbol ->
                val findSymbol = symbols
                    .find { it == symbol }

                findSymbol?.value = symbol.value
            }
    }

    fun getSymbols(): List<Symbol> {
        return symbols
    }

    companion object {
        fun load(file: File): SymbolLoader {
            return SymbolLoader(file).load()
        }
    }

    data class Symbol(
        val type: String,
        val innerClass: String,
        var value: String?,
        val name: String,
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Symbol

            if (type != other.type) return false
            if (innerClass != other.innerClass) return false
            if (name != other.name) return false

            return true
        }

        override fun hashCode(): Int {
            var result = type.hashCode()
            result = 31 * result + innerClass.hashCode()
            result = 31 * result + name.hashCode()
            return result
        }
    }
}