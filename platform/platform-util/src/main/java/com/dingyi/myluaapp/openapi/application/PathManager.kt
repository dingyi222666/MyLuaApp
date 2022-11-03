package com.dingyi.myluaapp.openapi.application

import com.dingyi.myluaapp.common.ktx.checkNotNull
import java.nio.file.Path
import java.nio.file.Paths


object PathManager {


    lateinit var homePath: Path

    lateinit var configPath: Path

    const val CONFIG_DIR = "config"

    init {

        homePath = ApplicationManager.getAndroidApplication().let { application ->
            application.externalCacheDir?.toPath()?.parent ?: application.cacheDir.toPath().parent
        } ?: error("load home path failed")

        configPath = homePath.resolve(CONFIG_DIR)


    }

}