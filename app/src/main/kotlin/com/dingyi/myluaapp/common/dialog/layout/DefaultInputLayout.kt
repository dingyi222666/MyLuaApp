package com.dingyi.myluaapp.common.dialog.layout

import android.view.LayoutInflater
import android.view.View
import com.dingyi.myluaapp.common.dialog.BottomDialog
import com.dingyi.myluaapp.common.dialog.helper.BaseBottomDialogLayoutHelper
import com.dingyi.myluaapp.common.dialog.helper.DefaultInputLayoutHelper
import com.dingyi.myluaapp.databinding.LayoutBottomDialogDefaultInputBinding

class DefaultInputLayout: BaseBottomDialogLayout {
    override fun getRootView(inflater: LayoutInflater): View {
        return LayoutBottomDialogDefaultInputBinding.inflate(inflater).root
    }

    override fun getLayoutHelper(rootView: View,dialog: BottomDialog): BaseBottomDialogLayoutHelper {
        return DefaultInputLayoutHelper(rootView,dialog)
    }
}