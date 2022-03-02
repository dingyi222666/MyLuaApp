package com.dingyi.myluaapp.core.project


import com.dingyi.myluaapp.common.ktx.LuaJVM
import com.dingyi.myluaapp.common.ktx.toFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.OutputStream

/**
 * @author: dingyi
 * @date: 2021/10/12 20:07
 * @description: a project manager
 * @param projectRootPath root project path
 **/
@Deprecated("Now Use Plugin Service to manager project")
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


    private fun listProject(block: (Project?) -> Unit) {

        projectRootPath.toFile()
            .walk()
            .maxDepth(1)
            .toList()
            .drop(1)
            .sortedByDescending {
                it.lastModified()
            }
            .filter {
                "${it.absolutePath}/build.gradle.lua".toFile()
                    .exists() && "${it.absolutePath}/.MyLuaApp/.config.json".toFile()
                    .exists()
            }
            .forEach { file ->
                block(Project(file.absolutePath, this))
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