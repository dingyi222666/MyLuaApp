package com.dingyi.myluaapp.build.modules.android.tasks

import com.dingyi.myluaapp.build.api.project.Module
import com.dingyi.myluaapp.build.api.task.Task
import java.util.*

class GenerateBuildConfig(
    private val module: Module
):Task {
    override val name: String
        get() = getType()




    private lateinit var type: String

    private fun getType(): String {
        if (this::type.isInitialized) {
            return "Generate${type.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }}BuildConfig"
        }
        return javaClass.simpleName
    }


    private lateinit var buildConfigType:String

    override suspend fun prepare() {
        if (module.type=="AndroidApplication") {
            buildConfigType = "Application"
        } else {
            buildConfigType = "Library"
        }
    }

    override suspend fun run() {
        TODO("Not yet implemented")
    }
}