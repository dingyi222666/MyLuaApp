package com.dingyi.MyLuaApp.core.editor

import android.annotation.SuppressLint
import android.widget.LinearLayout
import com.dingyi.MyLuaApp.R
import com.dingyi.MyLuaApp.base.BaseActivity
import com.dingyi.MyLuaApp.beans.ProjectInfo
import com.dingyi.MyLuaApp.core.project.manager.ProjectManager
import com.dingyi.MyLuaApp.databinding.ActivityEditorBinding
import com.dingyi.MyLuaApp.utils.*
import com.dingyi.MyLuaApp.widget.views.Magnifier
import com.dingyi.editor.IEditor
import com.dingyi.editor.IEditorView
import com.dingyi.editor.lua.LuaEditor
import java.util.*

@SuppressLint("ClickableViewAccessibility")
class EditorManager(
    mActivity: BaseActivity<*>,
    private val mProjectInfo: ProjectInfo,
    binding: ActivityEditorBinding
) : IEditor {


    private val mEditors: MutableMap<String, IEditorView> = mutableMapOf()

    private val mLayout = binding.editorParent
    val mProjectManager = ProjectManager(mActivity, mProjectInfo)
    private val mEditorTableManager = EditorTableLayoutManager(binding.tabLayout)

    var openFileCallBack: (String) -> Unit = {}

    private val mMagnifier = Magnifier(mActivity, mLayout)

    private lateinit var mNowEditor: IEditorView

    private val mSuffixList = arrayListOf("lua", "java", "gradle", "xml", "aly")

    init {
        mEditorTableManager.selectedTabCallBack = {
            select(it)
        }

    }

    fun open(path: String) {
        if (!mSuffixList.contains(path.toFile().getSuffix())) {
            showSnackbar(mLayout, R.string.openFail)
            return
        }
        if (mEditors[mProjectManager.getShortPath(path)] != null) {
            select(mProjectManager.getShortPath(path))
            return
        }

        val view = getEditorByPath(path)//拿到编辑器
        view.text = readString(path)//设置编辑器文字
        mNowEditor = view//设置为现在的编辑器
        addView(view)//添加布局
        mEditors[mProjectManager.getShortPath(path)] = view
        openFileCallBack.invoke(mProjectManager.getShortPath(path))//打开回调
        mProjectManager.putOpenPath(path)
        mEditorTableManager.addTab(path.substring(mProjectInfo.projectPath.length + 1))//添加tab
    }

    fun select(name: String) {
        mEditors[name]?.let {
            mNowEditor = it//设置为现在的编辑器
            mMagnifier.nowView = it
            addView(it)
            openFileCallBack.invoke(name)
            mEditorTableManager.selectTab(name)
        }
    }

    fun openLast() {
        mProjectManager.getLastOpenPath()?.let { open(it) }
    }

    fun saveAll() {
        mEditors.forEach {
            save(it.key)
        }
    }

    fun save(path: String) {
        mEditors[path]?.let { writeString(mProjectManager.getAllPath(path), it.text) }
    }

    fun reLoad(path:String) {
        mEditors[path]?.text= readString(path)
    }

    fun remove(str: String) {

        if (mEditors.containsKey(str)) {
            mEditors.remove(str)
            mEditorTableManager.removeTab(str)
        }
    }


    fun replace(lastSelectPath: String, path: String) {
        val targetView=mEditors[mProjectManager.getShortPath(lastSelectPath)]
        mEditorTableManager.renameTab(mProjectManager.getShortPath(lastSelectPath),mProjectManager.getShortPath(path))
        mEditors[mProjectManager.getShortPath(path)]=targetView!!
        mEditors.remove(mProjectManager.getShortPath(lastSelectPath))
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun addView(view: IEditorView) {

        mLayout.removeAllViewsInLayout()
        mLayout.addView(view, LinearLayout.LayoutParams(-1, -1))
        mMagnifier.nowView = view
        mMagnifier.scale = 1.25f

    }

    private fun getEditorByPath(path: String): IEditorView {
        return when (path.getSuffix().toLowerCase(Locale.ROOT)) {
            "lua", "aly" -> LuaEditor(mLayout.context, mMagnifier)
            else -> LuaEditor(mLayout.context, mMagnifier)
        }
    }

    override fun getText(): String {
        return mNowEditor.text
    }

    override fun setText(str: String?) {
        mNowEditor.text = str
    }

    override fun paste(it: String) {
        mNowEditor.paste(it)
    }

    override fun paste() {
        mNowEditor.paste()
    }

    override fun undo() {
        mNowEditor.undo()
    }

    override fun redo() {
        mNowEditor.redo()
    }

    override fun format() {
        mNowEditor.format()
    }

    override fun gotoLine() {
        mNowEditor.gotoLine()
    }

    override fun gotoLine(i: Int) {
        mNowEditor.gotoLine(i)
    }

    override fun search() {
        mNowEditor.search()
    }

    override fun isSelected(): Boolean {
        return mNowEditor.isSelected
    }

}