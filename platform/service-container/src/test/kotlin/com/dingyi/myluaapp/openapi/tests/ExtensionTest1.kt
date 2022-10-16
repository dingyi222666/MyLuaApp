package com.dingyi.myluaapp.openapi.tests

import com.dingyi.myluaapp.openapi.extensions.ExtensionPoint
import com.dingyi.myluaapp.openapi.extensions.ExtensionPointName
import com.dingyi.myluaapp.openapi.service.internal.DefaultServiceRegistry
import com.intellij.openapi.Disposable
import org.junit.Test

class ExtensionTest1 {


    private val myExtensionName = ExtensionPointName<MyExtension>(MyExtension::class.java.name)

    private val rootService = DefaultServiceRegistry("rootService")

    @Test
    fun test1() {


        rootService.extensionArea.registerExtensionPoint(
            myExtensionName.name, MyExtension::class.java.name, ExtensionPoint.Kind.INTERFACE, true
        )

        myExtensionName.point
            .registerExtension(MyExtensions2()
            ) { println("dispose MyExtensions2") }

        myExtensionName.extensions.forEach {
            println(it.overrideThis())
        }

        Thread.sleep(2000)

        rootService.dispose()


    }
}


class MyExtensions2 : MyExtension {
    override fun overrideThis() = 1145
}

interface MyExtension {
    fun overrideThis(): Int
}