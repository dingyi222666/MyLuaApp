@file:JvmName("ViewUtils")

package com.dingyi.MyLuaApp.utils

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.GradientDrawable
import android.util.TypedValue
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import com.dingyi.MyLuaApp.R
import com.dingyi.MyLuaApp.base.BaseActivity
import com.google.android.material.snackbar.Snackbar
import java.io.File


fun menuIconColor(menu: Menu, color: Int) {
    val array = menu.children
    array.forEach { item ->
        val drawable = item.icon
        drawable?.setTint(color)
    }
}

fun <T : View> Activity.getDecorView(): T {
    return this.window.decorView as T

}

fun Context.dp2px(int: Int): Int {
    val scale = this.resources.displayMetrics.density
    return (int * scale + 0.5f).toInt()
}

fun Context.sp2px(sp: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        sp,
        Resources.getSystem().displayMetrics
    )
}

fun <T : View> View.getViewParent(): T {
    return this.parent as T
}

fun radius(v: View, context: BaseActivity<*>, int: Int) {
    val drawable = GradientDrawable()
    drawable.shape = GradientDrawable.RECTANGLE
    drawable.setColor(context.themeManager.themeColors.colorBackground)
    drawable.cornerRadius = context.dp2px(int).toFloat()
    v.setBackgroundDrawable(drawable)
}

fun createProgressBarDialog(
    context: BaseActivity<*>,
    title: String?,
    message: String?
): ProgressDialog {
    val dialog = ProgressDialog(context)

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
        radius((it.decorView as ViewGroup).getChildAt(0), context, 8)

    }
    //dialog.show()
    return dialog
}

fun showSnackbar(view: View, resId: Int) {
    Snackbar.make(view, resId, Snackbar.LENGTH_LONG)
        .setText(resId)
        .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
        .show()
}

private var imageData = mapOf(
    "lua,java,aly,gradle,xml,py" to R.drawable.ic_twotone_code_24,
    "default" to R.drawable.ic_twotone_insert_drive_file_24,
    "png,jpg,bmp" to R.drawable.ic_twotone_image_24,
    "dir" to R.drawable.ic_twotone_folder_24
)

fun getImageType(file: File): Int {
    if (file.isDirectory) return imageData["dir"]!!
    if (file.path == "...") return R.drawable.ic_twotone_undo_24
    imageData.forEach {
        if (it.key.lastIndexOf(file.getSuffix()) != -1) {
            return it.value
        }
    }
    return imageData["default"]!!

}

