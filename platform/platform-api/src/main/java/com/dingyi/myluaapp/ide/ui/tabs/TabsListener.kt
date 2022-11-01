package com.dingyi.myluaapp.ide.ui.tabs

import com.dingyi.myluaapp.ide.ui.tabs.TabInfo

interface TabsListener {
    fun selectionChanged(oldSelection: TabInfo, newSelection: TabInfo) {}
    fun beforeSelectionChanged(oldSelection: TabInfo, newSelection: TabInfo) {}
    fun tabRemoved(tabToRemove: TabInfo) {}
    fun tabsMoved() {}


}
