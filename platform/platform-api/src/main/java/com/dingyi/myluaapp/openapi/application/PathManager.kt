package com.dingyi.myluaapp.openapi.application

import com.android.tools.build.bundletool.model.BundleModule.LIB_DIRECTORY
import kotlin.io.path.pathString


object PathManager {


    lateinit var homePath: String

    lateinit var configPath: String

    private var ourPluginsPath: String? = null

    private const val PLUGINS_DIRECTORY = "plugins"

    private const val CONFIG_DIR = "config"

    private const val LIB_DIRECTORY = "lib"

    init {

        homePath = ApplicationManager.getAndroidApplication().let { application ->
            (application.externalCacheDir?.toPath()?.parent ?: application.cacheDir.toPath().parent)
                .pathString
        } ?: error("load home path failed")

        configPath = "$homePath/$CONFIG_DIR"

    }

    fun getPreInstalledPluginsPath(): String {
        return "$homePath/$PLUGINS_DIRECTORY"
    }


    fun getPluginsPath(): String {
        if (ourPluginsPath != null) return ourPluginsPath.toString()

        ourPluginsPath = ("$configPath/").toString() + PLUGINS_DIRECTORY

        return ourPluginsPath.toString()
    }


    fun getLibPath(): String {
        return "$homePath/$LIB_DIRECTORY"
    }


}