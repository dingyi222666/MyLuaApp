package com.dingyi.myluaapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.common.kts.checkNotNull
import com.dingyi.myluaapp.databinding.ActivityGeneralBinding

/**
 * @author: dingyi
 * @date: 2021/8/4 21:31
 * @description:
 **/
class GeneralActivity : AppCompatActivity() {


    private val binding by lazy {
        ActivityGeneralBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        intent?.let {
            val bundle = it.getBundleExtra("arg")
            val type = it.getStringExtra("type").checkNotNull()

            val classStatic = Class.forName(type)

            (classStatic.newInstance() as Fragment).apply {
                    arguments=bundle
            }

        }?.let {
            supportFragmentManager.beginTransaction().add(R.id.container, it)
                .commit()
        }
    }

}