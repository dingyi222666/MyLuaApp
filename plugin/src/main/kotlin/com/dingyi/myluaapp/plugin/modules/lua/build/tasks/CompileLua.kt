package com.dingyi.myluaapp.plugin.modules.lua.build.tasks

import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.default.DefaultTask
import com.dingyi.myluaapp.build.modules.android.config.BuildConfig
import com.dingyi.myluaapp.build.modules.public.compiler.LuaCompiler

class CompileLua(
    private val module: Module
): DefaultTask(module) {

    override var name: String = "compileLua"


    private lateinit var buildVariants: String


    override suspend fun prepare(): Task.State {

        buildVariants =
            module.getCache().getCache<BuildConfig>("${module.name}_build_config").buildVariants


        getTaskInput()
            .addInputDirectory(
                module.getFileManager().resolveFile(
                    "build/intermediates/merged_assets/$buildVariants", module
                )
            )

        getTaskInput()
            .addInputDirectory(
                module
                    .getFileManager()
                    .resolveFile("build/intermediates/library_java_res/$buildVariants", module)
            )


        getTaskInput()
            .transformDirectoryToFile { it.isFile && it.extension == "lua" }

        return super.prepare()
    }

    override suspend fun run() {

        //ForEach all lua file in TaskInput,and also compile that lua file to byte code
        getTaskInput().getAllInputFile().forEach {
            val path = it.toFile().path
            LuaCompiler.compile(path, path)
        }


    }
}