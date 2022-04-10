package com.dingyi.myluaapp.build.api.tests

import com.dingyi.myluaapp.build.api.internal.plugins.DefaultExtensionContainer
import com.dingyi.myluaapp.build.api.plugins.ExtensionAware
import com.dingyi.myluaapp.build.api.plugins.ExtensionContainer
import org.junit.Test
import org.junit.Assert.*


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

class TestBean {
    var test = ""
    var age = 12
    override fun toString(): String {
        return "TestBean(test='$test', age=$age)"
    }

}

class Test2 {
    @Test
    fun test() {
        val container = DefaultExtensionContainer()
        val testBean = TestBean()
        container.add("test", testBean)
        assertEquals(testBean, container.getByName("test"))
        println(container.getByName("test"))
        container.configure("test") { it: TestBean ->
            it.apply {
                test = "configure"
                age = 120
            }
        }
        println(container.getByName("test"))
        assertEquals(testBean, container.getByName("test"))

    }
}