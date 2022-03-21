package com.dingyi.myluaapp.common.dialog

import android.content.Context
import com.dingyi.myluaapp.common.dialog.helper.BaseBottomDialogLayoutHelper
import com.dingyi.myluaapp.common.dialog.layout.BaseBottomDialogLayout
import com.dingyi.myluaapp.common.ktx.dp
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlin.properties.Delegates

class BottomDialog(context: Context) : BottomSheetDialog(context) {

    private var _layoutHelper by Delegates.notNull<BaseBottomDialogLayoutHelper>()

    val layoutHelper: BaseBottomDialogLayoutHelper
        get() = _layoutHelper

    fun setContentView(bottomDialogLayout: BaseBottomDialogLayout) {
        val rootView = bottomDialogLayout.getRootView(layoutInflater)
        _layoutHelper = bottomDialogLayout.getLayoutHelper(rootView, this)
        _layoutHelper.getCloseView().setOnClickListener {
            dismiss()
        }
        setContentView(rootView)
    }

    fun show(params: BottomDialogCreateParams) {
        _layoutHelper.apply(params)
        super.show()
        _layoutHelper.rootView.post {
            behavior.peekHeight = _layoutHelper.rootView.height - 1.dp
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    class BottomDialogCreateParams {

        enum class ChoiceType {
            SingleChoice, MultiChoice, None
        }

        var defaultText: String = ""
        private val defaultClick: DialogClickListener =
            { _: BaseBottomDialogLayoutHelper, _: Pair<String, Any>? -> }
        var title = ""
        var message = ""
        var negativeButtonText = ""
        var negativeButtonClick = defaultClick
        var positiveButtonText = ""
        var positiveButtonClick = defaultClick
        var neutralButtonText = ""
        var neutralButtonClick = defaultClick
        var items = listOf<Pair<String, Any>>()
        var itemsClick = defaultClick
        var choiceType = ChoiceType.None
        var defaultChoiceItem = intArrayOf(0)
    }


}

typealias DialogClickListener = (BaseBottomDialogLayoutHelper, Pair<String, Any>?) -> Unit