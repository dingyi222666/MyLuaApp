import com.dingyi.myluaapp.openapi.extensions.ExtensionPointName
import com.dingyi.myluaapp.openapi.extensions.ExtensionsArea
import com.dingyi.myluaapp.openapi.extensions.impl.ExtensionsAreaImpl
import org.junit.Test
import kotlin.test.assertEquals

class ExtensionPointTest {

    @Test
    fun testExtensionPoint() {
        val area = ExtensionsAreaImpl()
        val extensionPointName =
            ExtensionPointName.create<TestExtensionPointInterface>("com.dingyi.myluaapp.openapi.extensions.ExtensionPointListener")

        area.registerExtensionPoint(
            extensionPointName.name,
            TestExtensionPointInterface::class.java
        )

        val extensionPoint = area.getExtensionPoint(extensionPointName)

        extensionPoint.registerExtension { input -> input + 1 }

        extensionPoint.registerExtension { input -> input + 2 }

        val result = extensionPoint.extensionList.fold(0) { acc, extension -> extension.test(acc) }

        assertEquals(3, result)
    }
}

fun interface TestExtensionPointInterface {
    fun test(input: Int): Int
}