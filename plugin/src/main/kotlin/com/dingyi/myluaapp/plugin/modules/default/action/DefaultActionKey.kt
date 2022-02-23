package com.dingyi.myluaapp.plugin.modules.default.action

import android.app.Notification

object DefaultActionKey {


    val PROJECT_LIST_MENU_ACTION = ActionKey(0x1, false)

    val CREATE_EDITOR_ACTION = ActionKey(0x2,false)

    val CLICK_TREE_VIEW_FILE = ActionKey(0x3,true)


    val CLICK_SYMBOL_VIEW = ActionKey(0x4,false)

    val ADD_PROJECT_MENU = ActionKey(0x5,true)

    val SHOW_FILE_TAG_MENU = ActionKey(0x6, true)

    val OPEN_EDITOR_FILE_DELETE_TOAST = ActionKey(0x7,false)
}