package com.dingyi.myluaapp.ui

import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.common.kts.checkNotNull
import com.dingyi.myluaapp.common.kts.getString
import com.dingyi.myluaapp.common.kts.showSnackBar
import com.dingyi.myluaapp.databinding.ActivityGeneralBinding

/**
 * @author: dingyi
 * @date: 2021/8/4 21:31
 * @description:
 **/
class GeneralActivity : AppCompatActivity() {


    private var lastBackTime = System.currentTimeMillis()

    private val binding by lazy {
        ActivityGeneralBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_YES
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        setSupportActionBar(binding.toolbarInclude.toolbar)

        intent?.let {
            val bundle = it.getBundleExtra("arg")
            val type = it.getStringExtra("type").checkNotNull()

            val classStatic = Class.forName(type)

            (classStatic.newInstance() as Fragment).apply {
                arguments = bundle
            }

        }?.let {
            supportFragmentManager.beginTransaction().add(R.id.container, it)
                .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)

    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        return when (keyCode) {
            KeyEvent.KEYCODE_BACK, KeyEvent.KEYCODE_ESCAPE -> {
                if (System.currentTimeMillis() - lastBackTime > 2000) {
                    R.string.toast_exit_app
                        .getString()
                        .showSnackBar(binding.root)
                    lastBackTime = System.currentTimeMillis()
                    true
                } else {
                    finish()
                    super.onKeyUp(keyCode, event)
                }
            }
            else -> super.onKeyUp(keyCode, event)
        }
    }

}