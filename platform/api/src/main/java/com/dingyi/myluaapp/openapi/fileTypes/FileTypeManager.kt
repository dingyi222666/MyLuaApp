package com.dingyi.myluaapp.openapi.fileTypes

import com.dingyi.myluaapp.openapi.application.ApplicationManager
import com.dingyi.myluaapp.openapi.project.Project
import com.dingyi.myluaapp.openapi.service.get
import org.apache.commons.vfs2.FileObject


/**
 * Manages the relationship between filenames and [FileType] instances.
 */
abstract class FileTypeManager : FileTypeRegistry() {


    abstract fun registerFileType(
        type: FileType,
        vararg defaultAssociatedExtensions: String?
    )

    /**
     * Checks if the specified file is ignored by the IDE. Ignored files are not visible in
     * different project views and cannot be opened in the editor. They will neither be parsed nor compiled.
     *
     * @param name The name of the file to check.
     * @return `true` if the file is ignored, `false` otherwise.
     */
    abstract fun isFileIgnored(name: String): Boolean

    @Deprecated("obsolete - file type associations aren't limited to mere extensions list (see {@link #getAssociations}) ")
    abstract fun getAssociatedExtensions(type: FileType): Array<String>


    abstract fun getAssociations(type: FileType): List<FileNameMatcher>


    abstract fun getKnownFileTypeOrAssociate(
        file: FileObject,
        project: Project
    ): FileType


    /**
     * Returns the semicolon-delimited list of patterns for files and folders
     * which are excluded from the project structure though they may be present
     * physically on disk.
     *
     * @return Semicolon-delimited list of patterns.
     */
    /**
     * Sets new list of semicolon-delimited patterns for files and folders which
     * are excluded from the project structure.
     *
     * @param list List of semicolon-delimited patterns.
     */
    abstract fun getIgnoredFilesList(): String


    /**
     * Adds an extension to the list of extensions associated with a file type.
     *
     * @param type      the file type to associate the extension with.
     * @param extension the extension to associate.
     */
    fun associateExtension(type: FileType, extension: String) {
        associate(type, ExtensionFileNameMatcher(extension))
    }

    fun associatePattern(type: FileType, pattern: String) {
        associate(type, parseFromString(pattern))
    }

    abstract fun associate(
        type: FileType,
        matcher: FileNameMatcher
    )

    /**
     * Removes an extension from the list of extensions associated with a file type.
     *
     * @param type      the file type to remove the extension from.
     * @param extension the extension to remove.
     */
    fun removeAssociatedExtension(type: FileType, extension: String) {
        removeAssociation(type, ExtensionFileNameMatcher(extension))
    }

    abstract fun removeAssociation(
        type: FileType,
        matcher: FileNameMatcher?
    )


    abstract fun getStdFileType(fileTypeName: String): FileType

    companion object {
        init {
            setInstanceSupplier { instance!! }
        }

        private lateinit var ourInstance: FileTypeManager


        /**
         * Returns the singleton instance of the FileTypeManager component.
         */
        val instance: FileTypeManager
            get() {

                if (this::ourInstance::isInitialized.get().not()) {
                    val instance = ApplicationManager.getIDEApplication()
                        .get<FileTypeManager>() // else MockFileTypeManager()
                    ourInstance = instance
                }
                return instance
            }


        fun parseFromString(pattern: String): FileNameMatcher {
            return FileNameMatcherFactory.instance.createMatcher(pattern)
        }
    }
}