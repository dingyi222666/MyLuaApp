package com.dingyi.myluaapp.plugin.modules.lua.build

import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Project
import com.dingyi.myluaapp.build.api.builder.Builder
import com.dingyi.myluaapp.build.api.service.HookService
import com.dingyi.myluaapp.build.modules.android.config.BuildConfig
import com.dingyi.myluaapp.build.modules.android.module.AndroidModule
import com.dingyi.myluaapp.plugin.modules.lua.build.tasks.CompileLua
import org.luaj.vm2.LuaBoolean
import org.luaj.vm2.LuaNil
import org.luaj.vm2.LuaTable

class LuaBuildService:HookService {
    override fun onCreateModule(module: Module): Module {
        module.afterInit {
            val plugins = module.getMainBuilderScript()
                .get("plugins")

            if (plugins is LuaTable) {
                for (key in plugins.keys()) {
                    val value = plugins[key].tojstring()
                    if (value == "com.androlua") {
                        injectModule(module)
                    }
                }
            }
        }
        return module
    }


    private fun injectModule(module: Module): Module {

        val builder = module
            .getBuilder()


        val buildType = module
            .getCache()
            .getCache<BuildConfig>("${module.name}_build_config")
            .buildVariants

        val isCompileLua = module.getMainBuilderScript()
            .get("android.buildTypes.$buildType.compileLua")

        //default is true
        if (isCompileLua is LuaNil || (isCompileLua is LuaBoolean && isCompileLua.booleanValue())) {
            builder.onInit {
                builder.dependsOn(
                    CompileLua(module), builder
                        .getTaskByName("MergeJavaResources")
                )
            }
        }

        return module
    }

    override fun onCreateProject(project: Project): Project {
        return project
    }

    override fun onCreateBuilder(builder: Builder): Builder {
       return builder
    }
}