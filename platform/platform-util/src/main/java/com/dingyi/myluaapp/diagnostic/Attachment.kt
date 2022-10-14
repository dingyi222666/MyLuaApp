// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.dingyi.myluaapp.diagnostic

import com.intellij.openapi.util.NlsSafe
import com.intellij.util.ArrayUtil
import com.intellij.util.ExceptionUtil
import com.intellij.util.PathUtilRt
import java.io.ByteArrayInputStream
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.util.Base64

/**
 * @see com.dingyi.myluaapp.diagnostic.AttachmentFactory
 */
class Attachment private constructor(
    val path: String,
    val displayText: String,
    private val myBytes: ByteArray?,
    private val myTemporaryFile: Path?
) {
    var isIncluded // opt-out for traces, opt-in otherwise
            = false

    constructor(name: String, throwable: Throwable) : this(
        "$name.trace",
        ExceptionUtil.getThrowableText(throwable)
    ) {
        isIncluded = true
    }

    constructor(path: String, content: String) : this(path, content, null, null) {}
    constructor(path: String, bytes: ByteArray, displayText: String) : this(
        path,
        displayText,
        bytes,
        null
    ) {
    }

    constructor(path: String, temporaryFile: Path, displayText: String) : this(
        path,
        displayText,
        null,
        temporaryFile
    ) {
    }

    constructor(path: String, temporaryFile: File, displayText: String) : this(
        path,
        displayText,
        null,
        temporaryFile.toPath()
    ) {
    }

    val name: @NlsSafe String
        get() = PathUtilRt.getFileName(path)
    val encodedBytes: String
        get() = Base64.getEncoder().encodeToString(myBytes)
    val bytes: ByteArray
        get() {
            if (myBytes != null) {
                return myBytes
            }
            if (myTemporaryFile != null) {
                try {
                    return Files.readAllBytes(myTemporaryFile)
                } catch (e: IOException) {
                    LOG.error(
                        "Failed to read attachment content from temp. file $myTemporaryFile",
                        e
                    )
                }
            }
            return displayText.toByteArray(StandardCharsets.UTF_8)
        }

    fun openContentStream(): InputStream {
        if (myBytes != null) {
            return ByteArrayInputStream(myBytes)
        }
        if (myTemporaryFile != null) {
            try {
                return Files.newInputStream(myTemporaryFile)
            } catch (e: IOException) {
                LOG.error("Failed to read attachment content from temp. file $myTemporaryFile", e)
            }
        }
        return ByteArrayInputStream(displayText.toByteArray(StandardCharsets.UTF_8))
    }

    companion object {
        private val LOG = Logger.getInstance(
            Attachment::class.java
        )
        val EMPTY_ARRAY = arrayOfNulls<Attachment>(0)
    }
}