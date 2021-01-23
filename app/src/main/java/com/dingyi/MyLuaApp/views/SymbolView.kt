package com.dingyi.MyLuaApp.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.TextView
import com.dingyi.MyLuaApp.R

import com.dingyi.MyLuaApp.utils.EditorUtil
import com.dingyi.MyLuaApp.utils.get

class SymbolView(context: Context,attributeSet: AttributeSet): LinearLayout(context,attributeSet) {

    lateinit var util:EditorUtil;

    init {
        val symbols=get(context,"symbol","symbol","( ) [ ] { } = \" : . , _ + - * / \\ % # ^ \$ ? & | < > ~ ; '\n")?.split(" ");

        kotlin.runCatching {
            (this.parent as HorizontalScrollView).isHorizontalFadingEdgeEnabled = false
        }
        symbols?.forEach {
            val root=LayoutInflater.from(context).inflate(R.layout.view_symblo_layout,this,false)
            val text=root.findViewById<TextView>(R.id.text)
            text.text=it
            root.setOnClickListener { _ ->
                util.paste(it)
            }

            addView(root)
        }

    }


}