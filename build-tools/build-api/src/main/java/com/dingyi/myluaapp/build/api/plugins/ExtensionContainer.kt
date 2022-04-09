package com.dingyi.myluaapp.build.api.plugins

import com.dingyi.myluaapp.build.api.Action
/**
 * Allows adding 'namespaced' DSL extensions to a target object.
 */

interface ExtensionContainer {



        /**
         * Adds a new extension to this container.
         *
         * Adding an extension of name 'foo' will:
         *
         *  *  add 'foo' dynamic property
         *  *  add 'foo' dynamic method that accepts a closure that is a configuration script block
         *
         *
         * The extension will be exposed as `publicType`.
         *
         * @param publicType The extension public type
         * @param name The name for the extension
         * @param extension Any object implementing `publicType`
         * @throws IllegalArgumentException When an extension with the given name already exists.
         * @since 3.5
         */
        fun <T> add(publicType: Class<T>, name: String, extension: T)


        /**
         * Adds a new extension to this container.
         *
         * Adding an extension of name 'foo' will:
         *
         *  *  add 'foo' dynamic property
         *  *  add 'foo' dynamic method that accepts a closure that is a configuration script block
         *
         *
         * The extension will be exposed as `extension.getClass()` unless the extension itself declares a preferred public type via the [org.gradle.api.reflect.HasPublicType] protocol.
         *
         * @param name The name for the extension
         * @param extension Any object
         * @throws IllegalArgumentException When an extension with the given name already exists
         */
        fun add(name: String, extension: Any)

        /**
         * Creates and adds a new extension to this container.
         *
         * A new instance of the given `instanceType` will be created using the given `constructionArguments`.
         * The extension will be exposed as `publicType`.
         * The new instance will have been dynamically made [ExtensionAware], which means that you can cast it to [ExtensionAware].
         *
         * @param <T> the extension public type
         * @param publicType The extension public type
         * @param name The name for the extension
         * @param instanceType The extension instance type
         * @param constructionArguments The arguments to be used to construct the extension instance
         * @return The created instance
         * @throws IllegalArgumentException When an extension with the given name already exists.
         * @see .add
         * @since 3.5
        </T> */
        fun <T> create(
            publicType: Class<T>,
            name: String,
            instanceType: Class<out T>,
            vararg constructionArguments: Any?
        ): T


        /**
         * Creates and adds a new extension to this container.
         *
         * A new instance of the given `type` will be created using the given `constructionArguments`.
         * The extension will be exposed as `type` unless the extension itself declares a preferred public type via the [org.gradle.api.reflect.HasPublicType] protocol.
         * The new instance will have been dynamically made [ExtensionAware], which means that you can cast it to [ExtensionAware].
         *
         * @param name The name for the extension
         * @param type The type of the extension
         * @param constructionArguments The arguments to be used to construct the extension instance
         * @return The created instance
         * @throws IllegalArgumentException When an extension with the given name already exists.
         * @see .add
         */
        fun <T> create(name: String?, type: Class<T>?, vararg constructionArguments: Any?): T


        /**
         * Looks for the extension of a given type (useful to avoid casting). If none found it will throw an exception.
         *
         * @param type extension type
         * @return extension, never null
         * @throws UnknownDomainObjectException When the given extension is not found.
         */

        fun <T> getByType(type: Class<T>): T


        /**
         * Looks for the extension of a given type (useful to avoid casting). If none found null is returned.
         *
         * @param type extension type
         * @return extension or null
         */
        fun <T> findByType(type: Class<T>): T


        /**
         * Looks for the extension of a given name. If none found it will throw an exception.
         *
         * @param name extension name
         * @return extension, never null
         * @throws UnknownDomainObjectException When the given extension is not found.
         */

        fun getByName(name: String): Any

        /**
         * Looks for the extension of a given name. If none found null is returned.
         *
         * @param name extension name
         * @return extension or null
         */
        fun findByName(name: String?): Any?

        /**
         * Looks for the extension of the specified type and configures it with the supplied action.
         *
         * @param type extension type
         * @param action the configure action
         * @throws UnknownDomainObjectException if no extension is found.
         */
        fun <T> configure(type: Class<T>, action: Action<in T>)



        /**
         * Looks for the extension with the specified name and configures it with the supplied action.
         *
         * @param name extension name
         * @param action the configure action
         * @throws UnknownDomainObjectException if no extension is found.
         * @since 4.0
         */
        fun <T> configure(name: String, action: Action<in T>)

        /**
         * The extra properties extension in this extension container.
         *
         * This extension is always present in the container, with the name “ext”.
         *
         * @return The extra properties extension in this extension container.
         */
        fun getExtraProperties(): ExtraPropertiesExtension
}