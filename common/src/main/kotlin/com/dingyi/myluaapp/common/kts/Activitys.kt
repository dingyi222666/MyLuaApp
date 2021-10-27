package com.dingyi.myluaapp.common.kts

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.dingyi.myluaapp.MainApplication
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

fun Activity.restartApp() {
    val intent = packageManager
        .getLaunchIntentForPackage(application.packageName)
    val restartIntent = PendingIntent.getActivity(applicationContext, 0, intent, 0)
    val mgr = getSystemService(Context.ALARM_SERVICE) as AlarmManager?
    mgr?.set(AlarmManager.RTC, System.currentTimeMillis() + 100, restartIntent) // 1秒钟后重启应用
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

