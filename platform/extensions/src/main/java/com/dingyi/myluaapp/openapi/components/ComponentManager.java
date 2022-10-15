// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.dingyi.myluaapp.openapi.components;

import com.dingyi.myluaapp.openapi.extensions.AreaInstance;
import com.dingyi.myluaapp.openapi.extensions.ExtensionDescriptor;
import com.dingyi.myluaapp.openapi.extensions.ExtensionPointName;
import com.dingyi.myluaapp.openapi.extensions.ExtensionsArea;
import com.dingyi.myluaapp.openapi.extensions.PluginDescriptor;
import com.dingyi.myluaapp.openapi.extensions.PluginId;
import com.dingyi.myluaapp.util.messages.MessageBus;
import com.intellij.diagnostic.ActivityCategory;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.util.UserDataHolder;
import com.intellij.util.ReflectionUtil;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Provides access to components. Serves as a base interface for {@link com.intellij.openapi.application.Application}
 * and {@link com.intellij.openapi.project.Project}.
 *
 * @see com.intellij.openapi.application.Application
 * @see com.intellij.openapi.project.Project
 */
public interface ComponentManager extends UserDataHolder, Disposable, AreaInstance {

  /**
   * Gets the component by its interface class.
   *
   * @param interfaceClass the interface class of the component
   * @return component that matches interface class or null if there is no such component
   */
  <T> T getComponent(@NotNull Class<T> interfaceClass);

  /**
   * Checks whether there is a component with the specified interface class.
   *
   * @param interfaceClass interface class of component to be checked
   * @return {@code true} if there is a component with the specified interface class;
   * {@code false} otherwise
   */
  boolean hasComponent(@NotNull Class<?> interfaceClass);


  boolean isInjectionForExtensionSupported();

  /**
   * @see com.intellij.application.Topics#subscribe
   */
  @NotNull
  MessageBus getMessageBus();

  /**
   * @return true when this component is disposed (e.g. the "File|Close Project" invoked or the application is exited)
   * or is about to be disposed (e.g. the {@link com.intellij.openapi.project.impl.ProjectExImpl#dispose()} was called but not completed yet)
   * <br>
   * The result is only valid inside read action because the application/project/module can be disposed at any moment.
   * (see <a href="https://www.jetbrains.org/intellij/sdk/docs/basics/architectural_overview/general_threading_rules.html#readwrite-lock">more details on read actions</a>)
   */
  boolean isDisposed();

  /**
   * @deprecated Use {@link ExtensionPointName#getExtensionList(AreaInstance)}
   */
  @Deprecated
  default <T> T @NotNull [] getExtensions(@NotNull ExtensionPointName<T> extensionPointName) {
    return getExtensionArea().getExtensionPoint(extensionPointName).getExtensions();
  }

  /**
   * @return condition for this component being disposed.
   * see {@link com.intellij.openapi.application.Application#invokeLater(Runnable, Condition)} for the usage example.
   */
  @NotNull
  Condition<?> getDisposed();

  /**
   * @deprecated Use {@link #getServiceIfCreated(Class)} or {@link #getService(Class)}.
   */
  @Deprecated
  default <T> T getService(@NotNull Class<T> serviceClass, boolean createIfNeeded) {
    if (createIfNeeded) {
      return getService(serviceClass);
    }
    else {
      return getServiceIfCreated(serviceClass);
    }
  }

  <T> T getService(@NotNull Class<T> serviceClass);

  /**
   * Collects all services registered with client="..." attribute. Take a look at {@link com.intellij.openapi.client.ClientSession}
   */
  default @NotNull <T> List<T> getServices(@NotNull Class<T> serviceClass, boolean includeLocal) {
    T service = getService(serviceClass);
    return service != null ? Collections.singletonList(service) : Collections.emptyList();
  }

  default @Nullable <T> T getServiceIfCreated(@NotNull Class<T> serviceClass) {
    return getService(serviceClass);
  }

  @Override
  @NotNull
  ExtensionsArea getExtensionArea();


  default <T> T instantiateClass(@NotNull Class<T> aClass, @NotNull PluginId pluginId) {
    return ReflectionUtil.newInstance(aClass, false);
  }


  <T> T instantiateClassWithConstructorInjection(@NotNull Class<T> aClass, @NotNull Object key, @NotNull PluginId pluginId);


  default void logError(@NotNull Throwable error, @NotNull PluginId pluginId) {
    throw createError(error, pluginId);
  }


  @NotNull RuntimeException createError(@NotNull Throwable error, @NotNull PluginId pluginId);


  @NotNull RuntimeException createError(@NotNull @NonNls String message, @NotNull PluginId pluginId);

  @NotNull RuntimeException createError(@NotNull @NonNls String message,
                                        @Nullable Throwable error,
                                        @NotNull PluginId pluginId,
                                        @Nullable Map<String, String> attachments);


  <T> @NotNull Class<T> loadClass(@NotNull String className, @NotNull PluginDescriptor pluginDescriptor) throws ClassNotFoundException;


  @NotNull <T> T instantiateClass(@NotNull String className, @NotNull PluginDescriptor pluginDescriptor);


    default boolean isSuitableForOs(@NotNull ExtensionDescriptor.Os os) {
    return true;
  }
}