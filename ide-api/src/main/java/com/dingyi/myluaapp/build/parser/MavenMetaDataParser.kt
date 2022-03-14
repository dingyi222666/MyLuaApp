package com.dingyi.myluaapp.build.parser

import android.util.Xml
import com.dingyi.myluaapp.build.dependency.MavenMetaData

import org.xmlpull.v1.XmlPullParser
import java.io.FileReader


class MavenMetaDataParser {

    fun parse(path: String): MavenMetaData {


        val pullParser = Xml.newPullParser()

        pullParser.setInput(FileReader(path))

        return parse(pullParser)
    }

    private fun parse(pullParser: XmlPullParser): MavenMetaData {

        var eventType = pullParser.eventType


        val tmpMap = mutableMapOf<String, String>()

        val versions = mutableListOf<String>()



        while (eventType != XmlPullParser.END_DOCUMENT) {

            when (eventType) {
                XmlPullParser.START_TAG -> {

                    when (pullParser.name) {
                        "artifactId", "groupId" -> {
                            tmpMap[pullParser.name] = pullParser.nextText()
                        }
                        "versioning" -> parseVersion(pullParser, versions, tmpMap)
                    }

                }


            }

            eventType = pullParser.next()
        }

        return MavenMetaData(
            groupId = tmpMap["groupId"].toString(),
            artifactId = tmpMap["artifactId"].toString(),
            versioning = MavenMetaData.Version(
                versions = versions,
                latest = tmpMap["latest"].toString(),
                release = tmpMap["release"].toString()
            )
        )

    }

    private fun parseVersion(
        pullParser: XmlPullParser,
        versions: MutableList<String>,
        tmpMap: MutableMap<String, String>
    ) {
        var eventType = pullParser.eventType


        while (eventType != XmlPullParser.END_DOCUMENT) {

            when (eventType) {
                XmlPullParser.START_TAG -> {

                    when (pullParser.name) {
                        "latest", "release" -> {
                            tmpMap[pullParser.name] = pullParser.nextText()
                        }
                        "versions" -> parseVersions(pullParser, versions)
                    }

                }

                XmlPullParser.END_TAG ->
                    if (pullParser.name == "versioning") {
                        break
                    }

            }

            eventType = pullParser.next()
        }


    }

    private fun parseVersions(pullParser: XmlPullParser, versions: MutableList<String>) {
        var eventType = pullParser.eventType


        while (eventType != XmlPullParser.END_DOCUMENT) {

            when (eventType) {
                XmlPullParser.START_TAG -> {

                    if (pullParser.name == "version") {
                        versions.add(pullParser.nextText())
                    }

                }

                XmlPullParser.END_TAG ->
                    if (pullParser.name == "versions") {
                        break
                    }


            }

            eventType = pullParser.next()
        }


    }
}