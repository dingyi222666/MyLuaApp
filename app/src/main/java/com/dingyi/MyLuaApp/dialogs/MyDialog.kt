package com.dingyi.MyLuaApp.dialogs

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.dingyi.MyLuaApp.R
import com.dingyi.MyLuaApp.utils.ThemeUtil

class MyDialog(activity: Activity) : AlertDialog.Builder(activity) {
    private var themeBackground=0xffffffff
    private val activity=activity;

    constructor(activity: Activity, themeUtil: ThemeUtil) :this(activity) {
        themeBackground=themeUtil.colorBackgroundColor.toLong()
    }


    override fun show(): AlertDialog {
        val dialog = super.show()
        dialog.window?.let {
            it.setWindowAnimations(R.style.BaseDialogAnim)//设置动画
            if (android.os.Build.VERSION.SDK_INT >= 22) {
                it.setElevation(0f)//阴影
            }

        }
        return dialog
    }
}