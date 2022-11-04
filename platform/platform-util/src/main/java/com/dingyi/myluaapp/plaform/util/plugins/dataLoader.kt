package com.dingyi.myluaapp.plaform.util.plugins

import java.io.IOException
import java.nio.file.Files
import java.nio.file.NoSuchFileException
import java.nio.file.Path



interface DataLoader {

    fun isExcludedFromSubSearch(jarFile: Path): Boolean = false

    @Throws(IOException::class)
    fun load(path: String): ByteArray?

    override fun toString(): String
}


class LocalFsDataLoader(private val basePath: Path) : DataLoader {

    override fun load(path: String): ByteArray? {
        return try {
            Files.readAllBytes(basePath.resolve(path))
        }
        catch (e: NoSuchFileException) {
            null
        }
    }

    override fun toString() = basePath.toString()
}