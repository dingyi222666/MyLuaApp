package com.dingyi.MyLuaApp.widget.dialogs

import androidx.appcompat.app.AlertDialog
import com.dingyi.MyLuaApp.R
import com.dingyi.MyLuaApp.base.BaseActivity

open class BaseDialog(activity: BaseActivity<*>) : AlertDialog.Builder(activity) {

    override fun show(): AlertDialog {
        val alertDialog = super.show()
        alertDialog.window?.let {
            it.setWindowAnimations(R.style.BaseDialogAnim)//设置动画
            if (android.os.Build.VERSION.SDK_INT >= 22) {
                it.setElevation(0f)//阴影
            }
        }
        return alertDialog
    }
}