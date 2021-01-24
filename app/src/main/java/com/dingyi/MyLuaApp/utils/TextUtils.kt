package com.dingyi.MyLuaApp.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dingyi.MyLuaApp.R
import com.google.android.material.snackbar.Snackbar

var lastListenerTime = System.currentTimeMillis();

var canExit = false;

fun binSlideOutToRight(code: Int, activity: AppCompatActivity, view: View): Boolean {
    if (code == KeyEvent.KEYCODE_BACK) {
        if (System.currentTimeMillis() - lastListenerTime < 1500 && canExit) {
            activity.finishAndRemoveTask()
        } else {
            canExit = true
            showSnackBar(view, R.string.toast_exit)
        }

        lastListenerTime = System.currentTimeMillis()
        return true

    }
    canExit = false
    lastListenerTime = System.currentTimeMillis()
    return false
}

fun showSnackBar(view: View, string: String) {
    Snackbar.make(view, string, Snackbar.LENGTH_SHORT).setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE).show();
}

fun showSnackBar(view: View, resId: Int) {
    Snackbar.make(view, resId, Snackbar.LENGTH_SHORT).setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE).show();
}

fun showToast(context: Context,resId: Int) {
   Toast.makeText(context,resId,Toast.LENGTH_LONG).show()
}

fun showToast(context: Context,string: String) {
    Toast.makeText(context,string,Toast.LENGTH_LONG).show()
}

fun Context.copyText(text:String) {
    val service=this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    service.setPrimaryClip(ClipData.newPlainText("copyText",text))


}



