package com.dingyi.myluaapp.build.parser

import android.util.Log
import android.util.Xml
import com.dingyi.myluaapp.build.dependency.MavenPom
import org.xmlpull.v1.XmlPullParser
import java.io.FileReader

class PomParser {

    fun parse(path: String): MavenPom {


        val pullParser = Xml.newPullParser()

        pullParser.setInput(FileReader(path))

        return parse(pullParser)
    }

    private fun parse(pullParser: XmlPullParser): MavenPom {

        var eventType = pullParser.eventType

        val dependencies = mutableListOf<String>()

        val tmpMap = mutableMapOf<String, String>()

        while (eventType != XmlPullParser.END_DOCUMENT) {

            when (eventType) {
                XmlPullParser.START_TAG -> {

                    when (pullParser.name) {
                        "artifactId", "groupId", "packaging", "name", "version", "type" -> {
                            tmpMap[pullParser.name] = pullParser.nextText()
                        }
                        "dependencies" -> parseDependencies(pullParser, dependencies)
                    }

                }
                XmlPullParser.TEXT -> {

                }

            }

            eventType = pullParser.next()
        }

        return MavenPom(
            name = tmpMap["name"] ?: throw RuntimeException("missed name"),
            groupId = tmpMap["groupId"] ?: throw RuntimeException("missed groupId"),
            artifactId = tmpMap["artifactId"] ?: throw RuntimeException("missed artifactId"),
            packaging = tmpMap["packaging"] ?: "jar",
            versionName = tmpMap["version"] ?: throw RuntimeException("missed version"),
            dependencies = dependencies,
        ).also {
            tmpMap.clear()
        }
    }

    private fun parseDependencies(pullParser: XmlPullParser, dependencies: MutableList<String>) {
        var eventType = pullParser.eventType


        val tmpMap = mutableMapOf<String, String>()

        var inDependencyTag = false

        while (eventType != XmlPullParser.END_DOCUMENT) {

            when (eventType) {
                XmlPullParser.START_TAG -> {

                    val name = pullParser.name

                    if (inDependencyTag) {

                        if (pullParser.next() == XmlPullParser.TEXT) {

                            tmpMap[name] = pullParser.text
                        }
                    }

                    if (name == "dependency") {
                        inDependencyTag = true
                    }

                }

                XmlPullParser.END_TAG -> {

                    when (pullParser.name) {
                        "dependency" -> {

                            if (tmpMap["scope"] != "test") {
                                dependencies.add("${tmpMap["groupId"]}:${tmpMap["artifactId"]}:${tmpMap["version"]}")
                            }
                            inDependencyTag = false
                            tmpMap.clear()
                        }
                        "dependencies" -> {
                            return // break parse dependencies
                        }
                    }
                }
            }

            eventType = pullParser.next()
        }
    }
}