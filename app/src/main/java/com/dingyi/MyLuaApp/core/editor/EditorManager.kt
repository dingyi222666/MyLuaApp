package com.dingyi.MyLuaApp.core.editor

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import com.dingyi.MyLuaApp.R
import com.dingyi.MyLuaApp.base.BaseActivity
import com.dingyi.MyLuaApp.bean.ProjectInfo
import com.dingyi.MyLuaApp.core.project.ProjectManager
import com.dingyi.MyLuaApp.databinding.ActivityEditorBinding
import com.dingyi.MyLuaApp.utils.*
import com.dingyi.MyLuaApp.widget.views.Magnifier
import com.dingyi.editor.IEditor
import com.dingyi.editor.IEditorView
import com.dingyi.editor.lua.LuaEditor
import java.util.*
import kotlin.math.abs

@SuppressLint("ClickableViewAccessibility")
class EditorManager(private val activity: BaseActivity<*>,
                    private val info: ProjectInfo,
                    binding: ActivityEditorBinding) :IEditor{


    private val editors: MutableMap<String, IEditorView> =mutableMapOf()

    private val layout=binding.editorParent
    val projectManager= ProjectManager(activity, info)
    private val tableManager=EditorTableManager(binding.tabLayout)

    var openCallBack: (String)->Unit={}

    private val magnifier=Magnifier(activity,layout)

    private lateinit var nowEditor:IEditorView

    private val suffixList=arrayListOf("lua", "java", "gradle", "xml", "aly")
    init {
        tableManager.selectedTabCallBack={
            select(it)
        }


    }

    fun open(path: String) {
        if (!suffixList.contains(path.toFile().getSuffix())) {
            showSnackbar(layout, R.string.openFail)
            return
        }
        if (editors[projectManager.getShortPath(path)]!=null) {
            select(projectManager.getShortPath(path))
            return
        }
        val view=getEditorByPath(path)//拿到编辑器
        view.text = readString(path)//设置编辑器文字
        nowEditor=view//设置为现在的编辑器
        addView(view)//添加布局
        editors[projectManager.getShortPath(path)]=view
        openCallBack.invoke(projectManager.getShortPath(path))//打开回调
        projectManager.putOpenPath(path)
        tableManager.addTab(path.substring(info.path.length + 1))//添加tab
    }

    fun select(name: String) {
        editors[name]?.let {
            nowEditor=it//设置为现在的编辑器
            magnifier.nowView=it
            addView(it)
            openCallBack.invoke(name)
            tableManager.selectTab(name)
        }
    }

    fun openLast() {
        projectManager.getLastOpenPath()?.let { open(it) }
    }

    fun remove(str: String) {
        if (editors.containsKey(str)) {
            editors.remove(str)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun addView(view: IEditorView) {

        layout.removeAllViewsInLayout()
        layout.addView(view, LinearLayout.LayoutParams(-1, -1))
        magnifier.nowView=view
        magnifier.scale=1.25f

    }

    private fun getEditorByPath(path: String): IEditorView {
        return when (path.getSuffix().toLowerCase(Locale.ROOT)) {
            "lua","aly" -> LuaEditor(layout.context,magnifier)
             else -> LuaEditor(layout.context,magnifier)
        }
    }

    override fun getText(): String {
        return nowEditor.text
    }

    override fun setText(str: String?) {
       nowEditor.text =str
    }

    override fun paste(it: String) {
        nowEditor.paste(it)
    }

    override fun paste() {
        nowEditor.paste()
    }

    override fun undo() {
        nowEditor.undo()
    }

    override fun redo() {
        nowEditor.redo()
    }

    override fun format() {
        nowEditor.format()
    }

    override fun gotoLine() {
        nowEditor.gotoLine()
    }

    override fun gotoLine(i: Int) {
        nowEditor.gotoLine(i)
    }

    override fun search() {
        nowEditor.search()
    }

    override fun isSelected(): Boolean {
        return nowEditor.isSelected
    }
}