package com.dingyi.myluaapp.build.api.`object`

import com.dingyi.myluaapp.build.api.Action
import com.dingyi.myluaapp.build.api.Namer
import java.util.*


/**
 *
 * A `NamedDomainObjectCollection` represents a collection of objects that have an inherent, constant, name.
 *
 *
 * Objects to be added to a named domain object collection must implement `equals()` in such a way that no two objects
 * with different names are considered equal. That is, all equality tests **must** consider the name as an
 * equality key. Behavior is undefined if two objects with different names are considered equal by their `equals()` implementation.
 *
 *
 * All implementations **must** guarantee that all elements in the collection are uniquely named. That is,
 * an attempt to add an object with a name equal to the name of any existing object in the collection will fail.
 * Implementations may choose to simply return false from `add(T)` or to throw an exception.
 *
 *
 * Objects in the collection are accessible as read-only properties, using the name of the object
 * as the property name. For example (assuming the 'name' property provides the object name):
 *
 * <pre>
 * books.add(new Book(name: "gradle", title: null))
 * books.gradle.title = "Gradle in Action"
</pre> *
 *
 *
 * A dynamic method is added for each object which takes a configuration closure. This is equivalent to calling
 * [.getByName]. For example:
 *
 * <pre>
 * books.add(new Book(name: "gradle", title: null))
 * books.gradle {
 * title = "Gradle in Action"
 * }
</pre> *
 *
 *
 * You can also use the `[]` operator to access the objects of a collection by name. For example:
 *
 * <pre>
 * books.add(new Book(name: "gradle", title: null))
 * books['gradle'].title = "Gradle in Action"
</pre> *
 *
 *
 * [Rule] objects can be attached to the collection in order to respond to requests for objects by name
 * where no object with name exists in the collection. This mechanism can be used to create objects on demand.
 * For example:
 *
 * <pre>
 * books.addRule('create any') { books.add(new Book(name: "gradle", title: null)) }
 * books.gradle.name == "gradle"
</pre> *
 *
 * @param <T> The type of objects in this collection.
</T> */
interface NamedDomainObjectCollection<T> : DomainObjectCollection<T> {
    /**
     * Adds an object to the collection, if there is no existing object in the collection with the same name.
     *
     * @param e the item to add to the collection
     * @return `true` if the item was added, or `` false if an item with the same name already exists.
     */
    override fun add(e: T): Boolean

    /**
     * Adds any of the given objects to the collection that do not have the same name as any existing element.
     *
     * @param c the items to add to the collection
     * @return `true` if any item was added, or `` false if all items have non unique names within this collection.
     */
    override fun addAll(c: Collection<T>): Boolean

    /**
     * An object that represents the naming strategy used to name objects of this collection.
     *
     * @return Object representing the naming strategy.
     */
    fun getNamer(): Namer<T>

    /**
     *
     * Returns the objects in this collection, as a map from object name to object instance.
     *
     *
     * The map is ordered by the *natural ordering* of the object names (i.e. keys).
     *
     * @return The objects. Returns an empty map if this collection is empty.
     */

    fun getAsMap(): SortedMap<String, T>

    /**
     *
     * Returns the names of the objects in this collection as a Set of Strings.
     *
     *
     * The set of names is in *natural ordering*.
     *
     * @return The names. Returns an empty set if this collection is empty.
     */
    fun getNames(): SortedSet<String>

    /**
     * Locates an object by name, returning null if there is no such object.
     *
     * @param name The object name
     * @return The object with the given name, or null if there is no such object in this collection.
     */
    fun findByName(name: String): T?

    /**
     * Locates an object by name, failing if there is no such object.
     *
     * @param name The object name
     * @return The object with the given name. Never returns null.
     * @throws UnknownDomainObjectException when there is no such object in this collection.
     */

    fun getByName(name: String): T

    /**
     * Locates an object by name, failing if there is no such object. The given configure action is executed against
     * the object before it is returned from this method.
     *
     * @param name The object name
     * @param configureAction The action to use to configure the object.
     * @return The object with the given name, after the configure action has been applied to it. Never returns null.
     * @throws UnknownDomainObjectException when there is no such object in this collection.
     * @since 3.1
     */

    fun getByName(name: String, configureAction: Action<in T>): T

    /**
     * Locates an object by name, failing if there is no such object. This method is identical to [ ][.getByName]. You can call this method in your build script by using the groovy `[]` operator.
     *
     * @param name The object name
     * @return The object with the given name. Never returns null.
     * @throws UnknownDomainObjectException when there is no such object in this collection.
     */

    fun getAt(name: String?): T

}
