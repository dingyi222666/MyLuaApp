package com.dingyi.myluaapp.core.project


import com.dingyi.myluaapp.common.kts.LuaJVM
import com.dingyi.myluaapp.common.kts.toFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.OutputStream

/**
 * @author: dingyi
 * @date: 2021/10/12 20:07
 * @description: a project manager
 * @param projectRootPath root project path
 **/
class ProjectManager(private val projectRootPath: String) {


    var globalLuaJVM = LuaJVM()

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


    private fun listProject(block: (Project?) -> Unit) {

        projectRootPath.toFile()
            .walk()
            .maxDepth(1)
            .toList()
            .sortedByDescending {
                it.lastModified()
            }
            .forEach { file ->
                file.walk()
                    .maxDepth(1)
                    .toList()
                    //为了速度 就做一个简单的判断就行了
                    .filter {
                        "${it.absolutePath}/build.gradle.lua".toFile()
                            .exists() && "${it.absolutePath}/.MyLuaApp/.config.lua".toFile()
                            .exists()
                    }
                    .let {
                        if (it.size == 1) {
                            block(Project(file.absolutePath, this))
                        } else {
                            block(null)
                        }
                    }
            }

    }


    suspend fun getProjectList(): Pair<String?, List<Project>> {
        val result = mutableListOf<Project>()
        var errorFlag = false
        withContext(Dispatchers.IO) {
            listProject {
                if (it == null) {
                    errorFlag = true
                }
                it?.apply(result::add)
            }
        }
        return Pair(if (errorFlag) "Project Config File not found!" else null, result)

    }

    companion object {
        fun openProject(projectPath: String): Project {
            return Project(
                projectPath,
                ProjectManager(projectPath.toFile().parentFile?.absolutePath ?: "")
            )
        }
    }

    fun close() {
        globalLuaJVM.close()
    }


}