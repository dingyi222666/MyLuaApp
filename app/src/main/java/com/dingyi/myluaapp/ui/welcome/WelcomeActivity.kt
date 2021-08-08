package com.dingyi.myluaapp.ui.welcome

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dingyi.myluaapp.common.kts.startActivity
import com.dingyi.myluaapp.core.welcome.PrepareAssets
import com.dingyi.myluaapp.databinding.ActivityWelcomeBinding
import com.dingyi.myluaapp.ui.main.MainActivity
import kotlin.time.ExperimentalTime

/**
 * @author: dingyi
 * @date: 2021/8/4 13:00
 * @description:
 **/
class WelcomeActivity : AppCompatActivity() {

    @ExperimentalTime
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(ActivityWelcomeBinding.inflate(layoutInflater).root)


        val version = getSharedPreferences("default", Context.MODE_PRIVATE).getInt("version", 0)

        PrepareAssets(this).start {
            startToMainActivity()
        }
        /*
        if (version == 0 || version < versionCode) {

        } else {
            startToMainActivity()
        }

         */


    }

    private fun startToMainActivity() {
        startActivity<MainActivity>()
        finish()
    }

}