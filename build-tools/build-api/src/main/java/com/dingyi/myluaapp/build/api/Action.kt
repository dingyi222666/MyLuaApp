package com.dingyi.myluaapp.build.api


/**
 * Performs some action against objects of type T.
 *
 * @param <T> The type of object which this action accepts.
</T> */
interface Action<T> {
    /**
     * Performs this action against the given object.
     *
     * @param t The object to perform the action on.
     */
    fun execute(t: T)
}
