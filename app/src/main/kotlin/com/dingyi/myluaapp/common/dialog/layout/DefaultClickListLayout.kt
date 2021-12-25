package com.dingyi.myluaapp.common.dialog.layout

import android.view.LayoutInflater
import android.view.View
import com.dingyi.myluaapp.common.dialog.helper.BaseBottomDialogLayoutHelper
import com.dingyi.myluaapp.common.dialog.helper.DefaultClickListLayoutHelper
import com.dingyi.myluaapp.databinding.LayoutBottomDialogDefaultClickListBinding

class DefaultClickListLayout:BaseBottomDialogLayout {
    override fun getRootView(inflater: LayoutInflater): View {
        return LayoutBottomDialogDefaultClickListBinding.inflate(inflater).root
    }

    override fun getLayoutHelper(rootView: View): BaseBottomDialogLayoutHelper {
        return DefaultClickListLayoutHelper(rootView)
    }
}