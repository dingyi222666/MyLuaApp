package com.dingyi.myluaapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.base.BasePresenter
import com.dingyi.myluaapp.bean.ProjectInfo
import com.dingyi.myluaapp.common.kts.*
import com.dingyi.myluaapp.network.service.MainService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import org.luaj.vm2.LuajVm
import java.io.File

/**
 * @author: dingyi
 * @date: 2021/8/4 16:00
 * @description:
 **/
class MainPresenter(
    private val viewModel: MainViewModel,
    private val activity: AppCompatActivity
) : BasePresenter<MainViewModel>(viewModel, activity) {




    fun requestPoetry() {
        activity.lifecycleScope.launch {
            MainService.getPoetry(activity)
                .flowOn(Dispatchers.IO)
                .catch {
                    val array = activity.getStringArray(R.array.main_poetry_array)

                    viewModel.title.postValue(array.random())
                }
                .collect {
                    viewModel.title.postValue(it)
                }
        }

    }

    fun refreshProjectList() {
        val projectList = (Paths.projectDir.toFile().listFiles() ?: arrayOf<File>())
            .sortedByDescending {
                it.lastModified()
            }
            .filter {
                "${it.absolutePath}/build.gradle.lua".toFile()
                    .exists() && "${it.absolutePath}/.MyLuaApp/.config.lua".toFile().exists()
            }
            .map { it.path }


        val luaVm = LuajVm()
        val result = mutableListOf<ProjectInfo>()


        projectList.forEach {
            runCatching {
                val table = luaVm.loadFile("$it/.MyLuaApp/.config.lua")
                val info = ProjectInfo(
                    appName = table["appName"].tojstring(),
                    appPackageName = table["appPackageName"].tojstring(),
                    iconPath = it + "/" + table["iconPath"].tojstring(),
                    path = it
                )
                result.add(info)
            }.onFailure {
                R.string.main_project_list_config_error.getString().showToast()
            }
        }

        luaVm.close()
        System.gc()//clear lua vm
        println(result)
        viewModel.mainProjectList.value = result

    }

}