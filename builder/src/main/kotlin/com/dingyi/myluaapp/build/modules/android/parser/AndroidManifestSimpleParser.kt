package com.dingyi.myluaapp.build.modules.android.parser

class AndroidManifestSimpleParser {

    fun parser(path: String): AndroidManifestInfo {
        TODO()
    }

    fun deParser(androidManifestInfo: AndroidManifestInfo):String {
        TODO()
    }

    class AndroidManifestInfo(
        packageId: String,
        minSdk: Int = 16,
        maxSdk: Int? = null,
        targetSdk: Int = 31,
        versionCode: Int = 1,
        versionString: String = "1.0.0",
        compileSdkVersion: Int = 30,
        compileSdkVersionCodeName: Int = 12,
    ) {
        var xmlString: String = ""
    }
}