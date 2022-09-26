package com.dingyi.myluaapp.openapi.editor


import com.dingyi.myluaapp.openapi.Disposable
import com.dingyi.myluaapp.openapi.editor.event.DocumentListener
import org.jetbrains.annotations.Contract
import org.jetbrains.annotations.NotNull



interface Document {

    companion object {

        var EMPTY_ARRAY = arrayOfNulls<Document>(0)

    }

    /**
     * Retrieves a copy of the document content. For obvious performance reasons use
     * [.getCharsSequence] whenever it's possible.
     *
     * @return document content.
     */

    @Contract(pure = true)
    fun getText(): String {
        return getImmutableCharSequence().toString()
    }


    @Contract(pure = true)
    fun getText(range: TextRange): String {
        return range.substring(getText())
    }

    /**
     * Use this method instead of [.getText] if you do not need to create a copy of the content.
     * Content represented by returned CharSequence is subject to change whenever document is modified via delete/replace/insertString method
     * calls. It is necessary to obtain Application.runWriteAction() to modify content of the document though so threading issues won't
     * arise.
     *
     * @return inplace document content.
     * @see .getTextLength
     */
    @Contract(pure = true)

    fun getCharsSequence(): CharSequence {
        return getImmutableCharSequence()
    }

    /**
     * @return a char sequence representing document content that's guaranteed to be immutable. No read- or write-action is necessary.
     * @see com.intellij.util.text.ImmutableCharSequence
     */

    @Contract(pure = true)
    fun getImmutableCharSequence(): CharSequence



    /**
     * Returns the length of the document text.
     *
     * @return the length of the document text.
     * @see .getCharsSequence
     */
    @Contract(pure = true)
    fun getTextLength(): Int {
        return getImmutableCharSequence().length
    }

    /**
     * Returns the number of lines in the document.
     *
     * @return the number of lines in the document.
     */
    @Contract(pure = true)
    fun getLineCount(): Int

    /**
     * Returns the line number (0-based) corresponding to the specified offset in the document.
     *
     * @param offset the offset to get the line number for (must be in the range from 0 (inclusive)
     * to [.getTextLength] (inclusive)).
     * @return the line number corresponding to the offset.
     */
    @Contract(pure = true)
    fun getLineNumber(offset: Int): Int

    /**
     * Returns the start offset for the line with the specified number.
     *
     * @param line the line number (from 0 to getLineCount()-1)
     * @return the start offset for the line.
     */
    @Contract(pure = true)
    fun getLineStartOffset(line: Int): Int

    /**
     * Returns the end offset for the line with the specified number.
     *
     * @param line the line number (from 0 to getLineCount()-1)
     * @return the end offset for the line.
     */
    @Contract(pure = true)
    fun getLineEndOffset(line: Int): Int

    /**
     * @return whether the line with the given index has been modified since the document has been saved
     */
    fun isLineModified(line: Int): Boolean {
        return false
    }

    /**
     * Inserts the specified text at the specified offset in the document. Line breaks in
     * the inserted text must be normalized as \n.
     *
     * @param offset the offset to insert the text at.
     * @param s      the text to insert.
     * @throws ReadOnlyModificationException         if the document is read-only.
     * @throws ReadOnlyFragmentModificationException if the fragment to be modified is covered by a guarded block.
     */
    fun insertString(offset: Int, @NotNull s: CharSequence)

    /**
     * Deletes the specified range of text from the document.
     *
     * @param startOffset the start offset of the range to delete.
     * @param endOffset   the end offset of the range to delete.
     * @throws ReadOnlyModificationException         if the document is read-only.
     * @throws ReadOnlyFragmentModificationException if the fragment to be modified is covered by a guarded block.
     */
    fun deleteString(startOffset: Int, endOffset: Int)

    /**
     * Replaces the specified range of text in the document with the specified string.
     * Line breaks in the text to replace with must be normalized as \n.
     *
     * @param startOffset the start offset of the range to replace.
     * @param endOffset   the end offset of the range to replace.
     * @param s           the text to replace with.
     * @throws ReadOnlyModificationException         if the document is read-only.
     * @throws ReadOnlyFragmentModificationException if the fragment to be modified is covered by a guarded block.
     */
    fun replaceString(startOffset: Int, endOffset: Int, s: CharSequence)

    /**
     * Checks if the document text is read-only.
     *
     * @return `true` if the document text is writable, `false` if it is read-only.
     * @see .fireReadOnlyModificationAttempt
     */
    @Contract(pure = true)
    fun isWritable(): Boolean

    /**
     * Gets the modification stamp value. Modification stamp is a value changed by any modification
     * of the content of the file. Note that it is not related to the file modification time.
     *
     * @return the modification stamp value.
     * @see com.intellij.psi.PsiFile.getModificationStamp
     * @see com.intellij.openapi.vfs.VirtualFile.getModificationStamp
     */
    @Contract(pure = true)
    fun getModificationStamp(): Long

    /**
     * Fires a notification that the user would like to remove the read-only state
     * from the document (the read-only state can be removed by checking the file out
     * from the version control system, or by clearing the read-only attribute on the file).
     */
    fun fireReadOnlyModificationAttempt() {}

    /**
     * Adds a listener for receiving notifications about changes in the document content.
     *
     * @param listener the listener instance.
     */
    fun addDocumentListener(listener: DocumentListener) {}

    fun addDocumentListener(
        listener: DocumentListener,
        parentDisposable: Disposable
    ) {
    }

    /**
     * Marks the document as read-only or read/write. This method only modifies the flag stored
     * in the document instance - no checkouts or file changes are performed.
     *
     * @param isReadOnly the new value of the read-only flag.
     * @see .isWritable
     * @see .fireReadOnlyModificationAttempt
     */
    fun setReadOnly(isReadOnly: Boolean) {}



    fun setText(text: CharSequence)

    @Contract(pure = true)
    fun getLineSeparatorLength(line: Int): Int {
        return 0
    }



}
