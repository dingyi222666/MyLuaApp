package com.dingyi.myluaapp.plugin.runtime.project

import com.dingyi.myluaapp.build.api.service.Service
import com.dingyi.myluaapp.editor.lsp.LSPProject
import com.dingyi.myluaapp.plugin.api.context.PluginContext
import com.dingyi.myluaapp.plugin.api.project.Project
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.util.zip.ZipFile

abstract class DefaultProject(
    private val pluginContext: PluginContext
) : Project {

    override suspend fun deleteFile(targetFile: File): Unit = withContext(Dispatchers.IO) {
        if (targetFile.isFile) {
            pluginContext
                .getEditorService()
                .closeEditor(targetFile)
        } else {
            targetFile
                .walkBottomUp()
                .filter { it.isFile }
                .filter { file ->
                    pluginContext.getEditorService()
                        .getAllEditor().find { editor ->
                            editor
                                .getFile().path == file.path
                        } != null
                }
                .forEach {
                    pluginContext
                        .getEditorService()
                        .closeEditor(it)
                }


        }

        targetFile.deleteRecursively()

    }

    override fun backup(exportPath: File) {
        TODO("not implemented")
    }


    override fun walkProjectFile(): FileTreeWalk {
        return path.walk()
    }

    private var currentProject: com.dingyi.myluaapp.build.api.Project? = null

    override fun getBuildProject(): com.dingyi.myluaapp.build.api.Project {
        return currentProject ?: pluginContext.getBuildService<Service>()
            .getMainBuilder(this)
            .getProject().apply {
                currentProject = this
            }
    }

    override suspend fun renameFile(file: File, targetFile: File): Unit =
        withContext(Dispatchers.IO) {

            file.copyRecursively(targetFile)

            if (file.isFile) {
                pluginContext
                    .getEditorService()
                    .apply {
                        getEditor(file)?.save()
                        renameEditor(file, targetFile)
                    }
            } else {
                file
                    .walkBottomUp()
                    .filter { it.isFile }
                    .forEach {
                        val toFile = File(targetFile, it.path.substring(file.path.length + 1))
                        pluginContext
                            .getEditorService()
                            .apply {
                                getEditor(it)?.save()
                                renameEditor(it, toFile)
                            }
                    }
            }

            file.deleteRecursively()

        }

    override suspend fun createDirectory(targetPath: File): Unit = withContext(Dispatchers.IO) {
        targetPath.mkdirs()
    }

}