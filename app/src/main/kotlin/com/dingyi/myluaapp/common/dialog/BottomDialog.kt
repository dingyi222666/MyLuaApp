package com.dingyi.myluaapp.common.dialog

import android.content.Context
import com.dingyi.myluaapp.common.dialog.helper.BaseBottomDialogLayoutHelper
import com.dingyi.myluaapp.common.dialog.layout.BaseBottomDialogLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlin.properties.Delegates

class BottomDialog(context: Context) : BottomSheetDialog(context) {

    private var _layoutHelper by Delegates.notNull<BaseBottomDialogLayoutHelper>()

    val layoutHelper: BaseBottomDialogLayoutHelper
        get() = _layoutHelper

    fun setContentView(bottomDialogLayout: BaseBottomDialogLayout) {
        val rootView = bottomDialogLayout.getRootView(layoutInflater)
        _layoutHelper = bottomDialogLayout.getLayoutHelper(rootView)
        setContentView(rootView)
    }



}