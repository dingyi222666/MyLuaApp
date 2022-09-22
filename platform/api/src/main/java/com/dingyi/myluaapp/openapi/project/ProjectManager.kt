package com.dingyi.myluaapp.openapi.project

import com.dingyi.myluaapp.common.ktx.getJavaClass
import com.dingyi.myluaapp.openapi.application.ApplicationManager
import com.dingyi.myluaapp.openapi.service.find


import java.io.IOException


/**
 * Provides project management.
 */
abstract class ProjectManager {

    /**
     * Returns the list of currently opened projects.
     * [Project.isDisposed] must be checked for each project before use (if the whole operation is not under read action).
     */
    abstract fun getOpenProjects():Array<Project>

    /**
     * Returns the project which is used as a template for new projects. The template project
     * is always available, even when no other project is open. This [Project] instance is not
     * supposed to be used for anything except template settings storage.
     *
     *
     *
     * NB: default project can be lazy loaded
     *
     * @return the template project instance.
     */
    abstract fun getDefaultProject():Project

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


    /**
     * Asynchronously reloads the specified project.
     *
     * @param project the project to reload.
     */
    abstract fun reloadProject(project: Project)


    fun findOpenProjectByHash(locationHash: String): Project? {
        return null
    }

    companion object {


        /**
         * @return `ProjectManager` instance
         */
        val instance: ProjectManager
            get() = ApplicationManager.getIDEApplication().find<ProjectManager>()


    }
}