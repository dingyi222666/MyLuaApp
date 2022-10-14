// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.dingyi.myluaapp.openapi.progress;

import com.intellij.openapi.progress.ProcessCanceledException;


import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CancellationException;


public final class IndicatorCancellationException extends CancellationException {

  IndicatorCancellationException(@NotNull ProcessCanceledException pce) {
    initCause(pce);
  }
}
