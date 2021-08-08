package com.dingyi.myluaapp.common.kts

/**
 * @author: dingyi
 * @date: 2021/8/6 16:26
 * @description:
 **/


fun String.endsWith(vararg prefix: String):Boolean {
    for (it in prefix) {
        val result=this.endsWith(it,false)
        if (result) {
            return true
        }
    }
    return false
}