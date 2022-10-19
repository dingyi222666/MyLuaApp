package com.dingyi.myluaapp.openapi.util

import java.util.concurrent.atomic.AtomicLongFieldUpdater


open class SimpleModificationTracker : ModificationTracker {

    @Volatile
    private var myCounter: Long = 0

    override fun getModificationCount(): Long {
        return myCounter
    }

    fun incModificationCount() {
        UPDATER.incrementAndGet(this)
    }

    companion object {
        private val UPDATER: AtomicLongFieldUpdater<SimpleModificationTracker> =
            AtomicLongFieldUpdater.newUpdater(
                SimpleModificationTracker::class.java, "myCounter"
            )
    }
}

