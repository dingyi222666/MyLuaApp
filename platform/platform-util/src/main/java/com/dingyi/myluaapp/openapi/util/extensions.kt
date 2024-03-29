package com.dingyi.myluaapp.openapi.util

import com.intellij.openapi.Disposable
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.util.Pair

/** Allows deconstruction of `com.intellij.openapi.util.Pair`. */
operator fun <A, B> Pair<A, B>.component1(): A = this.first
operator fun <A, B> Pair<A, B>.component2(): B = this.second

/** Helps to get rid of platform types. */
fun <A : Any, B : Any> Pair<A?, B?>.toNotNull(): kotlin.Pair<A, B> = requireNotNull(first) to requireNotNull(second)

/**
 * Executes the given [block] function on this object and then disposes it correctly whether an exception is thrown or not.
 *
 * @param block a function to process this [Disposable] object.
 * @return the result of [block] function invoked on this object.
 * @see kotlin.io.use
 * @see Disposer.dispose
 */
inline fun <T : Disposable?, R> T.use(block: (T) -> R): R {
  try {
    return block(this)
  }
  finally {
    if (this != null) {
        Disposer.dispose(this)
    }
  }
}
