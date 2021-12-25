package com.dingyi.myluaapp.common.dialog.helper

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout

abstract class BaseBottomDialogLayoutHelper(val rootView: View):IBaseBottomDialogLayoutHelper

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

    fun setItemList(list: List<Pair<String, Any>>) {}

    fun onItemClick(block: (String, Any) -> Unit) {}

    fun getCloseView(): View
}