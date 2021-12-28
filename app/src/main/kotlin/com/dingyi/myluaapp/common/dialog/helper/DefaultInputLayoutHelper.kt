package com.dingyi.myluaapp.common.dialog.helper

import android.view.View
import android.widget.TextView
import com.dingyi.myluaapp.common.dialog.BottomDialog
import com.dingyi.myluaapp.databinding.LayoutBottomDialogDefaultInputBinding
import com.google.android.material.textfield.TextInputLayout

class DefaultInputLayoutHelper(rootView: View, dialog: BottomDialog) :
    BaseBottomDialogLayoutHelper(rootView, dialog) {

    private val binding = LayoutBottomDialogDefaultInputBinding.bind(rootView)

    override fun getTextInputLayout(): TextInputLayout? {
        return binding.inputLayout
    }


    override fun getMessageView(): TextView? {
        return null
    }

    override fun getPositiveButton(): View {
        return binding.positiveButton
    }

    override fun getCloseView(): View {
        return binding.closeImage
    }

    override fun apply(params: BottomDialog.BottomDialogCreateParams) {


        if (params.title.isNotEmpty()) {
            binding.title.text = params.title
        } else {
            binding.title.visibility = View.GONE
        }


        if (params.title.isEmpty()) {
            binding.title.visibility = View.INVISIBLE
        } else {
            binding.title.text = params.title
        }

        if (params.positiveButtonText.isNotEmpty()) {
            binding.positiveButton.apply {
                text = params.positiveButtonText

                setOnClickListener {
                    interceptClose(true)
                    params.positiveButtonClick(this@DefaultInputLayoutHelper, getItem())

                    dismiss()
                }
            }
        } else {
            binding.positiveButton.visibility = View.GONE
        }

        if (params.negativeButtonText.isNotEmpty()) {
            binding.negativeButton.apply {
                text = params.negativeButtonText
                setOnClickListener {
                    interceptClose(true)
                    params.negativeButtonClick(this@DefaultInputLayoutHelper, getItem())

                    dismiss()
                }
            }
        } else {
            binding.negativeButton.visibility = View.GONE
        }

        if (params.neutralButtonText.isNotEmpty()) {
            binding.neutralButton.apply {
                text = params.neutralButtonText

                setOnClickListener {
                    interceptClose(true)
                    params.neutralButtonClick(this@DefaultInputLayoutHelper, getItem())
                    dismiss()
                }
            }
        } else {
            binding.neutralButton.visibility = View.GONE
        }

    }


    private fun getItem(): Pair<String, String> {
        return "" to binding.inputEditText.text.toString()
    }

}
