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


/**
 * A task main action annotation.
 * If marked on a method, will execute the method as the task main action.
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class TaskAction