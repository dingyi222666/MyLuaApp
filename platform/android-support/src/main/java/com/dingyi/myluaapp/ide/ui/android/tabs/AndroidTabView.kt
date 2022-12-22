package com.dingyi.myluaapp.ide.ui.android.tabs

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.google.android.material.tabs.TabLayout
import java.util.ArrayList

class AndroidTabView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : TabLayout(context, attrs) {


    @Deprecated("", ReplaceWith("newTab(String)"))
    override fun newTab(): Tab {
        return super.newTab()
    }

    fun newTab(text:String):AndroidTabInfo {
        return AndroidTabInfo(super.newTab(),this)
    }
}