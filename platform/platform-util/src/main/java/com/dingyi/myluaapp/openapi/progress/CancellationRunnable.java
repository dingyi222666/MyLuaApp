// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.dingyi.myluaapp.openapi.progress;

import static com.dingyi.myluaapp.openapi.progress.Cancellation.withCurrentJob;

import kotlinx.coroutines.CompletableJob;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CancellationException;



import com.intellij.openapi.progress.CancellationCallable;

/**
 * A Runnable, which, when run, associates the calling thread with a job,
 * invokes original runnable, and completes the job.
 *
 * @see CancellationCallable
 */

public final class CancellationRunnable implements Runnable {

  private final @NotNull CompletableJob myJob;
  private final @NotNull Runnable myRunnable;

  public CancellationRunnable(@NotNull CompletableJob job, @NotNull Runnable runnable) {
    myJob = job;
    myRunnable = runnable;
  }

  @Override
  public void run() {
    try {
      withCurrentJob(myJob, () -> {
        myRunnable.run();
        return null;
      });
      myJob.complete();
    }
    catch (CancellationException e) {
      myJob.completeExceptionally(e);
    }
    catch (Throwable e) {
      myJob.completeExceptionally(e);
      throw e;
    }
  }

  @Override
  public String toString() {
    return myRunnable.toString();
  }
}