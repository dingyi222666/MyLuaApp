package com.dingyi.myluaapp.common.ktx

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
    startActivity(Intent(this, getJavaClass<T>()).apply(block))
}

inline fun Activity.startActivity(targetClass: Class<*>, block: Intent.() -> Unit = {}) {
    startActivity(Intent(this, targetClass).apply(block))
}


fun restartApp() {
    exitProcess(0)
}

fun Context.getStringArray(resId: Int): Array<String> {
    return this.resources.getStringArray(resId)
}

fun Int.getString(): String {
    return com.dingyi.myluaapp.MainApplication.instance.getString(this)
}

fun Int.getStringArray(): Array<String> {
    return com.dingyi.myluaapp.MainApplication.instance.getStringArray(this)
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


