package com.dingyi.MyLuaApp.dialogs

import android.view.LayoutInflater
import com.dingyi.MyLuaApp.activitys.BaseActivity
import com.dingyi.MyLuaApp.adapters.EditorFileListAdapter
import com.dingyi.MyLuaApp.databinding.DialogEditorFabBinding

import com.dingyi.MyLuaApp.utils.EditorUtil

import com.dingyi.MyLuaApp.utils.toFile

class FileListDialog {

    private var bottomSheetDialog: BottomDialog? = null;
    private var context: BaseActivity? = null;
    private var binding: DialogEditorFabBinding? = null;
    private var util: EditorUtil? = null;

    fun init(context: BaseActivity, util: EditorUtil): FileListDialog {
        if (bottomSheetDialog == null) {
            bottomSheetDialog = BottomDialog(context)
            binding = DialogEditorFabBinding.inflate(LayoutInflater.from(context))
        }
        this.util = util
        this.context = context
        return this
    }


    fun show() {
        initView();
        bottomSheetDialog?.let {
            it.setView(binding!!.root)
            //it.setMinHeight(0f)
            it.setWidth(context?.width!!)
            it.setHeight((context?.height!!*0.4).toInt())//activity 5/2的高度
            it.window?.setDimAmount(0f)
            it.setCanceledOnTouchOutside(true)
            it.setCancelable(true)
            it.setMinHeight(0f)
            it.setRadius(0,context?.themeUtil?.backgroundColor!!)
            // it.window?.attributes?.height=(((context?.height?.times(0.4))?.toInt() ?: Int) as Int) //dialog的高度
            it.show()

        }


    }

    private fun initFileListView() {
        binding?.let {
            val adapter = context?.let { EditorFileListAdapter(it) };
            it.list.adapter = adapter
            adapter!!.setOnItemClick(it.list)
            adapter.editorUtil=util
            it.close.setOnClickListener {
                bottomSheetDialog?.dismiss()
            }

            it.more.setOnClickListener {
                //val popupMenuCompat=Pop
            }

            util?.let { util ->
                adapter!!.load(util.projectPath,util.nowOpenPath.toFile().parentFile.path)
                it.title.text = util.nowOpenPath.toFile().parentFile.path
            }
        }
    }

    private fun initView() {
        initFileListView()
    }

    fun dismiss() {
        bottomSheetDialog?.dismiss()
    }
}
