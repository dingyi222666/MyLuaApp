package com.dingyi.myluaapp.editor.highlight

import io.github.rosemoe.sora.text.CharPosition

/**
 * The edit content of this edit event
 */
class IncrementalEditContent {

    /**
     * Edit action
     */
    var actionType = TYPE.EMPTY

    /**
     * The start position of this edit event
     */
    var startPosition = CharPosition()

    /**
     * The end position of this edit event
     */
    var endPosition = CharPosition()

    /**
     * The action content or this edit event
     */
    var actionContent: CharSequence = ""

    enum class TYPE {
        /**
         * editor do delete content
         */
        DELETE,

        /**
         * editor do insert content
         */
        INSERT,

        /**
         *  editor did nothing
         */
        EMPTY
    }


}