package com.dingyi.myluaapp.openapi.fileTypes

import com.dingyi.myluaapp.openapi.application.ApplicationManager
import com.dingyi.myluaapp.openapi.service.get
import org.apache.commons.vfs2.FileObject
import java.util.function.Supplier

/**
 * A service for retrieving file types for files.
 *
 *
 * **Performance notice.** There are different rules of file type matching for a file: matching by file name, by extension,
 * by file content, by custom logic providers and so on. They are all executed by the general methods `getFileTypeByFile`,
 * thus implying that execution of
 * such methods is as long as the sum of all possible matching checks in the worst case. That includes reading file contents to
 * feed to all [FileTypeDetector] instances, checking [FileTypeIdentifiableByVirtualFile] and so on. Such actions
 * may lead to considerable slowdowns if called on UI thread, e.g. in
 * [com.intellij.openapi.vfs.newvfs.BulkFileListener] implementations.
 *
 *
 * If it is possible and correct to restrict file type matching by particular means (e.g. match only by file name),
 * it is advised to do so, in order to improve the performance of the check, e.g. use
 * `FileTypeRegistry.getInstance().getFileTypeByFileName(file.getNameSequence())` instead of `file.getFileType()`.
 * Otherwise, consider moving the computation into background, e.g. via [com.intellij.openapi.vfs.AsyncFileListener] or
 * [com.intellij.openapi.application.ReadAction.nonBlocking].
 */
abstract class FileTypeRegistry {
   /* abstract fun isFileIgnored(file: FileObject): Boolean
*/
    /**
     * Checks if the given file has the given file type.
     */
    fun isFileOfType(file: FileObject, type: FileType): Boolean {
        return file.type.getName() == type.getDefaultExtension()
    }


    /*fun findFileTypeByLanguage(@NotNull language: Language): LanguageFileType {
        return language.findMyFileType(registeredFileTypes)
    }*/

    /**
     * Returns the list of all registered file types.
     */
    abstract fun registeredFileTypes(): Array<FileType>

    /**
     * Returns the file type for the specified file.
     */

    abstract fun getFileTypeByFile(file: FileObject): FileType

    /**
     * Returns the file type for the specified file.
     *
     * @param content a content of the file (if already available, to avoid reading from disk again)
     */

    @Deprecated("Use #getFileTypeByFile(FileObject)", ReplaceWith("getFileTypeByFile(file)"))
    fun getFileTypeByFile(file: FileObject, content: ByteArray?): FileType {
        return getFileTypeByFile(file)
    }

    /**
     * Returns the file type for the specified file name, or [FileTypes.UNKNOWN] if not found.
     */

    fun getFileTypeByFileName(fileNameSeq: CharSequence): FileType {
        return getFileTypeByFileName(fileNameSeq.toString())
    }

    /**
     * Same as [FileTypeRegistry.getFileTypeByFileName] but receives String parameter.
     * Consider using the method above in case when you want to get VirtualFile's file type by file name.
     */

    abstract fun getFileTypeByFileName(fileName: String): FileType

    /**
     * Returns the file type for the specified extension.
     * Note that a more general way of obtaining file type is with [.getFileTypeByFile]
     *
     * @param extension The extension for which the file type is requested, not including the leading '.'.
     * @return The file type instance, or [UnknownFileType.INSTANCE] if corresponding file type not found
     */

    abstract fun getFileTypeByExtension(extension: String): FileType?

    /**
     * Finds a file type with the specified name.
     */
    abstract fun findFileTypeByName(fileTypeName: String): FileType?

    /**
     * Pluggable file type detector by content
     */

    companion object {
        private var instanceGetter: Supplier<out FileTypeRegistry>? = null


        fun setInstanceSupplier(supplier: Supplier<out FileTypeRegistry>): Supplier<out FileTypeRegistry>? {
            val oldValue= instanceGetter
            instanceGetter = supplier
            return oldValue
        }

        val isInstanceSupplierSet: Boolean
            get() = instanceGetter != null

        // in tests FileTypeManager service maybe not preloaded, so, ourInstanceGetter is not set
        val instance: FileTypeRegistry
            get() {
                val instanceGetter: Supplier<out FileTypeRegistry> = instanceGetter
                    ?: // in tests FileTypeManager service maybe not preloaded, so, ourInstanceGetter is not set
                    return ApplicationManager.getApplication()
                        .get()
                return instanceGetter.get()
            }

        const val extensionName = "com.intellij.fileType"
    }
}
