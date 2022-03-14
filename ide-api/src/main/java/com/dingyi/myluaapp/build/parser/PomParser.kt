package com.dingyi.myluaapp.build.parser

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

        val dependencies = mutableListOf<MavenPom.Dependency>()

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

    private fun parseDependencies(
        pullParser: XmlPullParser,
        dependencies: MutableList<MavenPom.Dependency>
    ) {
        var eventType = pullParser.eventType


        val tmpMap = mutableMapOf<String, Any>()

        var inDependencyTag = false

        while (eventType != XmlPullParser.END_DOCUMENT) {

            when (eventType) {
                XmlPullParser.START_TAG -> {

                    val name = pullParser.name

                    if (inDependencyTag) {

                        if (pullParser.name == "exclusions") {
                            parseExclusions(pullParser, tmpMap)
                        }else if (pullParser.next() == XmlPullParser.TEXT) {
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
                                dependencies.add(
                                    MavenPom.Dependency(
                                        dependencyId = "${tmpMap["groupId"]}:${tmpMap["artifactId"]}:${tmpMap["version"]}",
                                        exclusions = tmpMap["exclusions"] as List<String>?
                                            ?: listOf()
                                    )
                                )
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

    private fun parseExclusions(
        pullParser: XmlPullParser,
        dependencies: MutableMap<String, Any>
    ) {
        var eventType = pullParser.eventType


        val tmpMap = mutableMapOf<String, Any>()


        while (eventType != XmlPullParser.END_DOCUMENT) {

            when (eventType) {
                XmlPullParser.START_TAG -> {

                    val name = pullParser.name

                    if (pullParser.next() == XmlPullParser.TEXT) {
                        tmpMap[name] = pullParser.text
                    }


                }

                XmlPullParser.END_TAG -> {

                    when (pullParser.name) {
                        "exclusion" -> {

                            val mavenId = "${tmpMap["groupId"]}:${tmpMap["artifactId"]}"

                            val exclusions = dependencies.getOrDefault(
                                "exclusions",
                                mutableListOf<String>()
                            ) as MutableList<String>?

                            exclusions?.add(mavenId)

                            dependencies["exclusions"] = exclusions ?: mutableListOf<String>()

                            tmpMap.clear()
                        }
                        "exclusions" -> {
                            return // break parse dependencies
                        }
                    }
                }
            }

            eventType = pullParser.next()
        }
    }
}