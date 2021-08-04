package com.dingyi.MyLuaApp.common.kts

import java.io.File

/**
 * @author: dingyi
 * @date: 2021/8/4 14:21
 * @description:
 **/


inline fun String.toFile()= File(this)