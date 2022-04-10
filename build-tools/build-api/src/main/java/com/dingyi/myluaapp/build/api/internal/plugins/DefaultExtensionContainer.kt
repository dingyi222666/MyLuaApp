package com.dingyi.myluaapp.build.api.internal.plugins

import com.dingyi.myluaapp.build.api.Action
import com.dingyi.myluaapp.build.api.plugins.ExtensionContainer
import com.dingyi.myluaapp.build.api.plugins.ExtraPropertiesExtension

class DefaultExtensionContainer : ExtensionContainer {

    private val extensions: MutableMap<String, ExtensionHolder<*>> =
        LinkedHashMap()


    override fun <T> add(publicType: Class<T>, name: String, extension: T) {
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




    override fun add(name: String, extension: Any) {
        extensions[name] = ExtensionHolder(
            name,
            extension.javaClass,
            extension
        )
    }

    override fun <T> create(
        publicType: Class<T>,
        name: String,
        instanceType: Class<out T>,
        vararg constructionArguments: Any?
    ): T {
        val extension = createInstance(
            instanceType,
            *constructionArguments
        ) as T

        add(publicType, name, extension)
        return extension
    }

    private fun createInstance(
        instanceType: Class<*>,
        vararg constructionArguments: Any?
    ): Any {
        val constructor = instanceType
            .getConstructor(*constructionArguments.map { it?.javaClass }.toTypedArray())
        return constructor.newInstance(*constructionArguments)
    }

    override fun <T> create(name: String, type: Class<T>, vararg constructionArguments: Any?): T {
        val extension = createInstance(
            type,
            *constructionArguments
        )

        add(name, extension)
        return extension as T
    }

    override fun <T> getByType(type: Class<T>): T {
        return getByType(type, type.name)
    }

    private fun <T> getByType(type: Class<T>, name: String): T {
        val extension = extensions[name]
            ?: throw IllegalArgumentException(
                String.format(
                    "No extension of type '%s' registered for name '%s'",
                    type.name,
                    name
                )
            )


        return if (extension.getPublicType() == type || extension.getPublicType().isAssignableFrom(type)) {
            extension.get() as T
        } else {
            throw IllegalArgumentException(
                String.format(
                    "Extension of type '%s' registered for name '%s' is not of type '%s'",
                    extension.getPublicType().name,
                    name,
                    type.name
                )
            )
        }
    }

    override fun <T> findByType(type: Class<T>): T? {
        for (extension in extensions.values) {
            if (extension.getPublicType() == type || extension.getPublicType().isAssignableFrom(type)) {
                return extension.get() as T
            }
        }
        return null
    }



    override fun getByName(name: String): Any {
        val extension = extensions[name]
            ?: throw IllegalArgumentException(
                String.format(
                    "No extension registered for name '%s'",
                    name
                )
            )

        return extension.get() as Any
    }

    override fun findByName(name: String): Any? {
        val extension = extensions[name]
            ?: return null

        return extension.get()
    }

    override fun <T> configure(type: Class<T>, action: Action<in T>) {
        findByType(type)?.let {
            action.execute(it)
        }
    }

    override fun <T> configure(name: String, action: Action<in T>) {
        findByName(name)?.let {
            action.execute(it as T)
        }
    }

    override fun getExtraProperties(): ExtraPropertiesExtension {
        TODO("Not yet implemented")
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

        init {
            this.publicType = publicType
            this.extension = extension
        }
    }
}