// Copyright 2000-2020 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.dingyi.myluaapp.openapi.extensions

import com.intellij.openapi.util.text.StringUtil

class SortingException internal constructor(
    message: String,
    vararg conflictingElements: LoadingOrder.Orderable
) : RuntimeException(
    "$message: " + StringUtil.join(
        conflictingElements,
        { item: LoadingOrder.Orderable -> item.orderId + "(" + item.order + ")" },
        "; "
    )
) {
    val conflictingElements: Array<LoadingOrder.Orderable>

    init {
        this.conflictingElements = conflictingElements as Array<LoadingOrder.Orderable>
    }
}