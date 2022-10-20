package com.dingyi.myluaapp.openapi.roots.libraries

abstract class LibraryTablePresentation {
    abstract fun getDisplayName(plural: Boolean): String
    abstract fun getDescription(): String
    abstract fun getLibraryTableEditorTitle(): String
}