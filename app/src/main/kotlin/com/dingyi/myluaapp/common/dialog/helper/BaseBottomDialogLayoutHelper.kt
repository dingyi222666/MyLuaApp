package com.dingyi.myluaapp.common.dialog.helper

import android.view.View
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout

abstract class BaseBottomDialogLayoutHelper(val rootView: View) {

    /***
     * if layout has textInputLayout return it
     * @return return textInputLayout if not null
     **/
    abstract fun  getTextInputLayout():TextInputLayout?




    abstract fun getMessageView():TextView?

    abstract fun getPositiveButton():View?

    abstract fun getCloseView():View
}