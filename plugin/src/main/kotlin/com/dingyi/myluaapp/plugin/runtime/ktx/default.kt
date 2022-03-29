package com.dingyi.myluaapp.plugin.runtime.ktx

/**
 * Get a random socket port and close it
 */
 fun getRandomPort(): Int {
    val serverSocket = java.net.ServerSocket(0)
    val port = serverSocket.localPort
    serverSocket.close()
    return port
}