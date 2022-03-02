package com.dingyi.myluaapp.common.ktx

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.dingyi.myluaapp.MainApplication
import java.io.File
import kotlin.system.exitProcess


/**
 * @author: dingyi
 * @date: 2021/8/4 14:03
 * @description:
 **/


inline val Context.versionCode: Int
    get() = packageManager.getPackageInfo(packageName, 0).versionCode


inline fun <reified T> Activity.startActivity(block: Intent.() -> Unit = {}) {
    startActivity(Intent(this, T::class.java).apply(block))
}

inline fun Activity.startActivity(targetClass: Class<*>, block: Intent.() -> Unit = {}) {
    startActivity(Intent(this, targetClass).apply(block))
}

fun AppCompatActivity.openDocument(fileType: String, callback: (Uri) -> Unit) {
    registerForActivityResult(ActivityResultContracts.OpenDocument()) {
        callback(it)
    }.launch(arrayOf(fileType))
}

fun restartApp() {
    exitProcess(0)
}

fun Context.getStringArray(resId: Int): Array<String> {
    return this.resources.getStringArray(resId)
}

fun Int.getString(): String {
    return MainApplication.instance.getString(this)
}

fun Int.getStringArray(): Array<String> {
    return MainApplication.instance.getStringArray(this)
}

fun Context.getAttributeColor(resId: Int): Int {
    val typedArray = obtainStyledAttributes(intArrayOf(resId))
    val color = typedArray.getColor(0, 0)
    typedArray.recycle()
    return color
}

fun <T> SharedPreferences.edit(block: SharedPreferences.Editor.() -> T): T {
    var result: T
    this.edit().apply {
        result = block(this)
    }.apply()
    return result
}

/**
 * Install Apk for get path
 */
fun Context.installApk(apkPath: String) {
    val file = File(apkPath)

    val intent = Intent(Intent.ACTION_VIEW)
    val data = FileProvider.getUriForFile(this, packageName, file);
    intent.apply {
        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION;/*给目标设置一个临时授权*/
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        setDataAndType(data, "application/vnd.android.package-archive");
    }

    startActivity(intent);
}


