package com.dingyi.myluaapp.openapi.fileEditor

import android.view.View
import com.dingyi.myluaapp.openapi.actions.DataKey
import com.dingyi.myluaapp.openapi.editor.CaretModel
import com.dingyi.myluaapp.openapi.editor.Editor
import com.dingyi.myluaapp.openapi.editor.LogicalPosition
import com.dingyi.myluaapp.openapi.project.Project
import com.dingyi.myluaapp.openapi.vfs.VirtualFile


/**
 * Allows opening file in editor, optionally at specific line/column position.
 */
class OpenFileDescriptor private constructor(
    project: Project,
    file: VirtualFile,
    logicalLine: Int,
    logicalColumn: Int,
) : FileEditorNavigatable, Comparable<OpenFileDescriptor> {
    private val myProject: Project
    private val myFile: VirtualFile
    val line: Int
    val column: Int


    constructor(project: Project, file: VirtualFile) : this(
        project,
        file,
        -1,
        -1,
    )

    init {
        myProject = project
        myFile = file
        line = logicalLine
        column = logicalColumn
    }


    override fun getFile(): VirtualFile {
        return myFile
    }


    override fun navigate(requestFocus: Boolean) {
        FileNavigator.instance.navigate(this, requestFocus)
    }

    fun navigateInEditor(project: Project, requestFocus: Boolean): Boolean {
        return FileNavigator.instance.navigateInEditor(this, requestFocus)
    }

    fun navigateIn(e: Editor) {
        Companion.navigateInEditor(this, e)
    }

    private fun scrollToCaret(e: Editor) {
        e.getScrollingModel().scrollToCaret()
    }

    override fun canNavigate(): Boolean {
        return FileNavigator.instance.canNavigate(this)
    }

    override fun canNavigateToSource(): Boolean {
        return canNavigate()
    }


    val project: Project
        get() = myProject


    fun dispose() {

    }

    override operator fun compareTo(o: OpenFileDescriptor): Int {
        var i: Int = myProject.getName().compareTo(o.myProject.getName())
        if (i != 0) return i
        i = myFile.name.compareTo(o.myFile.getName())
        if (i != 0) return i

        return 0
    }

    companion object {
        /**
         * Tells descriptor to navigate in specific editor rather than file editor in main IDE window.
         * For example if you want to navigate in editor embedded into modal dialog, you should provide this data.
         */
        val NAVIGATE_IN_EDITOR: DataKey<Editor> = DataKey.create("NAVIGATE_IN_EDITOR")
        protected fun navigateInEditor(
            descriptor: OpenFileDescriptor,
            e: Editor
        ) {
            val caretModel: CaretModel = e.getCaretModel()
            var caretMoved = false
            if (descriptor.line >= 0) {
                val pos = LogicalPosition(
                    descriptor.line, Math.max(
                        descriptor.column, 0
                    )
                )

                caretModel.moveToLogicalPosition(pos)
                caretMoved = true

            }
            if (caretMoved) {
                e.getSelectionModel().removeSelection()
                FileEditorManager.getInstance(descriptor.project).runWhenLoaded(e) {
                    descriptor.scrollToCaret(e)
                }
            }
        }


    }
}
