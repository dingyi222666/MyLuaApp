package com.dingyi.myluaapp.ui.welcome

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dingyi.myluaapp.build.util.getSHA256
import com.dingyi.myluaapp.common.kts.Paths
import com.dingyi.myluaapp.common.kts.startActivity
import com.dingyi.myluaapp.plugin.runtime.plugin.PluginModule
import com.dingyi.myluaapp.databinding.ActivityWelcomeBinding
import com.dingyi.myluaapp.ui.main.MainActivity
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

                // load plugin module

                val apkPath = packageResourcePath

                val file = ZipFile(apkPath)

                val path = File(Paths.assetsDir).absolutePath

                file.fileHeaders
                    .forEach {
                        if (it.fileName.startsWith("assets/")) {
                            withContext(Dispatchers.Main) {
                                viewBinding.title.text = "unzip:${it.fileName}"
                            }
                            val targetPath = File(path, it.fileName.substring("assets/".length))

                            if (targetPath.exists()) {
                                val targetPathHash = targetPath.inputStream().getSHA256()
                                val zipFileHash = file.getInputStream(it).getSHA256()

                                if (targetPathHash != zipFileHash) {
                                    file.extractFile(
                                        it,
                                        path,
                                        it.fileName.substring("assets/".length)
                                    )
                                }

                            } else {
                                file.extractFile(it, path, it.fileName.substring("assets/".length))
                            }
                        }
                    }


                PluginModule.init()
                PluginModule.loadAllPlugin()



            }



            viewBinding.title.text = "MyLuaApp by dingyi"


            startActivity<MainActivity>()

        }


    }

}