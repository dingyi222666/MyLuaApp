package com.dingyi.myluaapp.common.dialog.helper

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dingyi.myluaapp.common.dialog.BottomDialog
import com.google.android.material.textfield.TextInputLayout

abstract class BaseBottomDialogLayoutHelper(val rootView: View):IBaseBottomDialogLayoutHelper {
    override fun apply(params: BottomDialog.BottomDialogCreateParams) {
        getCloseView().setOnClickListener {  }
    }
}

interface IBaseBottomDialogLayoutHelper {

    /***
     * if layout has textInputLayout return it
     * @return return textInputLayout if not null
     **/
    fun getTextInputLayout(): TextInputLayout? {
        return null
    }

    fun getMessageView(): TextView? {
        return null
    }

    fun getPositiveButton(): View? {
        return null
    }

    fun getListView(): RecyclerView? {
        return null
    }


    fun getCloseView(): View
    fun apply(params: BottomDialog.BottomDialogCreateParams)
}