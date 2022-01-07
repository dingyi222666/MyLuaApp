package com.dingyi.myluaapp.common.dialog.builder


import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.dingyi.myluaapp.common.dialog.BottomDialog
import com.dingyi.myluaapp.common.dialog.DialogClickListener
import com.dingyi.myluaapp.common.dialog.layout.BaseBottomDialogLayout
import com.dingyi.myluaapp.common.kts.getString


class BottomDialogBuilder(private val context: Context) {


    private val bottomDialog = BottomDialog(context)

    private val params = BottomDialog.BottomDialogCreateParams()

    fun setContentView(resId: Int): BottomDialogBuilder = this.apply {
        bottomDialog.setContentView(resId)
    }


    fun setItems(items: List<Pair<String, Any>>, click: DialogClickListener): BottomDialogBuilder =
        this.apply {
            params.items = items
            params.itemsClick = click
        }

    fun setSingleChoiceItems(
        items: List<Pair<String, Any>>,
        defaultChoice: Int = 0,
        click: DialogClickListener = defaultClick
    ): BottomDialogBuilder = this.apply {
        params.items = items
        params.choiceType = BottomDialog.BottomDialogCreateParams.ChoiceType.SingleChoice
        params.defaultChoiceItem = intArrayOf(defaultChoice)
        params.itemsClick = click
    }

    fun setTitle(title: String): BottomDialogBuilder = this.apply {
        params.title = title
    }

    private val defaultClick: DialogClickListener = { _, _ -> }

    fun setTitle(resId: Int): BottomDialogBuilder = setTitle(resId.getString())

    fun setNegativeButton(
        buttonText: String,
        click: DialogClickListener = defaultClick
    ): BottomDialogBuilder =
        this.apply {
            params.apply {
                negativeButtonClick = click
                negativeButtonText = buttonText
            }
        }

    fun setPositiveButton(
        buttonText: String,
        click: DialogClickListener = defaultClick
    ): BottomDialogBuilder =
        this.apply {
            params.apply {
                positiveButtonClick = click
                positiveButtonText = buttonText
            }
        }

    fun setNeutralButton(
        buttonText: String,
        click: DialogClickListener = defaultClick
    ): BottomDialogBuilder =
        this.apply {
            params.apply {
                neutralButtonClick = click
                neutralButtonText = buttonText
            }
        }


    fun show(): BottomDialog = bottomDialog.apply {
        bottomDialog.show(params)

    }

    fun setDialogLayout(layout: BaseBottomDialogLayout): BottomDialogBuilder {
        bottomDialog.setContentView(layout)
        return this
    }

    fun setMessage(message: String): BottomDialogBuilder = this.apply {
        params.message = message
    }

    fun setMessage(id: Int): BottomDialogBuilder = setMessage(id.getString())
    fun setDefaultText(name: String): BottomDialogBuilder = this.apply {
        params.defaultText = name
    }


    companion object {
        fun with(activity: Context) = BottomDialogBuilder(activity)
    }

}