// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.dingyi.myluaapp.openapi.extensions

import com.intellij.openapi.diagnostic.ControlFlowException
import java.util.function.Supplier

/**
 * Throw in an extension class constructor to mark the extension as not applicable.
 */
class ExtensionNotApplicableException private constructor(withStacktrace: Boolean) :
    RuntimeException(null, null, false, withStacktrace), ControlFlowException {
    companion object {

        @Deprecated("Use {@link #create()}")
        val INSTANCE = ExtensionNotApplicableException(false)
        private lateinit var factory: Supplier<ExtensionNotApplicableException>

        init {
            if (System.getenv("TEAMCITY_VERSION") == null) {
                factory = Supplier { INSTANCE }
            } else {
                useFactoryWithStacktrace()
            }
        }

        fun create(): ExtensionNotApplicableException {
            return factory.get()
        }

        fun useFactoryWithStacktrace() {
            factory = Supplier { ExtensionNotApplicableException(true) }
        }
    }
}