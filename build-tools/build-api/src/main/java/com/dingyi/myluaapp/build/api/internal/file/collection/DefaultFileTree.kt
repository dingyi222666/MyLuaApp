package com.dingyi.myluaapp.build.api.internal.file.collection

import com.dingyi.myluaapp.build.api.file.collection.FileCollection
import com.dingyi.myluaapp.build.api.file.collection.FileTree
import java.io.File

class DefaultFileTree : FileTree {

    private var walker: Sequence<File>? = null

    override fun include(pattern: String): FileTree {
        val regex = kotlin.runCatching { pattern.toRegex() }.getOrNull()
        walker = walker?.filter {
            val name = it.name
            name.endsWith(pattern) or name.startsWith(pattern) or (regex?.matches(name) ?: false)
        }
        return this
    }

    override fun exclude(pattern: String): FileTree {
        val regex = kotlin.runCatching { pattern.toRegex() }.getOrNull()
        walker = walker?.filterNot {
            val name = it.name
            name.endsWith(pattern) or name.startsWith(pattern) or (regex?.matches(name) ?: false)
        }
        return this
    }

    override fun dir(name: String): FileTree {
        val file = File(name)

        walker = file.walk(FileWalkDirection.TOP_DOWN)

        return this
    }

    override fun toCollection(): FileCollection {
        return this
    }

    override fun visit(block: (File) -> Unit) {
        walker?.forEach(block)
    }

    override val size: Int
        get() = walker?.count() ?: 0

    override fun contains(element: File): Boolean {
        return walker?.contains(element) ?: false
    }

    override fun containsAll(elements: Collection<File>): Boolean {
        return walker?.toList()?.containsAll(elements) ?: false
    }

    override fun isEmpty(): Boolean {
        return walker?.count() ?: 0 == 0
    }

    override fun iterator(): Iterator<File> {
        return walker?.iterator() ?: emptyList<File>().iterator()
    }

    override fun toString(): String {
        return walker?.toList()?.toString() ?: ""
    }


}