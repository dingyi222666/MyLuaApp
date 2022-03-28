package com.dingyi.myluaapp.editor.lsp.connect

import android.util.Log
import org.eclipse.lsp4j.launch.LSPLauncher
import org.eclipse.lsp4j.services.LanguageServer
import java.io.*

class InternalConnectionProvider(
    private val server: LanguageServer
) : StreamConnectionProvider {

    private lateinit var inputStream: PipedInputStream
    private lateinit var outputStream: PipedOutputStream


    override fun start() {
        inputStream = PipedInputStream()
        outputStream = PipedOutputStream()
        inputStream.connect(outputStream)


        kotlin.runCatching {
            LSPLauncher
                .createServerLauncher(server, inputStream, outputStream)
                .startListening()
        }.onFailure {
            Log.e("InternalConnectionProvider","start language server failed", it)
        }

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