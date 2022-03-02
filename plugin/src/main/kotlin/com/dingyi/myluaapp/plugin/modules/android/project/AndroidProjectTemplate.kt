package com.dingyi.myluaapp.plugin.modules.android.project

import com.dingyi.myluaapp.common.ktx.MutablePair
import com.dingyi.myluaapp.common.ktx.endsWith
import com.dingyi.myluaapp.common.ktx.replaceString
import com.dingyi.myluaapp.common.ktx.toFile
import com.dingyi.myluaapp.plugin.api.project.ProjectTemplate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.lingala.zip4j.ZipFile
import java.io.File


class AndroidProjectTemplate(
    private val templateData: TemplateData
): ProjectTemplate {



    data class TemplateData(
        var path:String,
        val name:Map<String,String>,
        val replaces:List<String>
    )

    private fun getDefaultName():String {
        return templateData.name["default"].toString()
    }

    override val name: String
        get() = getDefaultName()
    override val path: String
        get() = templateData.path

    override suspend fun create(projectPath: File, packageName: String, name: String) = withContext(Dispatchers.IO) {
        val regexAppName = "\$app_name"
        val regexAppPackageName = "\$app_package_name"

        val zipFile = ZipFile(path)
        val pair = MutablePair(name,packageName)


        //projectPath.mkdirs()
        zipFile.fileHeaders
            .forEach {

                var filePath = it.fileName

                if (filePath.indexOf(regexAppName) != -1 || filePath.indexOf(regexAppPackageName) != -1) {
                    replaceString(filePath, pair).apply {

                        zipFile.extractFile(it, projectPath.path, this)
                        filePath = projectPath.path + "/" + this
                    }

                } else {
                    zipFile.extractFile(it, projectPath.path, it.fileName)
                    filePath = projectPath.path + "/" + it.fileName
                }

                if (filePath.endsWith(*templateData.replaces.toTypedArray())) {
                    val file = filePath.toFile()
                    val original = file.readText()
                    if (original.indexOf(regexAppName) != -1 || original.indexOf(regexAppPackageName) != -1) {
                        val replace = replaceString(original, pair)
                        file.writeText(replace)
                    }
                }

            }

    }
}