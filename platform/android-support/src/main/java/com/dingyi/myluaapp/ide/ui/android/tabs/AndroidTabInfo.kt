package com.dingyi.myluaapp.ide.ui.android.tabs

import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.core.view.allViews
import androidx.core.view.isVisible
import com.google.android.material.tabs.TabLayout
import kotlin.properties.Delegates
import kotlin.properties.ObservableProperty

class AndroidTabInfo(
    private val wrapperTab: TabLayout.Tab,
    private val parentView: AndroidTabView
) {

    var tooltipText: String
        get() = wrapperTab.view.tooltipText.toString()
        set(value) {
            wrapperTab.view.tooltipText = value
        }

    var icon: Drawable?
        get() = wrapperTab.icon
        set(value) {
            wrapperTab.icon = value
        }

    var text: String
        get() = wrapperTab.text.toString()
        set(value) {
            wrapperTab.text = value
        }


    var enabled by Delegates.observable(true) { property, oldValue, newValue ->
        dispatchEnabled(newValue)
    }

    private fun dispatchEnabled(newValue: Boolean) {
        wrapperTab.view.allViews.forEach {
            it.isEnabled = newValue
        }
    }

    var hidden: Boolean
        get() = wrapperTab.view.visibility == View.VISIBLE
        set(value) {
            wrapperTab.view.isVisible = value
        }


    fun select() {
        wrapperTab.select()
    }


}