package com.dingyi.myluaapp.build.internal.resources


/**
 * Represents a lock on an abstract resource.  Implementations of this interface should see that methods fail if they are called
 * outside of a [ResourceLockCoordinationService.withStateLock] transform.
 */
interface ResourceLock {
    /**
     * Returns true if this resource is locked by any thread.
     *
     * @return true if any thread holds the lock for this resource
     */
    fun isLocked():Boolean

    /**
     * Returns true if the current thread holds a lock on this resource.  Returns false otherwise.
     *
     * @return true if the task for this operation holds the lock for this resource.
     */
    fun isLockedByCurrentThread():Boolean

    /**
     * Attempt to lock this resource.  Does not block.
     *
     * @return true if resource was locked, false otherwise.
     */
    fun tryLock(): Boolean

    /**
     * Unlock this resource if it's held by the calling thread.
     */
    fun unlock()
}
