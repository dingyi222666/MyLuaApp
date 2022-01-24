package com.dingyi.myluaapp.common.dialog.helper

import android.view.View
import com.dingyi.myluaapp.common.dialog.BottomDialog
import com.dingyi.myluaapp.databinding.LayoutBottomDialogDefaultMessageBinding

class DefaultMessageLayoutHelper(rootView: View, dialog: BottomDialog) :
    BaseBottomDialogLayoutHelper(rootView, dialog) {


    private val binding = LayoutBottomDialogDefaultMessageBinding.bind(rootView)

    override fun getCloseView(): View {
        return binding.closeImage
    }

    override fun apply(params: BottomDialog.BottomDialogCreateParams) {


        if (params.message.isNotEmpty()) {
            binding.message.text = params.message
        } else {
            binding.message.visibility = View.GONE
        }

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
                    params.positiveButtonClick(this@DefaultMessageLayoutHelper, null)

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
                    params.negativeButtonClick(this@DefaultMessageLayoutHelper, null)

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
                    params.neutralButtonClick(this@DefaultMessageLayoutHelper, null)
                    dismiss()
                }
            }
        } else {
            binding.neutralButton.visibility = View.GONE
        }


    }
}