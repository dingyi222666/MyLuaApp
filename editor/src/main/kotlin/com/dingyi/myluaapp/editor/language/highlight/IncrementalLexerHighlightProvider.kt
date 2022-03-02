package com.dingyi.myluaapp.editor.language.highlight

import android.os.Bundle
import io.github.rosemoe.sora.lang.analysis.AnalyzeManager
import io.github.rosemoe.sora.lang.analysis.StyleReceiver
import io.github.rosemoe.sora.lang.styling.Styles
import io.github.rosemoe.sora.text.CharPosition
import io.github.rosemoe.sora.text.ContentReference

abstract class IncrementalLexerHighlightProvider:AnalyzeManager {

    private var incrementalContent:IncrementalEditContent? = IncrementalEditContent()

    private var reference: ContentReference? = null


    private var extraArguments: Bundle? = null

    private  var receiver: StyleReceiver? = null

    override fun reset(content: ContentReference, extraArguments: Bundle) {
        reference = content
        this.extraArguments = extraArguments
        rerun()
    }

    override fun setReceiver(receiver: StyleReceiver?) {
        this.receiver = receiver
    }

    override fun delete(start: CharPosition, end: CharPosition, deletedContent: CharSequence) {
        incrementalContent?.apply {
            actionType = IncrementalEditContent.TYPE.DELETE
            startPosition = start
            endPosition = end
            actionContent = deletedContent
        }
    }

    override fun insert(start: CharPosition, end: CharPosition, insertedContent: CharSequence) {
        incrementalContent?.apply {
            actionType = IncrementalEditContent.TYPE.INSERT
            startPosition = start
            endPosition = end
            actionContent = insertedContent
        }
    }

    fun update(styles: Styles) {
        receiver?.setStyles(this,styles)
    }



    override fun rerun() {

    }
}