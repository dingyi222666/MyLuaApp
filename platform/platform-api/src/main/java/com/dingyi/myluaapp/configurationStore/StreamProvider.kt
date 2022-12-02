package com.dingyi.myluaapp.configurationStore


import com.intellij.openapi.util.io.BufferExposingByteArrayOutputStream
import java.io.InputStream

interface StreamProvider {
    /**
     * Whether is enabled.
     */
    val enabled: Boolean
        get() = true

    /**
     * Whether is exclusive and cannot be used alongside another provider.
     *
     * Doesn't imply [enabled], callers should check [enabled] also if need.
     */
    val isExclusive: Boolean


    /**
     * Called only on `write`
     */
    fun isApplicable(fileSpec: String): Boolean = true

    /**
     * @param fileSpec
     * @param content bytes of content, size of array is not actual size of data, you must use `size`
     * @param size actual size of data
     */
    fun write(fileSpec: String, content: ByteArray, size: Int = content.size)

    fun write(path: String, content: BufferExposingByteArrayOutputStream): Unit = write(path, content.internalBuffer, content.size())

    /**
     * `true` if provider is applicable for file.
     */
    fun read(fileSpec: String, consumer: (InputStream?) -> Unit): Boolean

    /**
     * `true` if provider is fully responsible and local sources must be not used.
     */
    fun processChildren(path: String, filter: (name: String) -> Boolean, processor: (name: String, input: InputStream, readOnly: Boolean) -> Boolean): Boolean

    /**
     * Delete file or directory
     *
     * `true` if provider is fully responsible and local sources must be not used.
     */
    fun delete(fileSpec: String): Boolean
}
