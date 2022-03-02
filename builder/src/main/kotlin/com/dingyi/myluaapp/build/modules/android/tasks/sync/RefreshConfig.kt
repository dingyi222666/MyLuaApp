package com.dingyi.myluaapp.build.modules.android.tasks.sync

import com.dingyi.myluaapp.build.api.Module
import com.dingyi.myluaapp.build.api.Task
import com.dingyi.myluaapp.build.default.DefaultTask
import com.dingyi.myluaapp.build.modules.android.parser.AndroidManifestSimpleParser
import com.dingyi.myluaapp.common.ktx.getJavaClass
import com.dingyi.myluaapp.common.ktx.toFile
import com.google.gson.Gson
import org.luaj.vm2.LuaValue
import java.io.File

class RefreshConfig(private val module: Module) : DefaultTask(module) {
    override val name: String
        get() = this.javaClass.simpleName

    override suspend fun prepare(): Task.State {
        return Task.State.DEFAULT
    }

    override suspend fun run() {

        val buildScript = module
            .getMainBuilderScript()

        val packageName = (buildScript.get("android.defaultConfig.applicationId") as LuaValue?)
            ?.tojstring() ?: readManifestInfo()

        val appName = (module
            .getProject()
            .getSettingsScript()
            .get("rootProject.name") as LuaValue?)
            ?.tojstring()

        val configFile = File(module.getProject().getPath().toFile(), ".MyLuaApp/.config.json")

        val data =
            Gson().fromJson(configFile.readText(), getJavaClass<MutableMap<String, String>>())

        data["appPackageName"] = packageName ?: data["appPackageName"].toString()

        data["appName"] = appName ?: data["appName"].toString()

        configFile.writeText(Gson().toJson(data))
    }

    private fun readManifestInfo(): String? {
        return AndroidManifestSimpleParser()
            .parse(
                module.getFileManager()
                    .resolveFile("src/main/AndroidManifest.xml", module).path
            )
            .versionName
    }


}