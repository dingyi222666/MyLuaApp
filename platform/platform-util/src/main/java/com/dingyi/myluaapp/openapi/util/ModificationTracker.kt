package com.dingyi.myluaapp.openapi.util


fun interface ModificationTracker {
    fun getModificationCount(): Long

    companion object {
        val EVER_CHANGED: ModificationTracker = object : ModificationTracker {
            private var myCounter: Long = 0
            override fun getModificationCount(): Long {
                return myCounter++
            }
        }
        val NEVER_CHANGED = ModificationTracker { 0 }
    }
}
