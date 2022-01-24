package com.dingyi.myluaapp.core.project

import com.dingyi.myluaapp.MainApplication
import com.dingyi.myluaapp.common.kts.*
import com.dingyi.myluaapp.common.zip.ZipHelper
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.properties.Delegates

/**
 * @author: dingyi
 * @date: 2021/10/26 9:54
 * @description:
 **/
class ProjectBuilder {


    data class ProjectTemplate(
        var name: Name,
        var path: String, // zip/empty_project.zip
        var replaces: List<String>
    ) {
        data class Name(
            var default: String, // 空模板
            var english: String // Empty Project
        )
    }

    private data class ProjectTemplateRoot(
        val templates: List<ProjectTemplate>
    )


    private var templates by Delegates.notNull<List<ProjectTemplate>>()

    var selectItem = 0

    suspend fun createProject(pair: MutablePair<String, String>) =
        withContext(Dispatchers.IO) {

            val template = templates[selectItem]

            val templatePath = Paths.projectTemplateDir + template.path

            //获取zip内所有文件
            var allZipList = ZipHelper.getZipFileList(templatePath).map { it.name }

            val unPath = Paths.projectDir + "/" + pair.first
            println(allZipList, unPath, templatePath)

            //解压文件
            ZipHelper.unZipFile(
                templatePath,
                allZipList,
                toPath = { unPath },
                unPathFilterPrefix = { "/" })

            //过滤一下文件列表

            val regexAppName = "\$app_name"
            val regexAppPackageName = "\$app_package_name"

            allZipList = allZipList
                .map { "$unPath/$it" }
                .map { filePath ->
                    if (filePath.indexOf(regexAppName) != -1 || filePath.indexOf(regexAppPackageName) != -1) {
                        replaceString(filePath, pair).apply {
                            val file = filePath.toFile()
                            if (file.exists()) {
                                this.toFile().let {
                                    if (file.isDirectory) {
                                        file.copyRecursively(it)
                                        file.deleteRecursively()
                                    } else {
                                        file.copyTo(it)
                                        file.delete()
                                    }
                                }
                            }
                        }
                    }
                    filePath
                }

                .filter {
                    it.endsWith(*template.replaces.toTypedArray())
                }


            //替换文件

            allZipList.forEach {
                val file = it.toFile()
                val original = file.readText()
                if (original.indexOf(regexAppName) != -1 || original.indexOf(regexAppPackageName) != -1) {
                    val replace = replaceString(original, pair)
                    file.writeText(replace)
                }
            }

        }

    fun initDefaultAppName(): MutablePair<String, String> {

        val size = (Paths.projectDir.toFile().listFiles() ?: arrayOf<File>())
            .map { it.name }
            .filter {
                it.startsWith("MyApplication")
            }.size


        val name = "MyApplication" + (if (size > 0) (size + 1).toString() else "")

        return MutablePair(name, "com.MyLuaApp.application")

    }


    /**
     * replace string
     */
    private fun replaceString(
        context: String,
        pair: MutablePair<String, String>,
        usePath: Boolean = false
    ): String {
        return context.replace("\$app_name", pair.first)
            .replace(
                "\$app_package_name",
                if (usePath) pair.second.replace(".", "/") else pair.second
            )
    }


    suspend fun getProjectTemplates() = withContext(Dispatchers.IO) {
        MainApplication.instance.assets.open("res/template/project/project.json")
            .use {
                Gson().fromJson(
                    it.reader(),
                    getJavaClass<ProjectTemplateRoot>()
                ).templates
            }.apply {
                templates = this
            }
    }

    fun checkAppNameCanUse(name: String?): Boolean {
        return (Paths.projectDir + "/$name").toFile().isDirectory &&
                (Paths.projectDir + "/$name").toFile().absolutePath !=
                Paths.projectDir.toFile().absolutePath

    }


}