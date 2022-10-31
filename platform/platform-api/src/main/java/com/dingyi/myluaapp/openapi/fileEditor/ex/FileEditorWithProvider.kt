package com.dingyi.myluaapp.openapi.fileEditor.ex

import com.dingyi.myluaapp.openapi.fileEditor.FileEditor
import com.dingyi.myluaapp.openapi.fileEditor.FileEditorProvider


/**
 * A holder for both [FileEditor] and [FileEditorProvider].
 * The package is suffixed with 'ex' for backward compatibility
 */
data class FileEditorWithProvider(
    val fileEditor: FileEditor,
    val provider: FileEditorProvider
) {


}