package com.dingyi.myluaapp.editor.lsp.server.connect

import java.io.*

class InternalConnectionProvider(

) : StreamConnectionProvider {

    private lateinit var inputStream: PipedInputStream
    private lateinit var outputStream: PipedOutputStream

    override fun start() {
        inputStream = PipedInputStream()
        outputStream = PipedOutputStream()
        inputStream.connect(outputStream)
    }

    override fun getInputStream(): InputStream {
        return inputStream
    }

    override fun getOutputStream(): OutputStream {
        return outputStream
    }

    override fun stop() {
        inputStream.close()
        outputStream.close()
    }
}