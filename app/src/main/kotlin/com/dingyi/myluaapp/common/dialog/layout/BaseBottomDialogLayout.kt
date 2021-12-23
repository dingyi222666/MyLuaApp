package com.dingyi.myluaapp.common.dialog.layout

import android.view.LayoutInflater
import android.view.View
import com.dingyi.myluaapp.common.dialog.helper.BaseBottomDialogLayoutHelper

interface BaseBottomDialogLayout {
    fun getRootView(context: LayoutInflater): View
    fun getLayoutHelper(rootView: View): BaseBottomDialogLayoutHelper
}