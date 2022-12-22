package com.dingyi.myluaapp.openapi.components

import java.lang.annotation.RetentionPolicy
import kotlin.reflect.KClass


/**
 * Defines persistence storage location and options.
 * See [Persisting States](http://www.jetbrains.org/intellij/sdk/docs/basics/persisting_state_of_components.html).
 */
@Retention(AnnotationRetention.RUNTIME)
annotation class Storage(

    /**
     * Relative to component container configuration root path.
     * Consider using shorthand form - `@Storage("yourName.xml")` (when you need to specify only file path).
     *
     *
     * Consider reusing existing storage files instead of a new one to avoid creating new ones. Related components should reuse the same storage file.
     *
     * @see StoragePathMacros
     */
    val value: String = "",
    /**
     * If deprecated, data will be removed on write. And ignored on read if (and only if) new storage exists.
     */
    val deprecated: Boolean = false,


    /**
     * Class must have constructor `(String fileSpec, ComponentManager componentManager, StateStorageManager storageManager)`.
     * `componentManager` parameter can have more concrete type - e.g. `Module` (if storage intended to support only one type).
     */
    val storageClass: KClass<out StateStorage> = StateStorage::class,

    /**
     * Is exportable (Export Settings dialog) regardless of roaming type.
     */
    val exportable: Boolean = false,

    val exclusive: Boolean = false
)