// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
@file:Suppress("ReplacePutWithAssignment")

package com.dingyi.myluaapp.serviceContainer


import com.dingyi.myluaapp.diagnostic.PluginException
import com.dingyi.myluaapp.openapi.extensions.PluginDescriptor
import com.dingyi.myluaapp.openapi.extensions.PluginId
import com.dingyi.myluaapp.openapi.progress.Cancellation
import com.intellij.diagnostic.LoadingState
import com.intellij.openapi.progress.ProcessCanceledException
import kotlinx.coroutines.*
import kotlinx.coroutines.future.asCompletableFuture
import org.picocontainer.ComponentAdapter
import java.lang.invoke.MethodHandles
import java.lang.invoke.VarHandle
import java.lang.reflect.Field

@OptIn(ExperimentalCoroutinesApi::class)
internal sealed class BaseComponentAdapter(
    @JvmField internal val componentManager: ComponentManagerImpl,
    @JvmField val pluginDescriptor: PluginDescriptor,
    private val deferred: CompletableDeferred<Any>,
    private var implementationClass: Class<*>?,
) : ComponentAdapter {
    companion object {
        private val IS_DEFERRED_PREPARED: Field
        private val INITIALIZING: Field

        init {
            IS_DEFERRED_PREPARED =
                BaseComponentAdapter::class.java.getDeclaredField("isDeferredPrepared").apply {
                    isAccessible = true
                }
            INITIALIZING = BaseComponentAdapter::class.java.getDeclaredField("initializing").apply {
                isAccessible = true
            }
        }
    }

    @Suppress("unused")
    private var isDeferredPrepared = false

    @Suppress("unused")
    private var initializing = false

    val pluginId: PluginId
        get() = pluginDescriptor.pluginId

    protected abstract val implementationClassName: String

    protected abstract fun isImplementationEqualsToInterface(): Boolean

    final override fun getComponentImplementation(): Class<*> = getImplementationClass()

    @Synchronized
    fun getImplementationClass(): Class<*> {
        var result = implementationClass
        if (result == null) {
            try {
                result = componentManager.loadClass<Any>(implementationClassName, pluginDescriptor)
            } catch (e: ClassNotFoundException) {
                throw PluginException(
                    "Failed to load class: $implementationClassName",
                    e,
                    pluginDescriptor.pluginId
                )
            }
            implementationClass = result
        }
        return result
    }

    fun getInitializedInstance(): Any? = if (deferred.isCompleted) deferred.getCompleted() else null

    @Suppress("DeprecatedCallableAddReplaceWith")
    @Deprecated("Do not use")
    final override fun getComponentInstance(): Any? {
        //LOG.error("Use getInstance() instead")
        return getInstance(componentManager, null)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> getInstance(
        componentManager: ComponentManagerImpl,
        keyClass: Class<T>?,
        createIfNeeded: Boolean = true
    ): T? {
        if (deferred.isCompleted) {
            return deferred.getCompleted() as T
        } else if (!createIfNeeded) {
            return null
        }

        LoadingState.COMPONENTS_REGISTERED.checkOccurred()
        checkContainerIsActive(componentManager)


        if (IS_DEFERRED_PREPARED.compareAndSet(this, false, true)) {
            return createInstance(keyClass, componentManager)
        }

        // without this check, will be a deadlock if during createInstance we call createInstance again (cyclic initialization)
        if (Thread.holdsLock(this)) {
            throw PluginException("Cyclic service initialization: ${toString()}", pluginId)
        }

        val result = (deferred as Deferred<T>).asCompletableFuture().join()

        return result
    }

    @Synchronized
    private fun <T : Any> createInstance(
        keyClass: Class<T>?,
        componentManager: ComponentManagerImpl
    ): T {
        check(!deferred.isCompleted)
        check(INITIALIZING.compareAndSet(this, false, true)) {
            PluginException("Cyclic service initialization: ${toString()}", pluginId)
        }

        return Cancellation.computeInNonCancelableSection<T, RuntimeException> {
            doCreateInstance(keyClass, componentManager)
        }
    }

    private fun <T : Any> doCreateInstance(
        keyClass: Class<T>?,
        componentManager: ComponentManagerImpl,
    ): T {
        try {
            val implementationClass: Class<T>
            if (keyClass != null && isImplementationEqualsToInterface()) {
                implementationClass = keyClass
                this.implementationClass = keyClass
            } else {
                @Suppress("UNCHECKED_CAST")
                implementationClass = getImplementationClass() as Class<T>
            }

            val instance = doCreateInstance(componentManager, implementationClass)


            deferred.complete(instance)
            return instance
        } catch (e: Throwable) {
            deferred.completeExceptionally(e)
            throw e
        } finally {
            INITIALIZING.set(this, false)
        }
    }

    @Suppress("UNCHECKED_CAST")
    suspend fun <T : Any> getInstanceAsync(
        componentManager: ComponentManagerImpl,
        keyClass: Class<T>?
    ): Deferred<T> {
        return withContext(NonCancellable) {
            if (!IS_DEFERRED_PREPARED.compareAndSet(this@BaseComponentAdapter, false, true)) {
                return@withContext deferred as Deferred<T>
            }

            createInstance(
                keyClass = keyClass,
                componentManager = componentManager,
            )
            deferred as Deferred<T>
        }
    }

    /**
     * Indicator must be always passed - if under progress, then ProcessCanceledException will be thrown instead of AlreadyDisposedException.
     */
    private fun checkContainerIsActive(componentManager: ComponentManagerImpl) {
        if (isUnderIndicatorOrJob()) {
            checkCanceledIfNotInClassInit()
        }

        if (componentManager.isDisposed()) {
            throwAlreadyDisposedError(toString(), componentManager)
        }
        if (!isGettingServiceAllowedDuringPluginUnloading(pluginDescriptor)) {
            componentManager.componentContainerIsReadonly?.let {
                val error = RuntimeException(
                    "Cannot create ${toString()} because container in read-only mode (reason=$it, container=${componentManager})"
                )
                throw if (!isUnderIndicatorOrJob()) error else ProcessCanceledException(error)
            }
        }

    }

    internal fun throwAlreadyDisposedError(componentManager: ComponentManagerImpl) {
        throwAlreadyDisposedError(toString(), componentManager)
    }


    protected abstract fun <T : Any> doCreateInstance(
        componentManager: ComponentManagerImpl,
        implementationClass: Class<T>
    ): T
}

fun Field.compareAndSet(obj: Any, expected: Any, set: Any): Boolean {
    val value = get(obj)
    if (value != expected) {
        set(obj, set)
        return false
    }
    return true
}