package com.dingyi.myluaapp.build.util

import java.io.File
import java.io.InputStream
import java.lang.StringBuilder
import java.security.MessageDigest


fun File.getSHA256(): String = inputStream().getSHA256()

fun InputStream.getSHA256(): String {
    return use { stream ->
        val buffer = ByteArray(1024)
        val md5: MessageDigest = MessageDigest.getInstance("SHA-256")
        var numRead = 0
        while (stream.read(buffer).also { numRead = it } > 0) {
            md5.update(buffer, 0, numRead)
        }
        val bytes = md5.digest()
        val sb = StringBuilder()
        for (aB in bytes) {
            sb.append(Integer.toHexString(aB.toInt() and 0xFF))
        }
        sb.toString()
    }
}
