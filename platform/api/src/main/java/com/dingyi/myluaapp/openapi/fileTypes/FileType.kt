package com.dingyi.myluaapp.openapi.fileTypes

import android.graphics.drawable.Drawable
import com.intellij.openapi.util.NlsSafe
import org.jetbrains.annotations.NonNls


interface FileType {

    companion object {
        var EMPTY_ARRAY = arrayOfNulls<FileType>(0)
    }

    /**
     * Returns the name of the file type. The name must be unique among all file types registered in the system.
     */
    @NonNls

    fun getName(): String

    /**
     * Returns the user-readable description of the file type.
     */

    fun getDescription(): String

    /**
     * Returns the default extension for files of the type, *not* including the leading '.'.
     */

    fun getDefaultExtension(): @NlsSafe String

    /**
     * Returns the icon used for showing files of the type, or `null` if no icon should be shown.
     */
    fun getIcon(): Drawable

    /**
     * Returns `true` if files of the specified type contain binary data, `false` if the file is plain text.
     * Used for source control, to-do items scanning and other purposes.
     */
    fun isBinary(): Boolean

    /**
     * Returns `true` if the specified file type is read-only. Read-only file types are not shown in the "File Types" settings dialog,
     * and users cannot change the extensions associated with the file type.
     */
    fun isReadOnly(): Boolean {
        return false
    }


}