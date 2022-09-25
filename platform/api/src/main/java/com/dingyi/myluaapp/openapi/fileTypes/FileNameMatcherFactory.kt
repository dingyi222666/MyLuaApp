package com.dingyi.myluaapp.openapi.fileTypes

import com.dingyi.myluaapp.openapi.application.ApplicationManager
import com.dingyi.myluaapp.openapi.application.IDEApplication
import com.dingyi.myluaapp.openapi.service.get

abstract class FileNameMatcherFactory {

    abstract fun createMatcher(pattern: String): FileNameMatcher

    companion object {
        val instance: FileNameMatcherFactory
            get() = ApplicationManager.getIDEApplication().get()
    }
}
