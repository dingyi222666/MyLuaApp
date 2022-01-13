package com.dingyi.myluaapp.builder.invocation

import com.dingyi.myluaapp.builder.api.internal.*
import com.dingyi.myluaapp.builder.api.internal.plugin.DefaultPluginContainer
import com.dingyi.myluaapp.builder.api.internal.plugin.PluginContainer
import com.dingyi.myluaapp.common.kts.Paths
import com.dingyi.myluaapp.common.kts.toFile
import java.io.File
import java.util.logging.Logger
import kotlin.properties.Delegates

class DefaultBuilder(
    private val startPath:String,
    private val logger: Logger
) :BuilderInternal {

    private var _rootProject by Delegates.notNull<ProjectInternal>()

    private val defaultProjectContainer = DefaultProjectContainer(this)

    private val pluginContainer = DefaultPluginContainer(this)

    private val defaultProjectRunner = DefaultProjectRunner(this)

    private var _defaultProject by Delegates.notNull<ProjectInternal>()

    override fun getRootProject(): ProjectInternal {
        return _rootProject

    }

    override fun getProjectRunner(): ProjectRunner {
        return defaultProjectRunner
    }

    override fun getDefaultProject(): ProjectInternal {
        return _defaultProject
    }

    override fun setDefaultProject(defaultProject: ProjectInternal) {
        this._defaultProject = defaultProject
    }

    override fun setRootProject(rootProject: ProjectInternal) {
      this._rootProject = rootProject
    }

    override fun getProjectContainer(): ProjectContainer {
        return defaultProjectContainer
    }

    override fun getPluginContainer(): PluginContainer {
        return pluginContainer
    }

    override val builderVersion: String
        get() = "1.0"
    override val builderHomeDir: File
        get() = Paths.builderPath.toFile()


    override fun logWarn(tag: String, message: String) {
        logger.warning("$tag:$message")
    }

    override fun logInfo(tag: String, message: String) {
        logger.info("$tag:$message")
    }

    override fun logError(tag: String, message: String) {
        logger.severe("$tag:$message")
    }

    override fun logDebug(tag: String, message: String) {
        logger.config("$tag:$message")
    }

    override fun logVerbose(tag: String, message: String) {
        logger.fine("$tag:$message")
    }
}