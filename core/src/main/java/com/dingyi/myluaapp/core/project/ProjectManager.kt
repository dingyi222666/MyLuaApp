package com.dingyi.myluaapp.core.project

import java.io.FileOutputStream
import java.io.OutputStream

/**
 * @author: dingyi
 * @date: 2021/10/12 20:07
 * @description: a project manager
 **/
/**
 * @param projectRootPath root project path
 */
class ProjectManager(private val projectRootPath:String) {




    /**
     * delete project
     */
    fun deleteProject(project:Project) {
        
    }

    /**
     * backup project
     */
    fun backupProject(project:Project,exportOutputStream: OutputStream) {}

    /**
     * create project
     */
    fun createProject(projectBuilder:Project.Builder) {}

    fun listProject(block:(Project)->Unit) {

    }



}