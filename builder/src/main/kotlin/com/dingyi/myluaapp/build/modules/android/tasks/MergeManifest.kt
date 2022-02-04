package com.dingyi.myluaapp.build.modules.android.tasks

import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.default.DefaultTask
import com.dingyi.myluaapp.build.modules.android.config.BuildConfig
import com.dingyi.myluaapp.build.modules.android.parser.AndroidManifestSimpleParser

import java.util.*

class MergeManifest(private val applicationModule:Module): DefaultTask(applicationModule){
    override val name: String
        get() = getType()

    private lateinit var buildVariants: String


    private fun getType(): String {
        if (this::buildVariants.isInitialized) {
            return "Merge${
                buildVariants.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }
            }AndroidManifest"
        }
        return javaClass.simpleName
    }


    override suspend fun prepare(): Task.State {
        return Task.State.DEFAULT
    }

    override suspend fun run() {

    }


}