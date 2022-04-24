package com.dingyi.myluaapp.build.api.plugins

/**
 * Additional, ad-hoc, properties for Gradle domain objects.
 *
 *
 * Extra properties extensions allow new properties to be added to existing domain objects. They act like maps,
 * allowing the storage of arbitrary key/value pairs. All [ExtensionAware] Gradle domain objects intrinsically have an extension
 * named “{@value #EXTENSION_NAME}” of this type.
 *
 *
 * An important feature of extra properties extensions is that all of its properties are exposed for reading and writing via the [ExtensionAware]
 * object that owns the extension.
 *
 * <pre class='autoTested'>
 * project.ext.set("myProp", "myValue")
 * assert project.myProp == "myValue"
 *
 * project.myProp = "anotherValue"
 * assert project.myProp == "anotherValue"
 * assert project.ext.get("myProp") == "anotherValue"
</pre> *
 *
 * Extra properties extension objects support Groovy property syntax. That is, a property can be read via `extension.«name»` and set via
 * `extension.«name» = "value"`. **Wherever possible, the Groovy property syntax should be preferred over the
 * [.get] and [.set] methods.**
 *
 * <pre class='autoTested'>
 * project.ext {
 * myprop = "a"
 * }
 * assert project.myprop == "a"
 * assert project.ext.myprop == "a"
 *
 * project.myprop = "b"
 * assert project.myprop == "b"
 * assert project.ext.myprop == "b"
</pre> *
 *
 * You can also use the Groovy accessor syntax to get and set properties on an extra properties extension.
 *
 * <pre class='autoTested'>
 * project.ext["otherProp"] = "a"
 * assert project.otherProp == "a"
 * assert project.ext["otherProp"] == "a"
</pre> *
 *
 * The exception that is thrown when an attempt is made to get the value of a property that does not exist is different depending on whether the
 * Groovy syntax is used or not. If Groovy property syntax is used, the Groovy [groovy.lang.MissingPropertyException] will be thrown.
 * When the [.get] method is used, an [UnknownPropertyException] will be thrown.
 *
 */
interface ExtraPropertiesExtension {
    /**
     * Returns whether or not the extension has a property registered via the given name.
     *
     * <pre class='autoTested'>
     * assert project.ext.has("foo") == false
     * assert project.hasProperty("foo") == false
     *
     * project.ext.foo = "bar"
     *
     * assert project.ext.has("foo")
     * assert project.hasProperty("foo")
    </pre> *
     *
     * @param name The name of the property to check for
     * @return `true` if a property has been registered with this name, otherwise `false`.
     */
    fun has(name: String): Boolean

    /**
     * Returns the value for the registered property with the given name.
     *
     * When using an extra properties extension from Groovy, you can also get properties via Groovy's property syntax.
     * All of the following lines of code are equivalent.
     *
     * <pre class='autoTested'>
     * project.ext { foo = "bar" }
     *
     * assert project.ext.get("foo") == "bar"
     * assert project.ext.foo == "bar"
     * assert project.ext["foo"] == "bar"
     *
     * assert project.foo == "bar"
     * assert project["foo"] == "bar"
    </pre> *
     *
     * When using the first form, an [UnknownPropertyException] exception will be thrown if the
     * extension does not have a property called “`foo`”. When using the second forms (i.e. Groovy notation),
     * Groovy's [groovy.lang.MissingPropertyException] will be thrown instead.
     *
     * @param name The name of the property to get the value of
     * @return The value for the property with the given name.
     * @throws UnknownPropertyException if there is no property registered with the given name
     */
    @Throws(UnknownPropertyException::class)
    operator fun get(name: String): Any

    /**
     * Updates the value for, or creates, the registered property with the given name to the given value.
     *
     * When using an extra properties extension from Groovy, you can also set properties via Groovy's property syntax.
     * All of the following lines of code are equivalent.
     *
     * <pre class='autoTested'>
     * project.ext.set("foo", "bar")
     * project.ext.foo = "bar"
     * project.ext["foo"] = "bar"
     *
     * // Once the property has been created via the extension, it can be changed by the owner.
     * project.foo = "bar"
     * project["foo"] = "bar"
    </pre> *
     *
     * @param name The name of the property to update the value of or create
     * @param value The value to set for the property
     */
    operator fun set(name: String, value: Any?)

    /**
     * Returns all of the registered properties and their current values as a map.
     *
     * The returned map is detached from the extension. That is, any changes made to the map do not
     * change the extension from which it originated.
     *
     * <pre class='autoTested'>
     * project.version = "1.0"
     *
     * assert project.hasProperty("version")
     * assert project.ext.properties.containsKey("version") == false
     *
     * project.ext.foo = "bar"
     *
     * assert project.ext.properties.containsKey("foo")
     * assert project.ext.properties.foo == project.ext.foo
     *
     * assert project.ext.properties.every { key, value -&gt; project.properties[key] == value }
    </pre> *
     *
     * @return All of the registered properties and their current values as a map.
     */
    val properties: Map<String, Any?>?

    /**
     * The exception that will be thrown when an attempt is made to read a property that is not set.
     */
    class UnknownPropertyException(extension: ExtraPropertiesExtension, propertyName: String) :
        RuntimeException(createMessage(propertyName)) {
        companion object {
            fun createMessage(propertyName: String?): String {
                return String.format(
                    "Cannot get property '%s' on extra properties extension as it does not exist",
                    propertyName
                )
            }
        }
    }

    companion object {
        /**
         * The name of this extension in all [ExtensionContainers][ExtensionContainer], {@value}.
         */
        const val EXTENSION_NAME = "ext"
    }
}
