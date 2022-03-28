package com.dingyi.myluaapp.editor.lsp.server.lua

import org.eclipse.lsp4j.launch.LSPLauncher
import java.io.Closeable
import java.net.ServerSocket
import kotlin.concurrent.thread

object Main {


    private lateinit var socketServer: ServerSocket

    @JvmStatic
    fun startWithSocket(args: Array<String>) {
        thread {
            if (this::socketServer.isInitialized && !socketServer.isClosed) {
                return@thread
            }
            socketServer = ServerSocket(args[0].toInt())
            println("waiting for client on port ${args[0]}")
            val socket = socketServer.accept()
            checkNotNull(socket) { "Failed to connect to client" }
            val server = LuaLanguageServer()
            val launcher = LSPLauncher
                .createServerLauncher(server, socket.getInputStream(), socket.getOutputStream())

            launcher.startListening()

            server.connect(launcher.remoteProxy)
        }
    }
}