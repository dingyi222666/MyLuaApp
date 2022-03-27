package com.dingyi.myluaapp.editor.lsp.ktx

import org.eclipse.lsp4j.PublishDiagnosticsParams
import java.net.URI

typealias DiagnosticHandler = (PublishDiagnosticsParams) -> Unit

fun String.toURI() = URI(this)