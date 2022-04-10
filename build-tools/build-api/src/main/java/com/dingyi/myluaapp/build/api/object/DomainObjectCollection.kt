package com.dingyi.myluaapp.build.api.`object`

import com.dingyi.myluaapp.build.api.Action


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
     * Returns a collection containing the objects in this collection of the given type.  The returned collection is
     * live, so that when matching objects are later added to this collection, they are also visible in the filtered
     * collection.
     *
     * @param type The type of objects to find.
     * @return The matching objects. Returns an empty collection if there are no such objects in this collection.
     */
    fun <S : T?> withType(type: Class<S>): DomainObjectCollection<S>

    /**
     * Returns a collection containing the objects in this collection of the given type. Equivalent to calling
     * `withType(type).all(configureAction)`
     *
     * @param type The type of objects to find.
     * @param configureAction The action to execute for each object in the resulting collection.
     * @return The matching objects. Returns an empty collection if there are no such objects in this collection.
     */
    fun <S : T?> withType(
        type: Class<S>,
        configureAction: Action<in S>
    ): DomainObjectCollection<S>



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


}
