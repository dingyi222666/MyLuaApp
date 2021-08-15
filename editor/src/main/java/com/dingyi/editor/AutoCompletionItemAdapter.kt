package com.dingyi.editor

import android.content.res.Resources
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.dingyi.editor.kts.dp
import io.github.rosemoe.editor.widget.EditorCompletionAdapter


/**
 * @author: dingyi
 * @date: 2021/8/15 6:36
 * @description:
 **/
class AutoCompletionItemAdapter : EditorCompletionAdapter() {


    class ViewHolder(val itemView: View) {
        val title: TextView = itemView.findViewById(R.id.result_item_label)
        val desc: TextView = itemView.findViewById(R.id.result_item_desc)
        val image: ImageView = itemView.findViewById(R.id.result_item_image)
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup,
        isCurrentCursorPosition: Boolean
    ): View {

        var rootView = convertView

        if (rootView == null) {
            rootView = LayoutInflater.from(parent.context)
                .inflate(R.layout.editor_auto_completion_item, parent, false)
        }
        rootView?.let {
            if (it.tag == null) {
                it.tag = ViewHolder(it)
            }
            (it.tag as ViewHolder).apply {

               if (isCurrentCursorPosition) {
                    itemView.setBackgroundColor(0xffdddddd.toInt());
                } else {
                    itemView.setBackgroundColor(0xffffffff.toInt());
                }

                title.text = getItem(position).label.split(".").run { get(size - 1) }
                desc.text = getItem(position).desc
                image.setImageDrawable(getItem(position).icon)
            }
        }

        return rootView ?: View(parent.context)
    }

    override fun getItemHeight(): Int {
        return 32.dp
    }

}