package com.dingyi.myluaapp.openapi.project

import com.dingyi.myluaapp.openapi.editor.DocumentEvent
import com.dingyi.myluaapp.openapi.service.ServiceRegistry
import kotlinx.coroutines.CoroutineScope
import org.jetbrains.annotations.NonNls
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable


interface Project {
    companion object {



        const val DIRECTORY_STORE_FOLDER = ".MyLuaApp"
    }


    /**
     * Returns a name ot the project. For a directory-based project it's an arbitrary string specified by user at project creation
     * or later in a project settings. For a file-based project it's a name of a project file without extension.
     *
     * @return project name
     */
    @NotNull
    fun getName(): String?



    /**
     * @return a path to project file (see [.getProjectFile]) or `null` for default project.
     */
    @Nullable

    @NonNls
    fun getProjectFilePath(): String?

    /**
     * Returns presentable project path:
     * [.getProjectFilePath] for file-based projects, [.getBasePath] for directory-based ones.<br></br>
     * Returns `null` for default project.
     * **Note:** the word "presentable" here implies file system presentation, not a UI one.
     */
    @Nullable

    @NonNls
    fun getPresentableUrl(): String? {
        return null
    }


    @NotNull
    @NonNls
    fun getLocationHash(): String?

    fun save()

    fun isOpen(): Boolean

    fun isInitialized(): Boolean

    fun isDefault(): Boolean {
        return false
    }

    fun getServiceRegistry(): ServiceRegistry


}