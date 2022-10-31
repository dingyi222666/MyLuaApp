package com.dingyi.myluaapp.ide.completion

import com.dingyi.myluaapp.openapi.editor.Document
import com.dingyi.myluaapp.openapi.editor.Editor
import com.dingyi.myluaapp.openapi.project.Project
import com.dingyi.myluaapp.openapi.vfs.VirtualFile


/**
 * @author peter
 */
class InsertionContext(
    val file: VirtualFile,
    val editor: Editor
) {

    var laterRunnable: Runnable? = null

    var tailOffset: Int
        private set

    init {
        tailOffset = editor.getCaretModel().offset
    }


    val document: Document
        get() = editor.getDocument()


    val project: Project
        get() = editor.getProject()


}
