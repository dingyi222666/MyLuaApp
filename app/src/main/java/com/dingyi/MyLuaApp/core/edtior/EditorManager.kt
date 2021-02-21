package com.dingyi.MyLuaApp.core.edtior

import android.widget.LinearLayout
import com.dingyi.MyLuaApp.base.BaseActivity
import com.dingyi.MyLuaApp.bean.ProjectInfo
import com.dingyi.MyLuaApp.core.project.ProjectManager
import com.dingyi.MyLuaApp.utils.getSuffix
import com.dingyi.MyLuaApp.utils.readString
import com.dingyi.editor.IEditor
import com.dingyi.editor.IEditorView
import com.dingyi.editor.lua.LuaEditor
import java.util.*

class EditorManager(private val activity: BaseActivity<*>,
                    private val info: ProjectInfo,
                    private val layout: LinearLayout) {



    private val editors: MutableList<IEditorView> =mutableListOf()

    private val projectManager= ProjectManager(activity,info)

    private lateinit var nowEditor:IEditorView;

    fun open(path:String) {
        val view=getEditorByPath(path)
        view.text = readString(path)
        nowEditor=view
        addView(nowEditor)
        editors.add(nowEditor)
        projectManager.putOpenPath(path)
    }


    fun openLast() {
        projectManager.getLastOpenPath()?.let { open(it) }
    }

    private fun addView(view:IEditorView) {
        layout.removeAllViewsInLayout()
        layout.addView(view,LinearLayout.LayoutParams(-1,-1))
    }

    private fun getEditorByPath(path: String): IEditorView {
        return when (path.getSuffix()) {
            "Lua" -> LuaEditor(layout.context)
            else -> LuaEditor(layout.context)
        }
    }
}