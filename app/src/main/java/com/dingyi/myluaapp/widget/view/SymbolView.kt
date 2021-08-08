package com.dingyi.myluaapp.widget.view

import android.content.Context
import android.util.AttributeSet
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import com.dingyi.myluaapp.MainApplication
import com.dingyi.myluaapp.common.kts.layoutInflater
import com.dingyi.myluaapp.databinding.LayoutItemSymbolBinding

/**
 * @author: dingyi
 * @date: 2021/8/8 18:48
 * @description:
 **/
class SymbolView(context: Context?, attrs: AttributeSet?) : HorizontalScrollView(context, attrs) {


    private val linearLayout = LinearLayout(context)

    private val symbols = mutableListOf<String>()

    private val _onClick = { _: String -> }

    init {
        linearLayout.elevation = elevation
        addView(linearLayout, -1, -1)
        readSymbol()
        symbols.forEach {
            val binding = LayoutItemSymbolBinding.inflate(layoutInflater, linearLayout, false)
            binding.text.text = it
            binding.root.setOnClickListener { _ ->
                _onClick(it)
            }
            linearLayout.addView(binding.root)
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