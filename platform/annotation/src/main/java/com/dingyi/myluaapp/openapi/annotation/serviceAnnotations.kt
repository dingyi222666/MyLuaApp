package com.dingyi.myluaapp.openapi.annotation

import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.annotation.AnnotationTarget.CONSTRUCTOR
import kotlin.annotation.AnnotationTarget.FIELD
import kotlin.annotation.AnnotationTarget.PROPERTY


@Target(CLASS, FIELD, PROPERTY,CONSTRUCTOR)
@Retention(RUNTIME)
annotation class Inject(
    val scope: ServiceScope = ServiceScope.Application
)

enum class ServiceScope {
    Application,
    Project,
    Module,
}

@Target(CLASS, FIELD, PROPERTY)
@Retention(RUNTIME)
annotation class Service(
    val scope: ServiceScope = ServiceScope.Application,
)

