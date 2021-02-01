package com.dingyi.MyLuaApp.dialogs

import android.content.DialogInterface
import android.view.Gravity
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import com.androlua.LuaUtil
import com.dingyi.MyLuaApp.R
import com.dingyi.MyLuaApp.base.BaseActivity
import com.dingyi.MyLuaApp.activitys.EditorActivity
import com.dingyi.MyLuaApp.adapters.EditorFileListAdapter
import com.dingyi.MyLuaApp.base.BaseViewManager
import com.dingyi.MyLuaApp.databinding.ActivityEditorDialogNewFileBinding
import com.dingyi.MyLuaApp.databinding.ActivityEditorDialogNewFolderBinding
import com.dingyi.MyLuaApp.databinding.DialogEditorFabBinding
import com.dingyi.MyLuaApp.helper.EditorHelper
import com.dingyi.MyLuaApp.impl.TextWatcherImpl
import com.dingyi.MyLuaApp.manager.activity.EditorViewManager
import com.dingyi.MyLuaApp.utils.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread

class FileListDialog {

    private var bottomSheetDialog: BottomDialog? = null;
    private var context: BaseActivity<EditorViewManager>? = null;
    var binding: DialogEditorFabBinding? = null;
    private var util: EditorHelper? = null;

    fun init(context:  BaseActivity<EditorViewManager>, util: EditorHelper): FileListDialog {
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
            it.setHeight((context?.height!! * 0.4).toInt())//activity 5/2的高度
            it.window?.setDimAmount(0f)
            it.setCanceledOnTouchOutside(true)
            it.setCancelable(true)
            it.setMinHeight(0f)
            it.setRadius(0, context?.themeUtil?.backgroundColor!!)
            // it.window?.attributes?.height=(((context?.height?.times(0.4))?.toInt() ?: Int) as Int) //dialog的高度
            it.show()

        }


    }

    private fun showChooseTemplateDialog(activity:BaseActivity<EditorViewManager>, name:CharSequence, map: Map<CharSequence,String>) {
        with(activity) {
            val binding=ActivityEditorDialogNewFileBinding.inflate(LayoutInflater.from(this))
            val newProject = MyDialog(this, themeUtil)
                    .setTitle(getString(R.string.new_file_title,name))
                    .setView(binding.root)
                    .setPositiveButton(android.R.string.ok) { w: DialogInterface?, s: Int ->
                        val path=(this@FileListDialog.binding?.title?.text?.toString()!! + "/" + binding.file.text.toString()+getFileTemplateSuffix(name.toString()))
                        map[name]?.let { writeString(path, it) }
                        this@FileListDialog.util?.openFile(path)
                        refreshList()
                        showSnackBar((activity as EditorActivity).binding.root,R.string.create_file_success)
                    }
                    .setNeutralButton(android.R.string.cancel, null)
                    .show()

            val buttonView = newProject.getButton(AlertDialog.BUTTON1)

            binding.file.addTextChangedListener(object : TextWatcherImpl() {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    super.onTextChanged(s, start, before, count)
                    buttonView.isEnabled = !(this@FileListDialog.binding?.title?.text?.toString()!! + "/" + s.toString()+getFileTemplateSuffix(name.toString())).toFile().exists()

                }
            })


        }
    }

    private fun showChooseTemplateDialog(activity: BaseActivity<EditorViewManager>) {
        with(activity) {
            val nowChooseProject = AtomicInteger()
            val fileTemplateMaps = getFileTemplate(this)
            MyDialog(this, themeUtil)
                    .setTitle(R.string.new_file)
                    .setSingleChoiceItems(fileTemplateMaps.keys.toTypedArray(), 0) { dialog: DialogInterface?, which: Int -> nowChooseProject.set(which) }
                    .setPositiveButton(android.R.string.ok) { w: DialogInterface?, s: Int ->
                        //showCreateProjectDialog(projectTemplateNames[nowChooseProject.get()].toString(), nowChooseProject.get())
                        showChooseTemplateDialog(this,fileTemplateMaps.keys.toTypedArray()[nowChooseProject.get()],fileTemplateMaps)
                    }
                    .setNeutralButton(android.R.string.cancel, null)
                    .show()
        }
    }


    private fun showNewFolderDialog(activity: BaseActivity<EditorViewManager>) {
        with(activity) {
            val binding=ActivityEditorDialogNewFolderBinding.inflate(LayoutInflater.from(this))

            val newProject = MyDialog(this, themeUtil)
                    .setTitle(getString(R.string.new_dir))
                    .setView(binding.root)
                    .setPositiveButton(android.R.string.ok) { a: DialogInterface?, which: Int ->
                        (this@FileListDialog.binding?.title?.text?.toString()!! + "/" + binding.folder.text.toString()).toFile().mkdirs()
                        this@FileListDialog.binding?.let {
                            val adapter = it.list.adapter as EditorFileListAdapter
                            adapter.load(adapter.projectPath,(this@FileListDialog.binding?.title?.text?.toString()!! + "/" + binding.folder.text.toString()))
                        }
                    }
                    .show()

            val buttonView = newProject.getButton(AlertDialog.BUTTON1)

            binding.folder.addTextChangedListener(object : TextWatcherImpl() {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    super.onTextChanged(s, start, before, count)
                    buttonView.isEnabled = !(this@FileListDialog.binding?.title?.text?.toString()!! + "/" + s.toString()).toFile().exists()

                }
            })



        }

    }

    private fun refreshList() {
        this@FileListDialog.binding?.let {
            val adapter = it.list.adapter as EditorFileListAdapter
            adapter.load(adapter.projectPath, this@FileListDialog.binding?.title?.text?.toString()!!)
        }
    }

    private fun deleteFile(path:String) {
        MyDialog(context!!, context!!.themeUtil)
                .setTitle(R.string.delete_title)
                .setMessage(context!!.getString(R.string.delete_title_message,path.toFile().name))
                .setNeutralButton(android.R.string.cancel,null)
                .setPositiveButton(android.R.string.ok) { a, w ->
                    val dialog = createProgressBarDialog(context!!, context!!.getString(R.string.delete_progress_title), "")

                    thread {
                        LuaUtil.rmDir(path.toFile())
                        context!!.runOnUiThread {
                            dialog.dismiss()
                            refreshList()
                            showSnackBar((context as EditorActivity).binding.root,R.string.delete_file_success)
                        }
                    }
                }.show()
    }

    private fun initFileListView() {
        binding?.let {
            val adapter = context?.let { EditorFileListAdapter(it) };
            it.list.adapter = adapter
            adapter!!.dialog=this
            adapter!!.setOnItemClick(it.list)
            adapter.editorUtil=util
            it.close.setOnClickListener {
                bottomSheetDialog?.dismiss()
            }

            it.list.setOnItemLongClickListener { _, view,_,_ ->

                val popupMenuCompat=PopupMenu(context!!, view, Gravity.NO_GRAVITY, R.attr.popupMenuStyle, R.style.BasePopMenuStyle)
                popupMenuCompat.menuInflater.inflate(R.menu.editor_file_delete_menu, popupMenuCompat.menu)
                popupMenuCompat.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.delete -> deleteFile(this.binding?.title?.text?.toString()!! + "/" + (view.tag as EditorFileListAdapter.ViewHolder).title.text.toString())
                    }
                    return@setOnMenuItemClickListener true
                }

                popupMenuCompat.show()


                return@setOnItemLongClickListener true
            }

            it.more.setOnClickListener {
                val popupMenuCompat=PopupMenu(it.context, it, Gravity.NO_GRAVITY, R.attr.popupMenuStyle, R.style.BasePopMenuStyle)

                popupMenuCompat.menuInflater.inflate(R.menu.editor_file_menu, popupMenuCompat.menu)
                popupMenuCompat.setOnMenuItemClickListener { item->
                    when (item.itemId) {
                        R.id.new_dir -> showNewFolderDialog(context!!)
                        R.id.new_file ->showChooseTemplateDialog(context!!)
                    }
                    return@setOnMenuItemClickListener true
                }

                popupMenuCompat.show()
            }

            util?.let { util ->
                adapter!!.load(util.projectPath, util.nowOpenPath.toFile().parentFile.path)
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
