package com.dingyi.myluaapp.editor.lsp.server.java.parser

import com.github.javaparser.ParserConfiguration
import com.github.javaparser.utils.SourceRoot
import java.io.File
import java.util.concurrent.CompletableFuture

class ParserHelper {

    private lateinit var rootFile: File


    private lateinit var sourceRoot: SourceRoot

    fun setRootProject(file: File) {
        this.rootFile = file
    }


    fun parserAllSource() = CompletableFuture.supplyAsync {

        sourceRoot = SourceRoot(
            rootFile.toPath(),
            ParserConfiguration().setLanguageLevel(ParserConfiguration.LanguageLevel.JAVA_14)
        )


        sourceRoot.tryToParse()
    }
}
