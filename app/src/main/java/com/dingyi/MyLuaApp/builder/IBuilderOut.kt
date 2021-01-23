package com.dingyi.MyLuaApp.builder

import androidx.appcompat.app.AppCompatActivity

interface IBuilderOut {
    fun hasMessage(string: String);
    fun hasError(string: String);
    fun init(activity: AppCompatActivity);
    fun bindBuilder(builder: IBuilder)
}