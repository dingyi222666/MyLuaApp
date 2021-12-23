package com.dingyi.myluaapp.common.dialog.builder


import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dingyi.myluaapp.common.dialog.BottomDialog
import com.dingyi.myluaapp.common.dialog.layout.BaseBottomDialogLayout
import com.google.android.material.bottomsheet.BottomSheetDialog


class BottomDialogBuilder(private val activity: AppCompatActivity) {
    enum class TYPE {
        DEFAULT_INPUT
    }



    private val bottomDialog = BottomDialog(activity)


    fun setContentView(resId: Int): BottomDialogBuilder = this.apply {
        bottomDialog.setContentView(resId)
    }


    fun show(): BottomDialog = bottomDialog.apply {
        bottomDialog.show()
    }

    fun setDialogLayout(layout: BaseBottomDialogLayout): BottomDialogBuilder {
        bottomDialog.setContentView(layout)
        return this
    }


    companion object {
        fun with(activity: AppCompatActivity) = BottomDialogBuilder(activity)
    }

}