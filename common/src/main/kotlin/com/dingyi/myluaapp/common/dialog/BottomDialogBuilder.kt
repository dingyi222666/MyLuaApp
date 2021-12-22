package com.dingyi.myluaapp.common.dialog


import android.util.Log
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import android.widget.FrameLayout
import androidx.core.view.get
import com.dingyi.myluaapp.common.R
import com.dingyi.myluaapp.common.kts.convertObject
import com.dingyi.myluaapp.common.kts.showToast


class BottomDialogBuilder(private val activity: AppCompatActivity) {


    private val bottomDialog = BottomSheetDialog(activity)


    fun setContentView(resId: Int): BottomDialogBuilder = this.apply {
        bottomDialog.setContentView(resId)
    }


    fun show(): BottomSheetDialog = bottomDialog.apply {
        bottomDialog.show()

    }


    companion object {
        fun with(activity: AppCompatActivity) = BottomDialogBuilder(activity)
    }

}