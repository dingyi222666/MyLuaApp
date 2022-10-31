package com.dingyi.myluaapp.openapi.fileEditor


import com.dingyi.myluaapp.openapi.fileEditor.ex.FileEditorWithProvider
import com.dingyi.myluaapp.openapi.vfs.VirtualFile
import java.util.EventObject


class FileEditorManagerEvent constructor(
    private val source: FileEditorManager,
    private val oldEditorWithProvider: FileEditorWithProvider?,
    private val newEditorWithProvider: FileEditorWithProvider?
) : EventObject(source) {

    val oldFile = oldEditorWithProvider?.fileEditor?.getFile()

    val oldEditor = oldEditorWithProvider?.fileEditor

    val oldProvider = oldEditorWithProvider?.provider

    val newFile =  newEditorWithProvider?.fileEditor?.getFile()

    val newEditor = newEditorWithProvider?.fileEditor;

    val newProvider = newEditorWithProvider?.provider




}