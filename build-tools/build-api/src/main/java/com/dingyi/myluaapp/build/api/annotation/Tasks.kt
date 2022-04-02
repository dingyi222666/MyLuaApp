package com.dingyi.myluaapp.build.api.annotation

/**
 * A task input annotation.
 * If marked on a field, will inject the input into the filed on runtime.
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class TaskInput


/**
 * A task output annotation.
 * If marked on a field, will inject the output into the filed on runtime.
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class TaskOutput