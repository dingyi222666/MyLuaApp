// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.dingyi.myluaapp.openapi.extensions;

import com.intellij.openapi.Disposable;
import com.dingyi.myluaapp.openapi.extensions.impl.ExtensionComponentAdapter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.TestOnly;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

/**
 *
 */
public interface ExtensionPoint<T extends Object> {
    /**
     * @deprecated Use  or {@link #registerExtension(Object, Disposable)}.
     */
    @Deprecated
    void registerExtension(T extension);

    @TestOnly
    void registerExtension(T extension, @NotNull Disposable parentDisposable);

    @TestOnly
    void registerExtension(T extension, @NotNull PluginDescriptor pluginDescriptor, @NotNull Disposable parentDisposable);

    /**
     * Use
     * to register extension as first or to completely replace existing extensions in tests.
     */
    @TestOnly
    void registerExtension(T extension, @NotNull LoadingOrder order, @NotNull Disposable parentDisposable);

    /**
     * Prefer to use {@link #getExtensionList()}.
     */
    T @NotNull [] getExtensions();

    @NotNull
    List<T> getExtensionList();

    @NotNull
    Stream<T> extensions();

    int size();

    /**
     * @deprecated Use another solution to unregister not applicable extension, because this method instantiates all extensions.
     */
    @Deprecated
    void unregisterExtension(T extension);

    /**
     * Unregisters an extension of the specified type.
     * <p>
     * Please note that you can deregister service specifying empty implementation class.
     * <p>
     * Consider to use {@link ExtensionNotApplicableException} instead.
     */
    void unregisterExtension(@NotNull Class<? extends T> extensionClass);

    /**
     * Unregisters extensions for which the specified predicate returns false.
     * <p>
     * Consider to use {@link ExtensionNotApplicableException} instead.
     */
    boolean unregisterExtensions(@NotNull BiPredicate<? super String, ? super ExtensionComponentAdapter> extensionClassNameFilter, boolean stopAfterFirstMatch);

    void addExtensionPointListener(@NotNull ExtensionPointListener<T> listener, boolean invokeForLoadedExtensions, @Nullable Disposable parentDisposable);


    void addExtensionPointListener(@NotNull ExtensionPointChangeListener listener,
                                   boolean invokeForLoadedExtensions,
                                   @Nullable Disposable parentDisposable);

    /**
     * Consider using {@link ExtensionPointName#addChangeListener}
     */
    void addChangeListener(@NotNull Runnable listener, @Nullable Disposable parentDisposable);

    void removeExtensionPointListener(@NotNull ExtensionPointListener<T> extensionPointListener);

    /**
     * @return true if the EP allows adding/removing extensions at runtime
     */
    boolean isDynamic();

    @NotNull
    PluginDescriptor getPluginDescriptor();

    enum Kind {INTERFACE, BEAN_CLASS}
}
