package com.dingyi.myluaapp.build.api.internal.plugins

import com.dingyi.myluaapp.build.api.Action

class DefaultExtensionContainer {

    private val extensions: MutableMap<String,ExtensionHolder<*>> =
        LinkedHashMap()


    fun <T> add(publicType: Class<T>, name: String, extension: T) {
        require(!hasExtension(name)) {
            String.format(
                "Cannot add extension with name '%s', as there is an extension already registered with that name.",
                name
            )
        }
        extensions[name] = ExtensionHolder(
            name,
            publicType,
            extension
        )
    }

    fun hasExtension(name: String?): Boolean {
        return extensions.containsKey(name)
    }


    private class ExtensionHolder<T>(
        val name: String,
        publicType: Class<T>,
        extension: T
    ) {
        private val publicType: Class<T>
        protected val extension: T
         fun getPublicType(): Class<T> {
            return publicType
        }

        fun get(): T {
            return extension
        }

        fun configure(action: Action<in T>): T {
            action.execute(extension)
            return extension
        }

        init {
            this.publicType = publicType
            this.extension = extension
        }
    }
}