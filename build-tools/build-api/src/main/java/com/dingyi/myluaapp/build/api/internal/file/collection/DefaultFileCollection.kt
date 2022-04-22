package com.dingyi.myluaapp.build.api.internal.file.collection

import com.dingyi.myluaapp.build.api.file.collection.FileCollection
import java.io.File

class DefaultFileCollection(
    private val delegate: Collection<File>
) : FileCollection  {
    override val size: Int
        get() = delegate.size

    override fun contains(element: File): Boolean {
        return delegate.contains(element)
    }

    override fun containsAll(elements: Collection<File>): Boolean {
        return delegate.containsAll(elements)
    }

    override fun isEmpty(): Boolean {
        return delegate.isEmpty()
    }

    override fun iterator(): Iterator<File> {
        return delegate.iterator()
    }
}