package com.dingyi.myluaapp.ui.newproject

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.base.BasePresenter
import com.dingyi.myluaapp.bean.ProjectTemplates
import com.dingyi.myluaapp.common.kts.Paths
import com.dingyi.myluaapp.common.kts.getString
import com.dingyi.myluaapp.common.kts.showToast
import com.dingyi.myluaapp.common.kts.toFile
import com.dingyi.myluaapp.core.project.ProjectCreator
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

/**
 * @author: dingyi
 * @date: 2021/8/4 22:08
 * @description:
 **/
class MainPresenter(
    val viewModel: MainViewModel,
    val activity: AppCompatActivity
) : BasePresenter<MainViewModel>(viewModel, activity) {


    fun updateTemplates() {
        activity.lifecycleScope.launch {
            val text = withContext(Dispatchers.Default) {
                "/data/data/${activity.packageName}/files/res/template/project/project.json"
                    .toFile()
                    .inputStream()
                    .use {
                        it.readBytes().decodeToString()
                    }
            }

            val bean = Gson().fromJson(text, ProjectTemplates::class.java)
            viewModel.templates.postValue(bean)
        }

    }

    fun checkAppNameCanUse(name: String): Boolean {
        return (Paths.projectDir + "/$name").toFile().isDirectory &&
                (Paths.projectDir + "/$name").toFile().absolutePath !=
                Paths.projectDir.toFile().absolutePath
    }


    fun initDefaultAppName() {


        val size = (Paths.projectDir.toFile().listFiles() ?: arrayOf<File>())
            .map { it.name }
            .filter {
                it.startsWith("MyApplication")
            }.size


        val name = "MyApplication" + (if (size > 0) (size + 1).toString() else "")

        viewModel.appName.value = name

        viewModel.appPackageName.value = "com.MyLuaApp.application"

    }

    fun newProject() {
        val info = viewModel.info
        if (info.appName.isEmpty()) {
            return R.string.new_project_app_name_error_empty
                .getString().showToast()

        }
        if (checkAppNameCanUse(info.appName)) {
            return R.string.new_project_app_name_error.getString().showToast()
        }
        if (info.appPackageName.isEmpty()) {
            return R.string.new_project_app_package_error_empty
                .getString().showToast()
        }
        viewModel.showProgressBar.value = true
        ProjectCreator(viewModel.info).start(activity) {
            delay(400)//故意延时1秒去突出新建项目的存在感
            viewModel.showProgressBar.value = false
            delay(80)
            activity.finish()
        }

    }
}