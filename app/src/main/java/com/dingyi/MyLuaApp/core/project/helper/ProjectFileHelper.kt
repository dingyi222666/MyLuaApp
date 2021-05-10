package com.dingyi.MyLuaApp.core.project.helper

import android.content.DialogInterface
import android.view.Gravity
import android.view.View

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.menu.MenuPopupHelper
import androidx.appcompat.widget.PopupMenu
import com.dingyi.MyLuaApp.R
import com.dingyi.MyLuaApp.base.BaseActivity
import com.dingyi.MyLuaApp.core.project.getFileTemplate
import com.dingyi.MyLuaApp.core.project.getFileTemplateSuffix
import com.dingyi.MyLuaApp.databinding.DialogLayoutInput1Binding
import com.dingyi.MyLuaApp.utils.*
import com.dingyi.MyLuaApp.widget.dialogs.InputDialog
import com.google.android.material.textfield.TextInputLayout

class ProjectFileHelper(
    private val mActivity: BaseActivity<*>,
    private val mCallBack: RefreshCallBack
) {


    private val fileTemplateMaps = getFileTemplate(mActivity)

    fun showFileMorePopupMenu(showView: View, dirPath: String) {
        val popupMenu = PopupMenu(mActivity, showView)
        popupMenu.inflate(R.menu.editor_file_more_menu)
        popupMenu.gravity = Gravity.END

        val helper = getPrivateField(popupMenu, "mPopup") as MenuPopupHelper

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.new_file -> showNewFileDialog(dirPath)
                R.id.new_dir -> showNewDirDialog(dirPath)
            }
            return@setOnMenuItemClickListener true
        }

        showView.post {
            getPrivateMethod(
                helper, "showPopup", arrayOf(
                    Integer.TYPE, Integer.TYPE,
                    //xOffset yOffset
                    java.lang.Boolean.TYPE, java.lang.Boolean.TYPE
                ), arrayOf(showView.width, -showView.height, true, true)
            )
        }

    }

    fun showFileSelectPopupMenu(filePath: String, showView: View, isHide: Boolean = false) {
        val popupMenu = PopupMenu(mActivity, showView)
        popupMenu.inflate(R.menu.editor_file_list_menu)

        if (filePath.toFile().isFile || isHide) {
            popupMenu.menu.findItem(R.id.new_dir).isVisible = false
            popupMenu.menu.findItem(R.id.new_file).isVisible = false
        }

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.new_file -> showNewFileDialog(filePath)
                R.id.new_dir -> showNewDirDialog(filePath)
                R.id.rename -> showFileRenameDialog(filePath)
            }
            return@setOnMenuItemClickListener true
        }

        val helper = getPrivateField(popupMenu, "mPopup") as MenuPopupHelper

        showView.post {
            getPrivateMethod(
                helper, "showPopup", arrayOf(
                    Integer.TYPE, Integer.TYPE,
                    //xOffset yOffset
                    java.lang.Boolean.TYPE, java.lang.Boolean.TYPE
                ), arrayOf(mActivity.dp2px(16), -showView.height, true, true)
            )
        }
    }

    private fun showNewFileDialog(path: String) {
        val arrays = fileTemplateMaps.keys.toTypedArray()
        var chooseIndex = 0
        InputDialog(mActivity)
            .setTitle(R.string.new_file)
            .setSingleChoiceItems(arrays, 0) { _, which ->
                chooseIndex = which
            }
            .setPositiveButton(android.R.string.ok) { _: DialogInterface?, _: Int ->
                showNewFileDialog(path, arrays[chooseIndex].toString())
            }
            .setNeutralButton(android.R.string.cancel, null)
            .show()

    }

    private fun showFileRenameDialog(path: String) {
        val mInputDialog = InputDialog(mActivity)
        val file = path.toFile()
        val mParentPath = file.parentFile?.path

        mInputDialog.setMode(InputDialog.Mode.INPUT_1)
            .setTitle(mActivity.getString(R.string.rename_file_format).format(file.name))
            .with {
                val parent: TextInputLayout = it.getBinding<DialogLayoutInput1Binding>().root
                parent.setHint(R.string.rename_file_hint)
                it.getBinding<DialogLayoutInput1Binding>().folder.setText(file.name)
            }
            .addListener(AlertDialog.BUTTON_POSITIVE, object : InputDialog.Listener {
                override fun isEnabled(text: String): Boolean {
                    return !"$mParentPath/$text".toFile().exists()
                }
            }, mInputDialog.getBinding<DialogLayoutInput1Binding>().folder)
            .setPositiveButton(android.R.string.ok) { _: DialogInterface?, _: Int ->
                val renamePath =
                    "$mParentPath/${mInputDialog.getBinding<DialogLayoutInput1Binding>().folder.text}"
                renameTo(path, renamePath)
                showSnackbar(mActivity.coordinatorLayout, R.string.rename_file_success)
                mCallBack.onRefresh(path)
            }
            .setNeutralButton(android.R.string.cancel, null)
            .show()
    }

    private fun showNewFileDialog(path: String, which: String) {
        val mInputDialog = InputDialog(mActivity)

        mInputDialog.setMode(InputDialog.Mode.INPUT_1)
            .setTitle(mActivity.getString(R.string.new_file_title).format(which))
            .with {
                val parent: TextInputLayout = it.getBinding<DialogLayoutInput1Binding>().root
                parent.setHint(R.string.new_file_hint)
            }
            .addListener(AlertDialog.BUTTON_POSITIVE, object : InputDialog.Listener {
                override fun isEnabled(text: String): Boolean {
                    return !"$path/$text${getFileTemplateSuffix(which)}".toFile().exists()
                }
            }, mInputDialog.getBinding<DialogLayoutInput1Binding>().folder)
            .setPositiveButton(android.R.string.ok) { _: DialogInterface?, _: Int ->
                val path =
                    "$path/${mInputDialog.getBinding<DialogLayoutInput1Binding>().folder.text}${
                        getFileTemplateSuffix(which)
                    }"
                path.toFile().createNewFile()
                fileTemplateMaps[which]?.let { writeString(path, it) }
                showSnackbar(mActivity.coordinatorLayout, R.string.create_file_success)
                mCallBack.onRefresh(path)
            }
            .setNeutralButton(android.R.string.cancel, null)
            .show()
    }

    private fun showNewDirDialog(path: String) {
        val mInputDialog = InputDialog(mActivity)

        mInputDialog.setMode(InputDialog.Mode.INPUT_1)
            .setTitle(mActivity.getString(R.string.new_dir))
            .with {
                val parent: TextInputLayout = it.getBinding<DialogLayoutInput1Binding>().root
                parent.setHint(R.string.new_folder_hint)
            }
            .addListener(AlertDialog.BUTTON_POSITIVE, object : InputDialog.Listener {
                override fun isEnabled(text: String): Boolean {
                    return !"$path/$text".toFile().exists()
                }
            }, mInputDialog.getBinding<DialogLayoutInput1Binding>().folder)
            .setPositiveButton(android.R.string.ok) { _: DialogInterface?, _: Int ->
                val path =
                    "$path/${mInputDialog.getBinding<DialogLayoutInput1Binding>().folder.text}"

                path.toFile().mkdirs()
                showSnackbar(mActivity.coordinatorLayout, R.string.create_dir_success)
                mCallBack.onRefresh(path)
            }
            .setNeutralButton(android.R.string.cancel, null)
            .show()
    }

    interface RefreshCallBack {
        fun onRefresh(path: String)
    }

}