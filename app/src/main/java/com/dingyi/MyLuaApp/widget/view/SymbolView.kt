package com.dingyi.MyLuaApp.widget.view

import android.content.Context
import android.util.AttributeSet
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import com.dingyi.MyLuaApp.MainApplication

/**
 * @author: dingyi
 * @date: 2021/8/8 18:48
 * @description:
 **/
class SymbolView(context: Context?, attrs: AttributeSet?) : HorizontalScrollView(context, attrs) {


    private val linearLayout = LinearLayout(context)

    private val symbols = mutableListOf<String>()

    private val _onClick={_:String -> }

    init {
        addView(linearLayout, -1, -1)

        readSymbol()

        symbols.forEach {

        }

    }

    fun readSymbol() {
        MainApplication.instance.getSharedPreferences("default", Context.MODE_PRIVATE)
            .getString("symbol", "( ) [ ] { } \" = : . , _ + - * / \\ % # ^ \$ ? & | < > ~ ; '")
            ?.apply {
                symbols.clear()
                symbols.addAll(split(" "))
            }
    }


}