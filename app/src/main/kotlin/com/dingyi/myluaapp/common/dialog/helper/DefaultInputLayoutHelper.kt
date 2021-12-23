package com.dingyi.myluaapp.common.dialog.helper

import android.view.View
import android.widget.TextView
import com.dingyi.myluaapp.databinding.LayoutBaseDialogBottomDialogDefaultInputBinding
import com.google.android.material.textfield.TextInputLayout

class DefaultInputLayoutHelper(rootView: View) : BaseBottomDialogLayoutHelper(rootView) {

    val binding = LayoutBaseDialogBottomDialogDefaultInputBinding.bind(rootView)

    override fun getTextInputLayout(): TextInputLayout? {
        return binding.inputLayout
    }

    override fun getMessageView(): TextView? {
       return null
    }

    override fun getPositiveButton(): View? {
        return binding.positiveButton
    }

    override fun getCloseView(): View {
        return binding.closeImage
    }

}
