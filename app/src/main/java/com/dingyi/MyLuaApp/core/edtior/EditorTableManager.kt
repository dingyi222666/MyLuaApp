package com.dingyi.MyLuaApp.core.edtior

import com.google.android.material.tabs.TabLayout

class EditorTableManager(private val tabLayout: TabLayout) {

    var selectedTabCallBack:(String)->Unit={

    }

    init {

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let{
                    selectedTabCallBack(it.text.toString())
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                //
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                //
            }
        })
    }

    fun addTab(name: String) {
        tabLayout.addTab(tabLayout.newTab().setText(name))
        tabLayout.selectTab(tabLayout.getTabAt(tabLayout.tabCount-1))
    }

    fun removeTab(name: String) {

    }

}