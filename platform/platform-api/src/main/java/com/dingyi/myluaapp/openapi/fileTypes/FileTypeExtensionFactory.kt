package com.dingyi.myluaapp.openapi.fileTypes

import com.dingyi.myluaapp.openapi.application.ApplicationManager
import com.dingyi.myluaapp.openapi.extensions.ExtensionPointName
import com.dingyi.myluaapp.openapi.util.KeyedExtensionFactory
import com.dingyi.myluaapp.openapi.util.KeyedFactoryEPBean


class FileTypeExtensionFactory<T : Any>(
    interfaceClass: Class<T>, epName: ExtensionPointName<KeyedFactoryEPBean>
) : KeyedExtensionFactory<T, FileType>(
    interfaceClass, epName, ApplicationManager.getApplication()
) {

    override fun getKey(key: FileType): String {
        return key.getName()
    }
}