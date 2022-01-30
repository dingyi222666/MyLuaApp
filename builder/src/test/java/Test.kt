import android.util.Xml
import com.dingyi.myluaapp.build.dependency.repository.LocalMavenRepository
import com.dingyi.myluaapp.build.modules.public.generator.SimpleJavaCodeGenerator
import com.dingyi.myluaapp.build.parser.PomParser
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

import org.xmlpull.v1.XmlPullParser
import java.io.FileReader


@RunWith(RobolectricTestRunner::class)
class Test {


    @Test
    fun main() {

        val repository = LocalMavenRepository("G:\\TestMaven")

        val dependency = repository.getDependency("com.google.android.material:material:1.0.0")


        val generator =
            SimpleJavaCodeGenerator(
                accessType = SimpleJavaCodeGenerator.AccessType.PUBLIC,
                className = "BuildConfig"
            )

        generator.addField(
            SimpleJavaCodeGenerator.Field(
                accessType = SimpleJavaCodeGenerator.AccessType.PUBLIC,
                isStatic = true,
                fieldName = "DEBUG",
                fieldType = "boolean",
                fieldValue = "true"
            )
        )

        println(generator.generate())

        println(dependency)
        println(dependency.getDependenciesFile())
    }
}