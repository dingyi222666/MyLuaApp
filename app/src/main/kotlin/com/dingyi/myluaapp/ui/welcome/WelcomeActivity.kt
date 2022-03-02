package com.dingyi.myluaapp.ui.welcome

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dingyi.myluaapp.build.util.getSHA256
import com.dingyi.myluaapp.common.ktx.Paths
import com.dingyi.myluaapp.common.ktx.startActivity
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


        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)

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

                            val targetPath = File(path, it.fileName.substring("assets/".length))

                            if (targetPath.exists()) {
                                val targetPathHash = targetPath.inputStream().getSHA256()
                                val zipFileHash = file.getInputStream(it).getSHA256()

                                if (targetPathHash != zipFileHash) {
                                    withContext(Dispatchers.Main) {
                                        viewBinding.title.text = "unzip:${it.fileName}"
                                    }
                                    file.extractFile(
                                        it,
                                        path,
                                        it.fileName.substring("assets/".length)
                                    )
                                }

                            } else {
                                withContext(Dispatchers.Main) {
                                    viewBinding.title.text = "unzip:${it.fileName}"
                                }
                                file.extractFile(it, path, it.fileName.substring("assets/".length))
                            }
                        }
                    }




            }



            viewBinding.title.text = "MyLuaApp by dingyi"


            startActivity<MainActivity>()

            finish()

        }


    }

}