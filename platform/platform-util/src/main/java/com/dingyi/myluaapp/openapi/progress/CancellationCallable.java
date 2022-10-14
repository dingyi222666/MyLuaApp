// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.dingyi.myluaapp.openapi.progress;

import kotlinx.coroutines.CompletableDeferred;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Callable;

import static com.dingyi.myluaapp.openapi.progress.Cancellation.withCurrentJob;
import static com.intellij.openapi.progress.Cancellation.withCurrentJob;

import com.intellij.openapi.progress.CancellationFutureTask;
import com.intellij.openapi.progress.CancellationRunnable;

/**
 * A Callable, which, when called, associates the calling thread with a job,
 * invokes original callable, and completes the job with its result.
 *
 * @see CancellationFutureTask
 * @see CancellationRunnable
 */

public final class CancellationCallable<V> implements Callable<V> {

  private final @NotNull CompletableDeferred<V> myDeferred;
  private final @NotNull Callable<? extends V> myCallable;

  public CancellationCallable(@NotNull CompletableDeferred<V> deferred, @NotNull Callable<? extends V> callable) {
    myDeferred = deferred;
    myCallable = callable;
  }

  @Override
  public V call() throws Exception {
    try {
      V result = withCurrentJob(myDeferred, myCallable::call);
      myDeferred.complete(result);
      return result;
    }
    catch (Throwable e) {
      myDeferred.completeExceptionally(e);
      throw e;
    }
  }
}
