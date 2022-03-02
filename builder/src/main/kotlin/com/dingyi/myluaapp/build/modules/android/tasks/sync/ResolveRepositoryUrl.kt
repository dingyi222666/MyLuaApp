package com.dingyi.myluaapp.build.modules.android.tasks.sync

import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.default.DefaultTask
import com.dingyi.myluaapp.common.ktx.toFile
import com.google.gson.Gson
import org.luaj.vm2.LuaTable
import java.io.File

class ResolveRepositoryUrl(private val module: Module) : DefaultTask(module) {


    override val name: String
        get() = this.javaClass.simpleName

    override suspend fun prepare(): Task.State {
        return Task.State.DEFAULT
    }

    override suspend fun run() {
        val script = module
            .getProject()
            .getMainBuilderScript()

        val allRepositoryUrl = script
            .get("allprojects.repositories.maven")
        val data = mutableListOf<String>()
        val targetData = mapOf("repositories" to data)

        if (allRepositoryUrl is LuaTable) {
            for (key in allRepositoryUrl.keys()) {
                val value = allRepositoryUrl[key]
                data.add(value.tojstring())
            }
        }

        val json = Gson().toJson(targetData)

        val file = File(module.getProject().getPath().toFile(),".MyLuaApp/repositories.json")
        runCatching {
            file.createNewFile()
            file.writeText(json)
        }.onFailure {
            error("Unable to create repositories json")
        }
    }
}