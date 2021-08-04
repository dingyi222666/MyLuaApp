package com.dingyi.MyLuaApp.ui.main

import android.os.Bundle
import android.view.Menu
import com.dingyi.MyLuaApp.R
import com.dingyi.MyLuaApp.base.BaseActivity
import com.dingyi.MyLuaApp.common.kts.getAttributeColor
import com.dingyi.MyLuaApp.common.kts.iconColor
import com.dingyi.MyLuaApp.databinding.ActivityMainBinding

/**
 * @author: dingyi
 * @date: 2021/8/4 15:06
 * @description:
 **/
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel, MainPresenter>() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setSupportActionBar(viewBinding.toolbarInclude.toolbar)

        supportActionBar.apply {
            title = getString(R.string.app_name)
        }


        presenter.requestPoetry()

    }

    override fun observeViewModel() {
        viewModel.title.observe(this){
            supportActionBar?.subtitle=it
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_toolbar,menu)
        menu?.iconColor(getAttributeColor(R.attr.theme_hintTextColor))
        return super.onCreateOptionsMenu(menu)
    }

    override fun getViewModelClass(): Class<MainViewModel> {
        return MainViewModel::class.java
    }

    override fun getViewBindingInstance(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun getPresenterImp(): MainPresenter {
        return MainPresenter(viewModel, this)
    }
}