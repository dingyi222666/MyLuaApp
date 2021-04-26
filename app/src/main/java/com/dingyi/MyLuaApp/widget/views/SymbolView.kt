package com.dingyi.MyLuaApp.widget.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.TextView
import com.dingyi.MyLuaApp.R
import com.dingyi.MyLuaApp.utils.SharedPreferencesUtil
import com.dingyi.editor.IEditor
import com.dingyi.editor.IEditorView
import kotlin.properties.Delegates

class SymbolView(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    var view by Delegates.notNull<IEditor>()

    init {
        val symbols = SharedPreferencesUtil.getDefaultSharedPreferencesUtil(context!!).get( "symbol", "( ) [ ] { } = \" : . , _ + - * / \\ % # ^ \$ ? & | < > ~ ; '\n")?.split(" ")


        kotlin.runCatching {
            (this.parent as HorizontalScrollView).isHorizontalFadingEdgeEnabled = false
        }
        symbols?.forEach {
            val root = LayoutInflater.from(context).inflate(R.layout.view_symbol_layout, this, false)
            val text = root.findViewById<TextView>(R.id.text)
            text.text=it
            root.setOnClickListener { _ ->
                view.paste(it)
            }

            addView(root)
        }
    }

}