package com.dingyi.myluaapp.build.api.tests.file.collection

import com.dingyi.myluaapp.build.api.internal.file.collection.DefaultFileTree
import com.dingyi.myluaapp.build.api.internal.objects.DefaultDomainObjectCollection
import com.dingyi.myluaapp.build.api.internal.provider.DefaultProvider
import com.dingyi.myluaapp.build.api.internal.provider.ValueProvider
import org.junit.Test
import org.junit.Assert.*

class FileTreeTest {

    @Test
    fun testForeach() {
        val fileTree = DefaultFileTree()
        fileTree.dir("G:\\dingyi\\Pictures")
        assertEquals(
            fileTree.toList().toString(),
            "[G:\\dingyi\\Pictures, G:\\dingyi\\Pictures\\1.jpg, G:\\dingyi\\Pictures\\39.png, G:\\dingyi\\Pictures\\Camera Roll, G:\\dingyi\\Pictures\\desktop.ini, G:\\dingyi\\Pictures\\Saved Pictures, G:\\dingyi\\Pictures\\橘1_1_Moment.jpg]"
        )
        fileTree.include(".jpg")
        assertEquals(
            fileTree.toList().toString(),
            "[G:\\dingyi\\Pictures\\1.jpg, G:\\dingyi\\Pictures\\橘1_1_Moment.jpg]"
        )
    }

    @Test
    fun test1() {
        val provider1 = ValueProvider.of("G:\\dingyi\\Pictures")
        val provider2 = DefaultProvider {
            listOf("G:\\dingyi\\Pictures\\1.jpg", "G:\\dingyi\\Pictures\\39.png", "G:\\dingyi\\Pictures\\Camera Roll", "G:\\dingyi\\Pictures\\desktop.ini", "G:\\dingyi\\Pictures\\Saved Pictures", "G:\\dingyi\\Pictures\\橘1_1_Moment.jpg")
        }
        val collection = DefaultDomainObjectCollection<String>(
        )

        collection.add("abc")
        println(collection.size)
        collection.addLater(provider1)
        println(collection.size)
        collection.addAllLater(provider2)
        println(collection.size)
        collection.configureEach {
            println(it)
        }
    }

}