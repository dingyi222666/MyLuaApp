package com.dingyi.myluaapp.build.api.objects

import com.dingyi.myluaapp.build.api.Action
import com.dingyi.myluaapp.build.api.provider.Provider
import com.dingyi.myluaapp.build.api.sepcs.Spec


/**
 *
 * A `DomainObjectCollection` is a specialised [Collection] that adds the ability to receive modification notifications and use live filtered sub collections.
 *
 *
 * The filtered collections returned by the filtering methods, such as [.matching], return collections that are *live*. That is, they reflect
 * changes made to the source collection that they were created from. This is true for filtered collections made from filtered collections etc.
 *
 *
 * You can also add actions that are executed as elements are added to the collection. Actions added to filtered collections will be fired if an addition/removal
 * occurs for the source collection that matches the filter.
 *
 *
 * `DomainObjectCollection` instances are not *thread-safe* and undefined behavior may result from the invocation of any method on a collection that is being mutated by another
 * thread; this includes direct invocations, passing the collection to a method that might perform invocations, and using an existing iterator to examine the collection.
 *
 *
 * @param <T> The type of objects in this collection.
</T> */
interface DomainObjectCollection<T> : MutableCollection<T> {


    /**
     * Adds an element to this collection, given a [Provider] that will provide the element when required.
     *
     * @param provider A [Provider] that can provide the element when required.
     * @since 4.8
     */
    fun addLater(provider: Provider<out T>)

    /**
     * Adds elements to this collection, given a [Provider] of [Iterable] that will provide the elements when required.
     *
     * @param provider A [Provider] of [Iterable] that can provide the elements when required.
     * @since 5.0
     */
    fun addAllLater(provider: Provider<out Iterable<T>>)

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
     * Returns a collection containing the objects in this collection of the given type.  The returned collection is
     * live, so that when matching objects are later added to this collection, they are also visible in the filtered
     * collection.
     *
     * @param type The type of objects to find.
     * @return The matching objects. Returns an empty collection if there are no such objects in this collection.
     */
    fun <S : T> withType(type: Class<S>): DomainObjectCollection<S>

    /**
     * Returns a collection containing the objects in this collection of the given type. Equivalent to calling
     * `withType(type).all(configureAction)`
     *
     * @param type The type of objects to find.
     * @param configureAction The action to execute for each object in the resulting collection.
     * @return The matching objects. Returns an empty collection if there are no such objects in this collection.
     */
    fun <S : T> withType(
        type: Class<S>,
        configureAction: Action<in S>
    ): DomainObjectCollection<S>


    /**
     * Returns a collection which contains the objects in this collection which meet the given specification. The
     * returned collection is live, so that when matching objects are added to this collection, they are also visible in
     * the filtered collection.
     *
     * @param spec The specification to use.
     * @return The collection of matching objects. Returns an empty collection if there are no such objects in this
     * collection.
     */
    fun matching(spec: Spec<in T>): DomainObjectCollection<T>

    /**
     * Adds an `Action` to be executed when an object is added to this collection.
     *
     *
     * Like [.all], this method will cause all objects in this container to be realized.
     *
     * @param action The action to be executed
     * @return the supplied action
     */
    fun whenObjectAdded(action: Action<in T>): Action<in T>


    /**
     * Adds an `Action` to be executed when an object is removed from this collection.
     *
     * @param action The action to be executed
     * @return the supplied action
     */
    fun whenObjectRemoved(action: Action<in T>): Action<in T>


    /**
     * Executes the given action against all objects in this collection, and any objects subsequently added to this
     * collection.
     *
     * @param action The action to be executed
     */
    fun all(action: Action<in T>)



    /**
     * Configures each element in this collection using the given action, as each element is required. Actions are run in the order added.
     *
     * @param action A [Action] that can configure the element when required.
     * @since 4.9
     */
    fun configureEach(action: Action<in T>)

    // note: this is here to override the default Groovy Collection.findAll { } method.
    /**
     * Returns a collection which contains the objects in this collection which meet the given closure specification.
     *
     * @param spec The specification to use. The closure gets a collection element as an argument.
     * @return The collection of matching objects. Returns an empty collection if there are no such objects in this
     * collection.
     */
    fun findAll(spec: Spec<in T>): Collection<T>


}
