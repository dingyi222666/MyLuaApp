package com.dingyi.myluaapp.ide.completion

import com.dingyi.myluaapp.openapi.editor.Caret
import com.dingyi.myluaapp.openapi.editor.Editor
import com.dingyi.myluaapp.openapi.project.Project
import com.dingyi.myluaapp.openapi.vfs.VirtualFile


/**
 * Contains useful information about the current completion request
 */
class CompletionParameters private constructor(
    val project: Project,
    val editor: Editor,
    val file: VirtualFile,
    val prefix: String?,
    val caret: Caret
) {

}