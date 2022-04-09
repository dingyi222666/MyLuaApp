package com.dingyi.myluaapp.build.api.tests

import com.dingyi.myluaapp.build.api.plugins.ExtensionAware
import org.junit.Test


@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class TestInject(val value: String)


class Test1 {
    @TestInject("test")
    var test: String = ""
}

@Test
fun main() {

    val testClass = Test1::class.java

    (testClass.fields + testClass.declaredFields).forEach {
        it.isAccessible = true
        println(it.annotatedType == TestInject::class.java)
        println(it.isAnnotationPresent(TestInject::class.java))
        println(it.getAnnotation(TestInject::class.java))
        println(it.toString())
    }

    val test = Test1()


}

class Test2 {
    @Test
    fun test() {
        val container: ExtensionAware? = null
        container?.let {
            it.getExtensions()
                .add(
                    "android",
                    "com.android.tools.build.gradle.internal.plugins.AndroidExtension"
                )


            it.getExtensions()
                .getByName("android").apply {

                }
        }

    }
}