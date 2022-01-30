package com.dingyi.myluaapp.build.modules.android.tasks

import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import java.util.*

class GenerateBuildConfig(
    private val module: Module
): Task {
    override val name: String
        get() = getType()




    private lateinit var buildVariants: String

    private fun getType(): String {
        if (this::buildVariants.isInitialized) {
            return "Generate${buildVariants.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }}BuildConfig"
        }
        return javaClass.simpleName
    }

    private val buildConfigFile = "build/intermediates/gen_build_config_file/BuildConfig.java"

    private lateinit var ModuleType: String

    override suspend fun prepare(): Task.State {
        ModuleType = if (module.type == "AndroidApplication") {
            "Application"
        } else {
            "Library"
        }



        return Task.State.DEFAULT
    }

    override suspend fun run() {
        TODO("Not yet implemented")
    }
}