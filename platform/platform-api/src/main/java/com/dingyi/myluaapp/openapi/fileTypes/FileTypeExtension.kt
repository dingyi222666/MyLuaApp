package com.dingyi.myluaapp.openapi.fileTypes


import com.dingyi.myluaapp.openapi.extensions.ExtensionPointName
import com.dingyi.myluaapp.openapi.util.KeyedExtensionCollector
import com.dingyi.myluaapp.util.KeyedLazyInstance


class FileTypeExtension<T : Any> : KeyedExtensionCollector<T, FileType> {
    constructor(epName: String) : super(epName) {}
    constructor(epName: ExtensionPointName<KeyedLazyInstance<T>>) : super(epName) {}

    override fun keyToString(key: FileType): String {
        return key.getName()
    }

    fun allForFileType(t: FileType): List<T> {
        return forKey(t)
    }

    fun forFileType(t: FileType): T? {
        val all = allForFileType(t)
        return if (all.isEmpty()) null else all[0]
    }

    val allRegisteredExtensions: Map<FileType, T>
        get() {
            val extensions: List<KeyedLazyInstance<T>> = extensions
            val result: MutableMap<FileType, T> = HashMap()
            for (extension in extensions) {
                val fileType = FileTypeRegistry.instance.findFileTypeByName(extension.key)
                if (fileType != null) {
                    result[fileType] = extension.getInstance()
                }
            }
            return result
        }
}