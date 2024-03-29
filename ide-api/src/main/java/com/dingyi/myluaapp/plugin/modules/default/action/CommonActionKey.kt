package com.dingyi.myluaapp.plugin.modules.default.action

import android.app.Notification

object CommonActionKey {


    val BUILD_STARTED_KEY = ActionKey(0x14,false)
    val CREATE_PROJECT_DIRECTORY = ActionKey(0x13,false)
    val RENAME_PROJECT_FILE = ActionKey(0x12,false)

    val CREATE_PROJECT_FILE = ActionKey(0x11, false)
    val DELETE_PROJECT_FILE = ActionKey(0x10, false)

    val CREATE_EDITOR_ACTION = ActionKey(0x2,true)

    val CLICK_TREE_VIEW_FILE = ActionKey(0x3,true)


    val CLICK_SYMBOL_VIEW = ActionKey(0x4,false)

    val ADD_PROJECT_MENU = ActionKey(0x5,true)

    val SHOW_FILE_TAG_MENU = ActionKey(0x6, true)

    val OPEN_EDITOR_FILE_DELETE_TOAST = ActionKey(0x7,false)

    val OPEN_LOG_FRAGMENT = ActionKey(0x8,false)

    val TREE_LIST_ON_LONG_CLICK = ActionKey(0x9,false)
}