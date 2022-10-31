package com.dingyi.myluaapp.openapi.fileEditor

import com.dingyi.myluaapp.openapi.application.ApplicationManager

interface FileNavigator {
    fun canNavigate(descriptor: OpenFileDescriptor): Boolean {
        return descriptor.file.isValid()
    }

    fun canNavigateToSource(descriptor: OpenFileDescriptor): Boolean {
        return descriptor.file.isValid()
    }

    fun navigate(descriptor: OpenFileDescriptor, requestFocus: Boolean)
    fun navigateInEditor(descriptor: OpenFileDescriptor?, requestFocus: Boolean): Boolean

    companion object {
        val instance: FileNavigator
            get() = ApplicationManager.getApplication().get(FileNavigator::class.java)
    }
}