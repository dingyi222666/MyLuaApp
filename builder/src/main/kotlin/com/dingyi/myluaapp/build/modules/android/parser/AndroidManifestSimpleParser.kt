package com.dingyi.myluaapp.build.modules.android.parser

import android.util.Xml
import com.dingyi.myluaapp.common.kts.toFile
import org.xmlpull.v1.XmlPullParser
import java.io.FileReader

class AndroidManifestSimpleParser {

    fun parse(path: String): AndroidManifestInfo {
        val xmlPullParser = Xml.newPullParser()
        xmlPullParser.setInput(FileReader(path))
        return parse(xmlPullParser,path.toFile().readText())
    }


    fun parse(pullParser: XmlPullParser,source:String): AndroidManifestInfo {


        var eventType = pullParser.eventType

        val tmpMap = mutableMapOf<String, String>()

        while (eventType != XmlPullParser.END_DOCUMENT) {

            when (eventType) {
                XmlPullParser.START_TAG -> {
                    when (pullParser.name) {
                        "manifest", "uses-sdk" -> {
                            for (i in 0 until pullParser.attributeCount) {
                                tmpMap[pullParser.getAttributeName(i)] =
                                    pullParser.getAttributeValue(i)
                            }
                        }
                    }

                }
            }

            eventType = pullParser.next()
        }


        return AndroidManifestInfo(
            packageId = tmpMap["package"],
            minSdk = tmpMap["minSdkVersion"]?.toInt(),
            maxSdk = tmpMap["maxSdkVersion"]?.toInt(),
            targetSdk = tmpMap["targetSdkVersion"]?.toInt(),
            versionCode = tmpMap["versionCode"]?.toInt(),
            versionName = tmpMap["versionName"],
            compileSdkVersion = tmpMap["compileSdkVersion"]?.toInt(),
            compileSdkVersionCodeName = tmpMap["compileSdkVersionCodeName"]?.toInt()
        ).apply {
            this.androidManifestSourceCode = source
        }

    }


    fun deParser(androidManifestInfo: AndroidManifestInfo): String {
        TODO()
    }

    class AndroidManifestInfo(
        var packageId: String?,
        var minSdk: Int?,
        var maxSdk: Int?,
        var targetSdk: Int?,
        var versionCode: Int?,
        var versionName: String?,
        var compileSdkVersion: Int?,
        var compileSdkVersionCodeName: Int?,
    ) {
        var androidManifestSourceCode: String = ""
    }
}