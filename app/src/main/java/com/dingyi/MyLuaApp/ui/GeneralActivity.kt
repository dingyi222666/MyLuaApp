package com.dingyi.MyLuaApp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dingyi.MyLuaApp.R
import com.dingyi.MyLuaApp.base.BaseFragment
import com.dingyi.MyLuaApp.common.kts.checkNotNull
import com.dingyi.MyLuaApp.databinding.ActivityGeneralBinding

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
            val bundle = it.getBundleExtra("arg").checkNotNull()
            val type = it.getStringExtra("type").checkNotNull()

            val classStatic = Class.forName(type)

            val method = classStatic.getMethod("newInstance", Bundle::class.java)

            method.invoke(null,bundle) as Fragment

        }?.let {
            supportFragmentManager.beginTransaction().add(R.id.container,it)
                .commit()
        }
    }

}