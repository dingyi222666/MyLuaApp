package com.dingyi.myluaapp.build.dependency

data class MavenMetaData(
    var groupId: String,
    var artifactId: String,
    val versioning: Version
) {
    data class Version(
        val versions: List<String>,
        val latest: String?,
        val release: String?
    )
}