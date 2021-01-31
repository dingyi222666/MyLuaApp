package com.dingyi.MyLuaApp.utils

import android.app.ProgressDialog
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import com.dingyi.MyLuaApp.R
import com.dingyi.MyLuaApp.base.BaseActivity

fun foreachSetMenuIconColor(menu: Menu, color: Int) {
    val array = menu.children
    array.forEach { item ->
        val drawable = item.icon
        drawable?.let {
            it.setTint(color)
        }
    }
}

fun Context.dp2px(int: Int):Int {
    val scale=this.resources.displayMetrics.density
    return (int*scale+0.5f).toInt()
}

fun radius(v: View, context: BaseActivity, int: Int) {
    val drawable=GradientDrawable()
    drawable.shape=GradientDrawable.RECTANGLE
    drawable.setColor(context.themeUtil.colorBackgroundColor)
    drawable.cornerRadius=context.dp2px(int).toFloat()
    v.setBackgroundDrawable(drawable)
}

fun createProgressBarDialog(context: BaseActivity, title: String, message: String):ProgressDialog {
    val dialog = ProgressDialog(context)

    dialog.setTitle(title)
    with(dialog) {
        setTitle(title)
        setMessage(message)
        setProgressStyle(ProgressDialog.STYLE_SPINNER)
        setCancelable(false)
        setCanceledOnTouchOutside(false)

    }

    dialog.window?.let {
        it.setWindowAnimations(R.style.BaseDialogAnim)//设置动画
        if (android.os.Build.VERSION.SDK_INT >= 22) {
            it.setElevation(0f)//阴影
        }
        it.decorView.setBackgroundColor(0)
        radius((it.decorView as ViewGroup).getChildAt(0),context,8)

    }
    //dialog.show()
    return dialog
}