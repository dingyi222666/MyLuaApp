package com.dingyi.myluaapp.editor.lsp.server.connect

import java.io.InputStream
import java.io.OutputStream

interface StreamConnectionProvider {

    fun start()

    fun getInputStream(): InputStream

    fun getOutputStream(): OutputStream

    fun stop()
}