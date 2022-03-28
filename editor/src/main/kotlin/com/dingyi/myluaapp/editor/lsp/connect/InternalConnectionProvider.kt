package com.dingyi.myluaapp.editor.lsp.connect

import android.util.Log
import org.eclipse.lsp4j.launch.LSPLauncher
import org.eclipse.lsp4j.services.LanguageServer
import java.io.*
import java.util.concurrent.CompletableFuture

class InternalConnectionProvider(
    private val block: () -> Pair<InputStream, OutputStream>
) : StreamConnectionProvider {

    private lateinit var inputStream: InputStream
    private lateinit var outputStream: OutputStream


    override fun start() {
        val (inputStream, outputStream) = block()
        this.inputStream = inputStream
        this.outputStream = outputStream

    }

    override fun getInputStream(): InputStream {
        return inputStream
    }

    override fun getOutputStream(): OutputStream {
        return outputStream
    }

    private var isStop = false

    override fun stop() {
        isStop = true
        inputStream.close()
        outputStream.close()
    }

    override fun isStop() = this.isStop
}