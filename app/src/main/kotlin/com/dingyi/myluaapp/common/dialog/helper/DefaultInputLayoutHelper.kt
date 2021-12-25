package com.dingyi.myluaapp.common.dialog.helper

import android.view.View
import android.widget.TextView
import com.dingyi.myluaapp.databinding.LayoutBottomDialogDefaultInputBinding
import com.google.android.material.textfield.TextInputLayout

class DefaultInputLayoutHelper(rootView: View) : BaseBottomDialogLayoutHelper(rootView) {

    private val binding = LayoutBottomDialogDefaultInputBinding.bind(rootView)

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
