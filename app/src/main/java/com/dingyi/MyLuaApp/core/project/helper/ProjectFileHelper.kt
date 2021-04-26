package com.dingyi.MyLuaApp.core.project.helper

import android.content.DialogInterface
import android.view.Gravity
import android.view.View
import androidx.appcompat.view.menu.MenuPopupHelper
import androidx.appcompat.widget.PopupMenu
import com.dingyi.MyLuaApp.R
import com.dingyi.MyLuaApp.base.BaseActivity
import com.dingyi.MyLuaApp.databinding.DialogLayoutInput1Binding
import com.dingyi.MyLuaApp.utils.getPrivateField
import com.dingyi.MyLuaApp.utils.getPrivateMethod
import com.dingyi.MyLuaApp.utils.toFile
import com.dingyi.MyLuaApp.widget.dialogs.InputDialog

class ProjectFileHelper(private val mActivity: BaseActivity<*>,private val mCallBack: RefreshCallBack) {



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
            getPrivateMethod(helper, "showPopup", arrayOf(Integer.TYPE, Integer.TYPE,
                    //xOffset yOffset
                    java.lang.Boolean.TYPE, java.lang.Boolean.TYPE), arrayOf(showView.width, -showView.height, true, true))
        }

    }

    private fun showNewFileDialog(path: String) {

    }

    private fun showNewDirDialog(path: String) {
        val mInputDialog = InputDialog(mActivity)

        mInputDialog.setMode(InputDialog.Mode.INPUT_1)
                .setTitle(mActivity.getString(R.string.new_dir))
                .setPositiveButton(android.R.string.ok) { _: DialogInterface?, _: Int ->
                    val path="$path/${mInputDialog.getBinding<DialogLayoutInput1Binding>().folder.text}"
                    path.toFile().mkdirs()
                    mCallBack.onRefresh(path)
                }
                .show()
    }

    interface RefreshCallBack {
        fun onRefresh(path: String)
    }

}