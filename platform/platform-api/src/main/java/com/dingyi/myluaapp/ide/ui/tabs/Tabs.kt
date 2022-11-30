package com.dingyi.myluaapp.ide.ui


import com.dingyi.myluaapp.ide.ui.android.tabs.AndroidTabView
import com.dingyi.myluaapp.ide.ui.tabs.TabInfo
import com.dingyi.myluaapp.ide.ui.tabs.TabsListener
import com.dingyi.myluaapp.openapi.actions.ActionGroup
import com.intellij.openapi.Disposable

import java.util.function.Supplier


interface Tabs {
    fun addTab(info: TabInfo, index: Int): TabInfo
    fun addTab(info: TabInfo): TabInfo
    fun removeTab(info: TabInfo): Boolean
    fun removeAllTabs()
    fun select(info: TabInfo, requestFocus: Boolean): Boolean
    val selectedInfo: TabInfo

    fun getTabAt(tabIndex: Int): TabInfo
    val tabCount: Int


    val tabs: List<TabInfo>
    val targetInfo: TabInfo


    fun addListener(listener: TabsListener): Tabs
    fun addListener(listener: TabsListener, disposable: Disposable): Tabs
    fun setSelectionChangeHandler(handler: SelectionChangeHandler): Tabs
    val component: AndroidTabView


    fun getIndexOf(tabInfo: TabInfo?): Int

    fun setNavigationActionBinding(prevActiobId: String, nextActionId: String): Tabs
    fun setNavigationActionsEnabled(enabled: Boolean): Tabs
    fun setPopupGroup(popupGroup: ActionGroup, place: String, addNavigationGroup: Boolean): Tabs
    fun setPopupGroup(
        popupGroup: Supplier<out ActionGroup>,
        place: String,
        addNavigationGroup: Boolean
    ): Tabs


    interface SelectionChangeHandler {
        fun execute(
            info: TabInfo,
            requestFocus: Boolean,
        )
    }
}
