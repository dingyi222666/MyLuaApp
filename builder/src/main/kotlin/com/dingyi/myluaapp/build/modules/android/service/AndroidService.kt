package com.dingyi.myluaapp.build.modules.android.service

import com.dingyi.myluaapp.build.api.builder.Builder
import com.dingyi.myluaapp.build.api.builder.MainBuilder
import com.dingyi.myluaapp.build.api.project.Module
import com.dingyi.myluaapp.build.api.project.Project
import com.dingyi.myluaapp.build.api.service.Service
import com.dingyi.myluaapp.build.default.DefaultBuilder
import com.dingyi.myluaapp.build.default.DefaultModule
import com.dingyi.myluaapp.build.default.DefaultProject
import com.dingyi.myluaapp.build.modules.android.builder.AndroidLibraryBuilder
import com.dingyi.myluaapp.build.modules.android.module.AndroidModule
import com.dingyi.myluaapp.common.kts.toFile
import org.luaj.vm2.LuaTable

class AndroidService : Service {


    override fun onCreateProject(path: String, builder: MainBuilder): Project? {
        return DefaultProject(path, builder)
    }

    override fun onCreateModule(path: String, project: Project): Module? {

        val targetScript = project.getAllScript()
            .filter {
                it.getPath().toFile().parentFile?.path == path && it.getName() == "build.gradle.lua"
            }
            .getOrNull(0)

        println("${targetScript?.getPath()} $path")

        val table = targetScript?.get("plugins")

        if (table is LuaTable) {
            for (key in table.keys()) {
                val value = table[key].tojstring()
                if (value == "com.android.application" || value == "com.android.library") {
                    return AndroidModule(project, path)
                }
            }
        }


        return null

    }

    override fun onCreateBuilder(path: String, module: Module): Builder? {

        val table = module.getMainBuilderScript().get("plugins")

        if (table is LuaTable) {
            for (key in table.keys()) {
                when (table[key].tojstring()) {
                    "com.android.library" -> return AndroidLibraryBuilder(module)
                }
            }
        }


        return DefaultBuilder(module)
    }
}