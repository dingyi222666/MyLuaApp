package com.dingyi.myluaapp.build.api.file.resolver

import java.io.File
import java.net.URI

interface FileResolver {

    fun resolve(path: Any): File?

    fun resolveUri(path: Any): URI?


    fun newResolver(baseDir: File): FileResolver

}