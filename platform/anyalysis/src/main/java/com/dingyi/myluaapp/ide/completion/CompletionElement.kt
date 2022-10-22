package com.dingyi.myluaapp.ide.completion

interface CompletionElement {
    /**
     * @return the string which will be inserted into the editor when this lookup element is chosen
     */

     fun getString(): String

    /**
     * Performs changes after the current lookup element is chosen by the user.
     *
     *
     *
     * When this method is invoked, the lookup string is already inserted into the editor.
     * In addition, the document is committed, unless [.requiresCommittedDocuments] returns false.
     *
     *
     *
     * This method is invoked inside a write action. If you need to show dialogs,
     * please do that inside [InsertionContext.setLaterRunnable].
     *
     */
    fun handleInsert(context: InsertionContext) {}
}