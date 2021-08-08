package com.dingyi.MyLuaApp.core.project

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.dingyi.MyLuaApp.bean.NewProjectInfo
import com.dingyi.MyLuaApp.common.kts.Paths
import com.dingyi.MyLuaApp.common.kts.endsWith
import com.dingyi.MyLuaApp.common.kts.toFile
import com.dingyi.MyLuaApp.common.zip.ZipHelper
import kotlinx.coroutines.launch

/**
 * @author: dingyi
 * @date: 2021/8/5 19:46
 * @description:
 **/
class ProjectCreator(
    private val info: NewProjectInfo
) {


    private fun replaceString(context: String, usePath: Boolean = false): String {
        return context.replace("\$app_name", info.appName)
            .replace(
                "\$app_package_name",
                if (usePath) info.appPackageName.replace(".", "/") else info.appPackageName
            )
    }

    fun start(lifecycleOwner: LifecycleOwner, block: suspend () -> Unit) {

        lifecycleOwner.lifecycleScope.launch {
            val templatePath = Paths.projectTemplateDir + info.template?.path
            //获取zip内所有文件
            var allZipList = ZipHelper.getZipAllList(templatePath).map { it.name }
            val unPath = Paths.projectDir + "/" + info.appName
            //解压文件
            ZipHelper.unZipFile(templatePath, allZipList, unPath)

            //过滤一下文件列表

            val regexAppName = "\$app_name"
            val regexAppPackageName = "\$app_package_name"

            allZipList = allZipList
                .map { "$unPath/$it" }
                .map { filePath ->
                    Log.d("map", filePath)
                    if (filePath.indexOf(regexAppName) != -1 || filePath.indexOf(regexAppPackageName) != -1) {
                        replaceString(filePath, true).apply {
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
                    info.template?.let { template ->
                        it.endsWith(*template.replaces.toTypedArray())
                    } == true
                }


            //替换文件


            allZipList.forEach {
                val file = it.toFile()
                val original = file.readText()
                if (original.indexOf(regexAppName) != -1 || original.indexOf(regexAppPackageName) != -1) {
                    val replace = replaceString(original)
                    file.writeText(replace)
                }
            }

            block()
        }

    }


}