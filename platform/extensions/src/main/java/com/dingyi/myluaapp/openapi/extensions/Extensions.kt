// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.dingyi.myluaapp.openapi.extensions

import com.dingyi.myluaapp.openapi.extensions.impl.ExtensionsAreaImpl
import com.intellij.openapi.Disposable
import com.intellij.openapi.util.Disposer
import org.jetbrains.annotations.TestOnly
import java.util.Objects

object Extensions {
    private var ourRootArea: ExtensionsAreaImpl? = null
    fun setRootArea(area: ExtensionsAreaImpl) {
        ourRootArea = area
    }

    @TestOnly
    fun setRootArea(area: ExtensionsAreaImpl, parentDisposable: Disposable) {
        val oldRootArea = ourRootArea
        ourRootArea = area
        Disposer.register(parentDisposable) {
            ourRootArea!!.notifyAreaReplaced(oldRootArea)
            ourRootArea = oldRootArea
        }
    }
}