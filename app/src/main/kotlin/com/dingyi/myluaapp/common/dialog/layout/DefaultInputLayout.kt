package com.dingyi.myluaapp.common.dialog.layout

import android.view.LayoutInflater
import android.view.View
import com.dingyi.myluaapp.databinding.LayoutBaseDialogBottomDialogDefaultInputBinding
import com.dingyi.myluaapp.common.dialog.helper.BaseBottomDialogLayoutHelper
import com.dingyi.myluaapp.common.dialog.helper.DefaultInputLayoutHelper

class DefaultInputLayout: BaseBottomDialogLayout {
    override fun getRootView(context: LayoutInflater): View {
        return LayoutBaseDialogBottomDialogDefaultInputBinding.inflate(context).root
    }

    override fun getLayoutHelper(rootView: View): BaseBottomDialogLayoutHelper {
        return DefaultInputLayoutHelper(rootView)
    }
}