package com.dingyi.myluaapp.editor.lsp.server.lua

import org.eclipse.lsp4j.launch.LSPLauncher
import java.io.*
import java.net.ServerSocket
import java.util.concurrent.CompletableFuture
import kotlin.concurrent.thread

object Main {


    private lateinit var socketServer: ServerSocket

    private lateinit var currentStream: Pair<InputStream, OutputStream>

    private lateinit var currentServer: LuaLanguageServer

    private lateinit var currentThread: Thread


    fun startWithSocket(args: Array<String>) {

        if (this::socketServer.isInitialized && !socketServer.isClosed &&
            this::currentServer.isInitialized && !currentServer.isClose()
        ) {
            return
        }

        if (this::currentThread.isInitialized && currentThread.isAlive) {
            currentThread.interrupt()
        }
        val port = args.getOrNull(0)?.toInt() ?: 0
        socketServer = ServerSocket(port)
        currentThread = thread {

            println("waiting for client on port $port")
            val socket = socketServer.accept()
            checkNotNull(socket) { "Failed to connect to client" }
            val server = LuaLanguageServer()
            val launcher = LSPLauncher
                .createServerLauncher(server, socket.getInputStream(), socket.getOutputStream())

            launcher.startListening()

            server.connect(launcher.remoteProxy)

            currentServer = server
        }
        return
    }

    fun close() {
        if (this::currentStream.isInitialized) {
            currentStream.first.close()
            currentStream.second.close()
        }
        if (this::socketServer.isInitialized && !socketServer.isClosed) {
            socketServer.close()
        }

    }

    fun startWithPipedStream(): Pair<InputStream, OutputStream> {

        if (this::currentStream.isInitialized &&
            this::currentServer.isInitialized &&
            !currentServer.isClose()
        ) {
            return currentStream
        } else {
            if (this::currentStream.isInitialized) {
                currentStream.toList().forEach { it.close() }
            }
        }

        if (this::currentThread.isInitialized && currentThread.isAlive) {
            currentThread.interrupt()
        }

        val inputStream = PipedInputStream()
        val outputStream = PipedOutputStream()

        val result = inputStream to outputStream

        currentStream = result
        currentThread = thread {
            val server = LuaLanguageServer()
            inputStream.connect(outputStream)
            val launcher = LSPLauncher
                .createServerLauncher(server, inputStream, outputStream)

            launcher.startListening()

            server.connect(launcher.remoteProxy)

        }

        return currentStream

    }
}