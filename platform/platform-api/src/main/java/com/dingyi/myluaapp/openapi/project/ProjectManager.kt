package com.dingyi.myluaapp.openapi.project


import com.dingyi.myluaapp.openapi.application.ApplicationManager
import com.dingyi.myluaapp.openapi.components.ComponentManager
import com.dingyi.myluaapp.openapi.components.getService
import java.io.IOException


/**
 * Provides project management.
 */
abstract class ProjectManager {

    /**
     * Loads and opens a project with the specified path. If the project file is from an older IDEA
     * version, prompts the user to convert it to the latest version. If the project file is from a
     * newer version, shows a message box telling the user that the load failed.
     *
     * @param filePath the .ipr file path
     * @return the opened project file, or null if the project failed to load because of version mismatch
     * or because the project is already open.
     * @throws IOException          if the project file was not found or failed to read
     * @throws JDOMException        if the project file contained invalid XML
     */

    @Throws(IOException::class)
    abstract fun loadAndOpenProject(filePath: String): Project?

    /**
     * Save, close and dispose project. Please note that only the project will be saved, but not the application.
     * @return true on success
     */
    abstract fun closeAndDispose(project: Project): Boolean

/*    *//**
     * Asynchronously reloads the specified project.
     *
     * @param project the project to reload.
     *//*
    abstract fun reloadProject(project: Project)*/


    companion object {

        /**
         * @return `ProjectManager` instance
         */
        val instance: ProjectManager
            get() = ApplicationManager.getApplication().getService()


    }
}