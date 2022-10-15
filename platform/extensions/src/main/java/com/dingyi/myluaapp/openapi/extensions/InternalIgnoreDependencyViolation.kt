// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.dingyi.myluaapp.openapi.extensions

/**
 * See https://youtrack.jetbrains.com/articles/IDEA-A-65/Plugin-Model#internalignoredependencyviolation
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
annotation class InternalIgnoreDependencyViolation