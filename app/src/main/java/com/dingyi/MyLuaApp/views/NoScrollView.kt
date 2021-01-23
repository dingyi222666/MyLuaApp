package com.dingyi.MyLuaApp.views

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ScrollView
import java.util.jar.Attributes

class NoScrollView(context: Context,attributeSet: AttributeSet) :ScrollView(context,attributeSet){
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }
}