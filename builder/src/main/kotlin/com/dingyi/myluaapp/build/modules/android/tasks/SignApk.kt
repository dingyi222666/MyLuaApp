package com.dingyi.myluaapp.build.modules.android.tasks

import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.default.DefaultTask
import java.io.File
import java.util.*

class SignApk(private val module: Module) : DefaultTask(module) {

    override val name: String
        get() = getType()

    private lateinit var buildVariants: String


    private fun getType(): String {
        if (this::buildVariants.isInitialized) {
            return "Sign${
                buildVariants.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }
            }Apk"
        }
        return javaClass.simpleName
    }

    private lateinit var inputPath: File

    private val outputPath: String
        get() = "build/outputs/apk/$buildVariants/app-$buildVariants.apk"


    override suspend fun prepare(): Task.State {

        return Task.State.DEFAULT
    }

    override suspend fun run() {

    }
}