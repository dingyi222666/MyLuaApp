package com.dingyi.myluaapp.common.kts

import java.io.File
import java.security.MessageDigest

/**
 * @author: dingyi
 * @date: 2021/8/6 16:26
 * @description:
 **/


fun String.endsWith(vararg prefix: String): Boolean {
    for (it in prefix) {
        val result = this.endsWith(it, false)
        if (result) {
            return true
        }
    }
    return false
}

fun String.getFileName():String {
    val index = this.lastIndexOf(File.separatorChar)
    return this.substring(index + 1)
}

fun String.toMD5() = kotlin.runCatching {
    val instance = MessageDigest.getInstance("MD5")//获取md5加密对象
    val digest = instance.digest(this.toByteArray())//对字符串加密，返回字节数组
    val sb = StringBuffer()
    for (b in digest) {
        val i = b.toInt() and 0xff//获取低八位有效值
        var hexString = Integer.toHexString(i)//将整数转化为16进制
        if (hexString.length < 2) {
            hexString = "0" + hexString//如果是一位的话，补0
        }
        sb.append(hexString)
    }
    return sb.toString()
}.getOrNull() ?: ""
