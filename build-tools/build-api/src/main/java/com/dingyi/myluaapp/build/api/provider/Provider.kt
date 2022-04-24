package com.dingyi.myluaapp.build.api.provider

import com.dingyi.myluaapp.build.api.Transformer
import java.util.concurrent.Callable
import java.util.function.BiFunction


/**
 * A container object that provides a value of a specific type. The value can be retrieved using one of the query methods such as [.get] or [.getOrNull].
 *
 *
 *
 * A provider may not always have a value available, for example when the value may not yet be known but will be known at some point in the future.
 * When a value is not available, [.isPresent] returns `false` and retrieving the value will fail with an exception.
 *
 *
 *
 *
 * A provider may not always provide the same value. Although there are no methods on this interface to change the value,
 * the provider implementation may be mutable or use values from some changing source. A provider may also provide a value that is mutable and that changes over time.
 *
 *
 *
 *
 * A provider may be used to represent a task output. Such a provider carries information about which task produces its value. When attached to a task input, this allows Gradle to automatically add dependencies between tasks based on the values they use as inputs and produce as outputs.
 *
 *
 *
 *
 * A typical use of a provider is to pass values from one Gradle model element to another, e.g. from a project extension to a task, or between tasks.
 * Providers also allow expensive computations to be deferred until their value is actually needed, usually at task execution time.
 *
 *
 *
 * There are a number of ways to create a [Provider] instance. Some common methods:
 *
 *
 *  * A number of Gradle types, such as [Property], extend [Provider] and can be used directly as a provider.
 *  * Calling [.map] to create a new provider from an existing provider.
 *  * Using the return value of [org.gradle.api.tasks.TaskContainer.register], which is a provider that represents the task instance.
 *  * Using the methods on [org.gradle.api.file.Directory] and [org.gradle.api.file.DirectoryProperty] to produce file providers.
 *  * By calling [ProviderFactory.provider] or [org.gradle.api.Project.provider] to create a new provider from a [Callable].
 *
 *
 *
 *
 * For a provider whose value can be mutated, see [Property] and the methods on [org.gradle.api.model.ObjectFactory].
 *
 *
 *
 * **Note:** This interface is not intended for implementation by build script or plugin authors.
 *
 * @param <T> Type of value represented by provider
 * @since 4.0
</T> */
interface Provider<T> {
    /**
     * Returns the value of this provider if it has a value present, otherwise throws `java.lang.IllegalStateException`.
     *
     * @return the current value of this provider.
     * @throws IllegalStateException if there is no value present
     */
    fun get(): T

    /**
     * Returns the value of this provider if it has a value present. Returns `null` a value is not available.
     *
     * @return the value or `null`
     */
    fun getOrNull(): T?

    /**
     * Returns the value of this provider if it has a value present. Returns the given default value if a value is not available.
     *
     * @return the value or the default value.
     * @since 4.3
     */
    fun getOrElse(defaultValue: T): T

    /**
     * Returns a new [Provider] whose value is the value of this provider transformed using the given function.
     *
     *
     * The new provider will be live, so that each time it is queried, it queries this provider and applies the transformation to the result.
     * Whenever this provider has no value, the new provider will also have no value and the transformation will not be called.
     *
     *
     * When this provider represents a task or the output of a task, the new provider will be considered an output of the task and will carry dependency information that Gradle can use to automatically attach task dependencies to tasks that use the new provider for input values.
     *
     * @param transformer The transformer to apply to values. May return `null`, in which case the provider will have no value.
     * @since 4.3
     */
    fun <S> map(transformer: Transformer<out S, in T>): Provider<S>

    /**
     * Returns a new [Provider] from the value of this provider transformed using the given function.
     *
     *
     * The new provider will be live, so that each time it is queried, it queries this provider and applies the transformation to the result.
     * Whenever this provider has no value, the new provider will also have no value and the transformation will not be called.
     *
     *
     * Any task details associated with this provider are ignored. The new provider will use whatever task details are associated with the return value of the function.
     *
     * @param transformer The transformer to apply to values. May return `null`, in which case the provider will have no value.
     * @since 5.0
     */
    fun <S> flatMap(transformer: Transformer<out Provider<out S>, in T>): Provider<S>

    /**
     * Returns `true` if there is a value present, otherwise `false`.
     *
     * @return `true` if there is a value present, otherwise `false`
     */
    val isPresent: Boolean

    /**
     * Returns a [Provider] whose value is the value of this provider, if present, otherwise the given default value.
     *
     * @param value The default value to use when this provider has no value.
     * @since 5.6
     */
    fun orElse(value: T): Provider<T>

    /**
     * Returns a [Provider] whose value is the value of this provider, if present, otherwise uses the value from the given provider, if present.
     *
     * @param provider The provider whose value should be used when this provider has no value.
     * @since 5.6
     */
    fun orElse(provider: Provider<out T>): Provider<T>

    /**
     * Returns a view of this [Provider] which can be safely read at configuration time.
     *
     * @since 6.5
     */
    fun forUseAtConfigurationTime(): Provider<T>

    /**
     * Returns a provider which value will be computed by combining this provider value with another
     * provider value using the supplied combiner function.
     *
     * If the supplied providers represents a task or the output of a task, the resulting provider
     * will carry the dependency information.
     *
     * @param right the second provider to combine with
     * @param combiner the combiner of values
     * @param <B> the type of the second provider
     * @param <R> the type of the result of the combiner
     * @return a combined provider
     *
     * @since 6.6
    </R></B> */
    fun <B, R> zip(right: Provider<B>, combiner: BiFunction<T, B, R>): Provider<R>
}
