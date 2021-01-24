package com.dingyi.MyLuaApp.builder

import androidx.appcompat.app.AppCompatActivity

interface IBuilderOut {
    fun hasMessage(string: String);
    fun hasError(string: String);
    fun init(activity: AppCompatActivity):IBuilderOut;
    fun end();
}