package com.dingyi.myluaapp.ui.welcome

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dingyi.myluaapp.common.kts.Paths
import com.dingyi.myluaapp.core.plugin.runtime.plugin.PluginModule
import com.dingyi.myluaapp.databinding.ActivityWelcomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.lingala.zip4j.ZipFile
import java.io.File


class WelcomeActivity : AppCompatActivity() {


    private val viewBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityWelcomeBinding.inflate(layoutInflater)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(viewBinding.root)

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val apkPath = packageResourcePath

                val file = ZipFile(apkPath)

                val path = File(Paths.assetsDir).absolutePath

                Log.e("", path)

                file.fileHeaders
                    .forEach {
                        if (it.fileName.startsWith("assets/")) {
                            withContext(Dispatchers.Main) {
                                viewBinding.title.text = "unzip:${it.fileName}"
                            }
                            file.extractFile(it, path, it.fileName.substring("assets/".length))
                        }
                    }
            }

            viewBinding.title.text = "MyLuaApp by dingyi"

            val pluginModule = PluginModule()

            pluginModule
                .init(this@WelcomeActivity)

            pluginModule.loadAllPlugin()


        }


    }

}