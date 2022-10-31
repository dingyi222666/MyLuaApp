package com.dingyi.myluaapp.ide.util

/**
 * Allows to pass property name of field
 *
 * @author Konstantin Bulenkov
 */
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class PropertyName(val value: String, val defaultValue: String = NOT_SET) {
    companion object {
        const val NOT_SET = ""
    }
}
