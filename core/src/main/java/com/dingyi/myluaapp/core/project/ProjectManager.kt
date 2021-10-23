package com.dingyi.myluaapp.core.project

import com.dingyi.myluaapp.common.kts.toFile
import java.io.OutputStream

/**
 * @author: dingyi
 * @date: 2021/10/12 20:07
 * @description: a project manager
 **/
/**
 * @param projectRootPath root project path
 */
class ProjectManager(private val projectRootPath: String) {


    /**
     * delete project
     */
    fun deleteProject(project: Project) {
        project.delete()
    }

    /**
     * backup project
     */
    fun backupProject(project: Project, exportOutputStream: OutputStream) {
        project.backup(exportOutputStream)
    }

    /**
     * create project
     */
    fun createProject(projectBuilder: Project.Builder) {

    }


    fun listProject(block: (Project?) -> Unit) {
        projectRootPath.toFile()
            .walk()
            .maxDepth(1)
            .toList()
            .forEach { file ->
                file.walk()
                    .maxDepth(1)
                    .toList()
                    .filter { it.name == "build.gradle.lua" }
                    .let {
                        if (it.size == 1) {
                            block(Project(file.absolutePath))
                        } else {
                            block(null)
                        }
                    }
            }

    }

    companion object {
        fun openProject(projectPath: String): Project {
            return Project(projectPath)
        }
    }


}