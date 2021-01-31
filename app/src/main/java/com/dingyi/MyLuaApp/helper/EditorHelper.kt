package com.dingyi.MyLuaApp.helper

import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.androlua.IEditor
import com.androlua.MyLuaEditor
import com.dingyi.MyLuaApp.R
import com.dingyi.MyLuaApp.activitys.EditorActivity
import com.dingyi.MyLuaApp.bean.ProjectInfo
import com.dingyi.MyLuaApp.utils.*
import java.io.File

class EditorHelper(private val context: AppCompatActivity) : IEditor {
    private lateinit var editor: View;
    private lateinit var viewGroup: LinearLayout
    lateinit var projectPath: String;
    lateinit var nowOpenPath: String;
    private val listView = mutableMapOf<String, View>()
    private var openFileListenerList = mutableListOf<OpenFileListener>();


    fun initParentView(viewGroup: LinearLayout): EditorHelper {
        this.viewGroup = viewGroup
        return this;
    }

    private fun addEditor(view: View) {
        if (view.layoutParams != null) {
            view.layoutParams = LinearLayout.LayoutParams(-1, -1)
        }
        editor = view
        listView[getNowOpenPathName()] = view
        viewGroup.removeAllViewsInLayout()
        viewGroup.addView(view)
    }

    private fun newEditor(path: String) {

        when (getSuffix(path)) {
            "lua", "aly" -> addEditor(MyLuaEditor(context))
        }
    }

    fun openProject(projectPath: String): EditorHelper {
        this.projectPath = projectPath

        val defaultPath = getDefaultPath(projectPath)
        val lastOpenPath = get(context, "editorPath", projectPath, defaultPath)
        if (lastOpenPath != null) {
            openProject(projectPath, lastOpenPath)
        }
        return this
    }

    fun openProject(projectInfo: ProjectInfo): EditorHelper {
        projectInfo.path?.let { this.openProject(it) }
        return this;
    }

    fun openProject(projectPath: String, allPath: String) {
        openFile(allPath)

    }

    fun openFile(path: String) {
        when (getSuffix(path)) {
            "lua", "aly" -> {
            }
            else -> {
                kotlin.runCatching {
                    (context as EditorActivity).dialog.dismiss()
                }
                showSnackBar(viewGroup, R.string.openFail)
                return
            }
        }
        put(context, "editorPath", projectPath, path)
        this.nowOpenPath = path
        newEditor(path)
        if (path.toFile().isFile) {
            text = readString(path)
            clickOpenFileListener(path)
        } else {
            text = readString(getDefaultPath(projectPath))
            clickOpenFileListener(getDefaultPath(projectPath))
        }

    }

    fun refresh(string: String) {
        if (listView[string] != null) {
           setTextWithView(listView[string]!!, readString("$projectPath/$string"))
        }
    }

    fun addOpenFileListener(openFileListener: OpenFileListener) {
        openFileListenerList.add(openFileListener)
    }

    private fun clickOpenFileListener(path: String) {
        openFileListenerList.forEach {
            it.openFile(path)
        }
    }

    fun getNowOpenPathName(): String {
        return nowOpenPath.substring(projectPath.length + 1, nowOpenPath.length)
    }

    fun select(position: String) {
        val nowView = listView[position]

        nowOpenPath = projectPath + File.separator + position

        if (nowView != null) {
            addEditor(nowView)
        }
    }


    private fun getTextWithView(view: View): String {
        if (view is MyLuaEditor) {
            return view.text
        }
        return ""
    }

    private fun setTextWithView(view: View, string: String) {
        if (view is MyLuaEditor) {
            view.text = string
        }

    }

    fun save() {
        listView.forEach {
            writeString(projectPath + File.separator + it.key, getTextWithView(it.value))
        }
    }

    fun save(name:String) {
        listView[name]?.let {
            writeString(projectPath + File.separator + name, getTextWithView(it))
        }
    }

    fun remove(name: String) {
        listView[name]?.let {
            if (it==editor) {
                save(name)
                viewGroup.removeAllViews()
            }
            listView.remove(name)
        }
    }

    interface OpenFileListener {
        fun openFile(file: String)
    }

    fun canCheckError(): Boolean {
        if (editor is MyLuaEditor) {
            return true
        }
        return false
    }

    override fun getError(): String {
        if (editor is MyLuaEditor) {
            return (editor as MyLuaEditor).error
        }
        return ""
    }

    override fun getText(): String {
        if (editor is MyLuaEditor) {
            return (editor as MyLuaEditor).text.toString()
        }
        return ""
    }

    override fun setText(readString: String) {
        if (editor is MyLuaEditor) {
            (editor as MyLuaEditor).text = readString;
        }
    }

    override fun paste(it: String) {
        if (editor is MyLuaEditor) {
            (editor as MyLuaEditor).paste(it)
        }
    }

    override fun paste() {
        if (editor is MyLuaEditor) {
            (editor as MyLuaEditor).paste()
        }
    }

    override fun undo() {
        if (editor is MyLuaEditor) {
            (editor as MyLuaEditor).undo();
        }
    }

    override fun redo() {
        if (editor is MyLuaEditor) {
            (editor as MyLuaEditor).redo();
        }
    }

    override fun format() {
        if (editor is MyLuaEditor) {
            (editor as MyLuaEditor).format();
        }
    }

    override fun gotoLine() {
        if (editor is MyLuaEditor) {
            (editor as MyLuaEditor).gotoLine();
        }
    }

    override fun gotoLine(i: Int) {
        if (editor is MyLuaEditor) {
            (editor as MyLuaEditor).gotoLine(i);
        }
    }

    override fun search() {
        if (editor is MyLuaEditor) {
            (editor as MyLuaEditor).search()
        }
    }


}