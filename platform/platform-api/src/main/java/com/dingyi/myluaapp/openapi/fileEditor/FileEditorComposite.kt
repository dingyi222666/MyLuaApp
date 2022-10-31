package com.dingyi.myluaapp.openapi.fileEditor

interface FileEditorComposite {
    val allEditors: List<FileEditor>
    val allProviders: List<FileEditorProvider>
    val isPreview: Boolean
}