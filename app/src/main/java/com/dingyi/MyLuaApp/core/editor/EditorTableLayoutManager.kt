package com.dingyi.MyLuaApp.core.editor

import com.google.android.material.tabs.TabLayout

class EditorTableLayoutManager(private val tabLayout: TabLayout) {

    var selectedTabCallBack: (String) -> Unit = {

    }


    init {

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
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
        tabLayout.addTab(tabLayout.newTab().setText(name), true)

    }

    fun selectTab(name: String) {
        for (i in 0 until tabLayout.tabCount) {
            tabLayout.getTabAt(i)?.let { tab ->
                if (tab.text.toString() == name) {
                    tabLayout.selectTab(tab)
                }
            }

        }
    }

    fun renameTab(oldName:String,newName:String) {
        for (i in 0 until tabLayout.tabCount) {
            tabLayout.getTabAt(i)?.let { tab ->
                if (tab.text.toString() == oldName) {
                    tab.text = newName
                }
            }

        }
    }

    fun removeTab(name: String) {
        for (i in 0 until tabLayout.tabCount) {
            tabLayout.getTabAt(i)?.let { tab ->
                if (tab.text.toString() == name) {
                    if (i == 1&&tabLayout.tabCount>=2) {
                        tabLayout.getTabAt(2)?.select()
                    } else if (tabLayout.tabCount > 1) {
                        tabLayout.getTabAt(i - 1)?.select()
                    }
                    tabLayout.removeTabAt(i)
                }
            }

        }
    }

}