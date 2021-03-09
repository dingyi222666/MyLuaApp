package com.dingyi.MyLuaApp.core.editor

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

    fun selectTab(name:String) {
        for (i in 0 until tabLayout.tabCount) {
            tabLayout.getTabAt(i)?.let { tab->
                if (tab.text.toString()==name) {
                    tab.select()
                }
            }

        }
    }

    fun removeTab(name: String) {
        for (i in 0 until tabLayout.tabCount) {
            tabLayout.getTabAt(i)?.let { tab->
                if (tab.text.toString()==name) {
                    if (i==1) {
                        tabLayout.getTabAt(2)?.select()
                    }else if (tabLayout.tabCount>1) {
                        tabLayout.getTabAt(i-1)?.select()
                    }
                    tabLayout.removeTabAt(i)

                }
            }

        }
    }

}