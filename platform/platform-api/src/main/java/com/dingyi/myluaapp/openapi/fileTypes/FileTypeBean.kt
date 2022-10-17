package com.dingyi.myluaapp.openapi.fileTypes

import com.dingyi.myluaapp.openapi.extensions.PluginAware
import com.dingyi.myluaapp.openapi.extensions.PluginDescriptor
import com.dingyi.myluaapp.openapi.extensions.PluginId


class FileTypeBean : PluginAware {
    private lateinit var myPluginDescriptor: PluginDescriptor

    /**
     * Name of the class implementing the file type (must be a subclass of [FileType]). This can be omitted
     * if the fileType declaration is used to add extensions to an existing file type (in this case, only 'name'
     * and 'extensions' attributes must be specified).
     */

    lateinit var implementationClass: String


    /**
     * Name of the file type. Needs to match the return value of [FileType.getName].
     */

    lateinit var name: String

    /**
     * Semicolon-separated list of extensions to be associated with the file type. Extensions
     * must not be prefixed with a `.`.
     */

    lateinit var extensions: String


    /**
     * Semicolon-separated list of patterns (strings containing ? and * characters) to be associated with the file type.
     */
     lateinit var patterns: String



    /**
     * For file types that extend [LanguageFileType] and are the primary file type for the corresponding language, this must be set
     * to the ID of the language returned by [LanguageFileType.getLanguage].
     */

    lateinit var language: String


    override fun setPluginDescriptor(pluginDescriptor: PluginDescriptor) {
        myPluginDescriptor = pluginDescriptor
    }

    fun getPluginDescriptor():PluginDescriptor = myPluginDescriptor



    val pluginId: PluginId
        get() = myPluginDescriptor.getPluginId()
}