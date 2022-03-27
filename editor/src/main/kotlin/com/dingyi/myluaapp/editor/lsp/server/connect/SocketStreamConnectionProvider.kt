package com.dingyi.myluaapp.editor.lsp.server.connect

import java.io.*
import java.net.Socket

class SocketStreamConnectionProvider(
    private val port:Int
):StreamConnectionProvider {


    private lateinit var socket: Socket
    private lateinit var inputStream: InputStream
    private lateinit var outputStream: OutputStream


    override fun start() {

        socket = Socket("localhost", port)
        inputStream = BufferedInputStream(socket.getInputStream())
        outputStream = BufferedOutputStream(socket.getOutputStream())
    }

    override fun getInputStream(): InputStream {
        return inputStream
    }

    override fun getOutputStream(): OutputStream {
        return outputStream
    }

    override fun stop() {
        try {
            socket.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}