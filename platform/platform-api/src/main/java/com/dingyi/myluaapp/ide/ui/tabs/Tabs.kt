package com.dingyi.myluaapp.ide.ui


import com.dingyi.myluaapp.ide.ui.android.tabs.AndroidTabView
import com.dingyi.myluaapp.ide.ui.tabs.TabInfo
import com.dingyi.myluaapp.ide.ui.tabs.TabsListener
import com.dingyi.myluaapp.openapi.actions.ActionGroup
import com.intellij.openapi.Disposable

import java.util.function.Supplier


interface JBTabs {
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


    fun addListener(listener: TabsListener): JBTabs
    fun addListener(listener: TabsListener, disposable: Disposable): JBTabs
    fun setSelectionChangeHandler(handler: SelectionChangeHandler): JBTabs
    val component: AndroidTabView


    fun getIndexOf(tabInfo: TabInfo?): Int

    fun setNavigationActionBinding(prevActiobId: String, nextActionId: String): JBTabs
    fun setNavigationActionsEnabled(enabled: Boolean): JBTabs
    fun setPopupGroup(popupGroup: ActionGroup, place: String, addNavigationGroup: Boolean): JBTabs
    fun setPopupGroup(
        popupGroup: Supplier<out ActionGroup>,
        place: String,
        addNavigationGroup: Boolean
    ): JBTabs


    interface SelectionChangeHandler {
        fun execute(
            info: TabInfo,
            requestFocus: Boolean,
        )
    }
}
