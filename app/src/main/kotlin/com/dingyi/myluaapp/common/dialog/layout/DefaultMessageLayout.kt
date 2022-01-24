package com.dingyi.myluaapp.common.dialog.layout

import android.view.LayoutInflater
import android.view.View
import com.dingyi.myluaapp.common.dialog.BottomDialog
import com.dingyi.myluaapp.common.dialog.helper.BaseBottomDialogLayoutHelper
import com.dingyi.myluaapp.common.dialog.helper.DefaultMessageLayoutHelper
import com.dingyi.myluaapp.databinding.LayoutBottomDialogDefaultMessageBinding

object DefaultMessageLayout : BaseBottomDialogLayout {


    override fun getRootView(inflater: LayoutInflater): View {
        return LayoutBottomDialogDefaultMessageBinding.inflate(inflater).root
    }

    override fun getLayoutHelper(
        rootView: View,
        dialog: BottomDialog
    ): BaseBottomDialogLayoutHelper {
        return DefaultMessageLayoutHelper(rootView, dialog)
    }
}