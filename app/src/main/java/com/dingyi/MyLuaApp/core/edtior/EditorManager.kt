package com.dingyi.MyLuaApp.core.edtior

import android.widget.LinearLayout
import com.dingyi.MyLuaApp.base.BaseActivity
import com.dingyi.MyLuaApp.bean.ProjectInfo
import com.dingyi.MyLuaApp.core.project.ProjectManager
import com.dingyi.MyLuaApp.databinding.ActivityEditorBinding
import com.dingyi.MyLuaApp.utils.getSuffix
import com.dingyi.MyLuaApp.utils.printDebug
import com.dingyi.MyLuaApp.utils.readString
import com.dingyi.editor.IEditor
import com.dingyi.editor.IEditorView
import com.dingyi.editor.lua.LuaEditor
import java.util.*

class EditorManager(activity: BaseActivity<*>,
                    private val info: ProjectInfo,
                    binding: ActivityEditorBinding) {



    private val editors: MutableMap<String,IEditorView> =mutableMapOf()

    private val layout=binding.editorParent
    private val projectManager= ProjectManager(activity,info)

    private val tableManager=EditorTableManager(binding.tabLayout)

    private lateinit var nowEditor:IEditorView;

    init {
        tableManager.selectedTabCallBack={
            select(it)
        }
    }

    fun open(path:String) {
        val view=getEditorByPath(path)
        view.text = readString(path)
        nowEditor=view
        addView(nowEditor)
        editors[projectManager.getShortPath(path)]=nowEditor
        printDebug(projectManager.getShortPath(path))
        projectManager.putOpenPath(path)
        tableManager.addTab(path.substring(info.path.length + 1))
    }

    fun select(name: String) {
        editors[name]?.let { addView(it) }
    }

    fun openLast() {
        projectManager.getLastOpenPath()?.let { open(it) }
    }

    fun remove(str:String) {
        if (editors.containsKey(str)) {
            editors.remove(str)
        }
    }

    private fun addView(view:IEditorView) {
        layout.removeAllViewsInLayout()
        layout.addView(view,LinearLayout.LayoutParams(-1,-1))
    }

    private fun getEditorByPath(path: String): IEditorView {
        return when (path.getSuffix().toLowerCase()) {
            "lua" -> LuaEditor(layout.context)
             else -> LuaEditor(layout.context)
        }
    }
}