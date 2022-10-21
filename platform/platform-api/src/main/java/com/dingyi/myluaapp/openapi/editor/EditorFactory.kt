package com.dingyi.myluaapp.openapi.editor

import com.dingyi.myluaapp.openapi.application.ApplicationManager
import com.dingyi.myluaapp.openapi.editor.event.EditorFactoryListener
import com.dingyi.myluaapp.openapi.fileTypes.FileType
import com.dingyi.myluaapp.openapi.project.Project
import com.dingyi.myluaapp.openapi.vfs.VirtualFile
import com.intellij.openapi.Disposable
import java.util.stream.Stream
import kotlin.streams.toList


/**
 * Provides services for creating document and editor instances.
 *
 * Creating and releasing of editors must be done from EDT.
 */
abstract class EditorFactory {
    /**
     * Creates a document from the specified text specified as a character sequence.
     */

    abstract fun createDocument(text: CharSequence): Document

    /**
     * Creates a document from the specified text specified as an array of characters.
     */

    abstract fun createDocument(text: CharArray): Document

    /**
     * Creates an editor for the specified document. Must be invoked in EDT.
     *
     *
     * The created editor must be disposed after use by calling [.releaseEditor].
     *
     */
    abstract fun createEditor(document: Document): Editor

    /**
     * Creates a read-only editor for the specified document. Must be invoked in EDT.
     *
     *
     * The created editor must be disposed after use by calling [.releaseEditor].
     *
     */
    abstract fun createViewer(document: Document): Editor

    /**
     * Creates an editor for the specified document associated with the specified project. Must be invoked in EDT.
     *
     *
     * The created editor must be disposed after use by calling [.releaseEditor].
     *
     * @see Editor.getProject
     */
    abstract fun createEditor(document: Document, project: Project?): Editor


    /**
     * Creates an editor for the specified document associated with the specified project. Must be invoked in EDT.
     *
     *
     * The created editor must be disposed after use by calling [.releaseEditor].
     *
     *
     * @param document the document to create the editor for.
     * @param project  the project for which highlighter should be created
     * @param fileType the file type according to which the editor contents is highlighted.
     * @param isViewer true if read-only editor should be created
     * @see Editor.getProject
     */
    abstract fun createEditor(
        document: Document,
        project: Project?,
        fileType: FileType,
        isViewer: Boolean
    ): Editor?

    /**
     * Creates an editor for the specified document associated with the specified project. Must be invoked in EDT.
     *
     *
     * The created editor must be disposed after use by calling [.releaseEditor].
     *
     * @param document the document to create the editor for.
     * @param project  the project for which highlighter should be created
     * @param file     the file according to which the editor contents is highlighted.
     * @param isViewer true if read-only editor should be created
     * @return the editor instance.
     * @see Editor.getProject
     */
    abstract fun createEditor(
        document: Document,
        project: Project?,
        file: VirtualFile,
        isViewer: Boolean
    ): Editor?


    /**
     * Creates a read-only editor for the specified document associated with the specified project. Must be invoked in EDT.
     *
     *
     * The created editor must be disposed after use by calling [.releaseEditor]
     *
     */
    abstract fun createViewer(document: Document, project: Project?): Editor?


    /**
     * Disposes the specified editor instance. Must be invoked in EDT.
     */
    abstract fun releaseEditor(editor: Editor)

    /**
     * Returns the stream of editors for the specified document associated with the specified project.
     *
     * @param document the document for which editors are requested.
     * @param project  the project with which editors should be associated, or null if any editors
     * for this document should be returned.
     */

    abstract fun editors(
        document: Document,
        project: Project?
    ): Stream<Editor>

    /**
     * If а collaborative development session is off,
     * then returns the result of [EditorFactory.editors].
     *
     *
     * If а collaborative development session is on,
     * then returns such editors from [EditorFactory.editors],
     * which belong only to the guest or host, who's action/activity is currently processing.
     *
     */

    fun editorsForCurrentClient(
        document: Document,
        project: Project?
    ): Stream<Editor> {
        return editors(document, project)
    }

    /**
     * Returns the stream of all editors for the specified document.
     */

    fun editors(document: Document): Stream<Editor> {
        return editors(document, null)
    }

    /**
     * Consider using [.editors].
     */
    fun getEditors(
        document: Document,
        project: Project?
    ): Array<Editor> {
        return editors(document, project)//.toList()
            .toArray { arrayOfNulls<Editor>(it) }

    }

    /**
     * Consider using [.editors].
     */
    fun getEditors(document: Document): Array<Editor> {
        return getEditors(document, null)
    }

    /**
     * Returns the list of all currently open editors.
     */
    abstract val allEditors: Array<Editor>


    /**
     * Registers a listener for receiving notifications when editor instances are created and released
     * and removes the listener when the `parentDisposable` gets disposed.
     */
    abstract fun addEditorFactoryListener(
        listener: EditorFactoryListener,
        parentDisposable: Disposable
    )


    /**
     * Reloads the editor settings and refreshes all currently open editors.
     */
    abstract fun refreshAllEditors()

    companion object {
        /**
         * Returns the editor factory instance.
         *
         * @return the editor factory instance.
         */
        val instance: EditorFactory
            get() = ApplicationManager.getApplication()[EditorFactory::class.java]
    }
}
