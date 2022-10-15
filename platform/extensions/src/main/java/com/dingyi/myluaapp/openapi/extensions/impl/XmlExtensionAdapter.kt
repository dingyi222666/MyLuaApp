// Copyright 2000-2022 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.dingyi.myluaapp.openapi.extensions.impl

import com.dingyi.myluaapp.openapi.extensions.ExtensionDescriptor
import com.dingyi.myluaapp.openapi.extensions.ExtensionNotApplicableException
import com.dingyi.myluaapp.openapi.extensions.LoadingOrder
import com.dingyi.myluaapp.openapi.extensions.PluginAware
import com.dingyi.myluaapp.openapi.extensions.PluginDescriptor
import com.dingyi.myluaapp.openapi.service.ServiceRegistry
import com.intellij.openapi.progress.ProcessCanceledException
import java.util.Arrays

internal open class XmlExtensionAdapter(implementationClassName: String,
                                        pluginDescriptor: PluginDescriptor,
                                        orderId: String?,
                                        order: LoadingOrder,
                                       /* private var extensionElement: XmlElement?,*/
                                        implementationClassResolver: ImplementationClassResolver
) : ExtensionComponentAdapter(
  implementationClassName, pluginDescriptor, orderId, order, implementationClassResolver) {
  companion object {
    private val NOT_APPLICABLE = Any()
  }

  @Volatile
  private var extensionInstance: Any? = null
  private var initializing = false

  override val isInstanceCreated: Boolean
    get() = extensionInstance != null

  override fun <T : Any> createInstance(componentManager: ServiceRegistry): T? {
    @Suppress("UNCHECKED_CAST")
    return (extensionInstance as T?)?.takeIf { it !== NOT_APPLICABLE } ?: doCreateInstance(componentManager)
  }

  @Synchronized
  private fun <T : Any> doCreateInstance(componentManager: ServiceRegistry): T? {
    @Suppress("UNCHECKED_CAST")
    var instance = extensionInstance as T?
    if (instance != null) {
      return instance.takeIf { it !== NOT_APPLICABLE }
    }

    if (initializing) {
      /*throw componentManager.createError*/ error("Cyclic extension initialization: $this"/*, pluginDescriptor.pluginId*/)
    }

    try {
      initializing = true
      @Suppress("UNCHECKED_CAST")
      val aClass = implementationClassResolver.resolveImplementationClass(componentManager, this) as Class<T>
      instance = instantiateClass(aClass, componentManager)
      if (instance is PluginAware) {
        instance.setPluginDescriptor(pluginDescriptor)
      }
    /*  val element = extensionElement
      if (element != null) {
        XmlSerializer.getBeanBinding(instance::class.java).deserializeInto(instance, element)
        extensionElement = null
      }*/
      extensionInstance = instance
    }
    catch (e: ExtensionNotApplicableException) {
      extensionInstance = NOT_APPLICABLE
     /* extensionElement = null*/
      return null
    }
    catch (e: ProcessCanceledException) {
      throw e
    }
    catch (e: Throwable) {
      /*throw componentManager.createError*/ throw RuntimeException("Cannot create extension (class=$assignableToClassName)", e/*, pluginDescriptor.pluginId, null*/)
    }
    finally {
      initializing = false
    }
    return instance
  }

  protected open fun <T> instantiateClass(aClass: Class<T>, componentManager: ServiceRegistry): T {
    return aClass.newInstance() /*componentManager.instantiateClass(aClass, pluginDescriptor.pluginId)*/
  }

  internal class SimpleConstructorInjectionAdapter(implementationClassName: String,
                                                   pluginDescriptor: PluginDescriptor,
                                                   descriptor: ExtensionDescriptor,
                                                   implementationClassResolver: ImplementationClassResolver
  ) : XmlExtensionAdapter(
    implementationClassName, pluginDescriptor, descriptor.orderId, descriptor.order,/* descriptor.element,*/ implementationClassResolver) {
    override fun <T> instantiateClass(aClass: Class<T>, componentManager: ServiceRegistry): T {
      if (aClass.name != "org.jetbrains.kotlin.asJava.finder.JavaElementFinder") {
        try {
          return super.instantiateClass(aClass, componentManager)
        }
        catch (e: ProcessCanceledException) {
          throw e
        }
        catch (e: ExtensionNotApplicableException) {
          throw e
        }
        catch (e: RuntimeException) {
          val cause = e.cause
          if (!(cause is NoSuchMethodException || cause is IllegalArgumentException)) {
            throw e
          }
          ExtensionPointImpl.LOG.error(
            "Cannot create extension without pico container (class=" + aClass.name + ", constructors=" +
            Arrays.toString(aClass.declaredConstructors) + ")," +
            " please remove extra constructor parameters", e)
        }
      }
      return componentManager.apply {
          asRegistration().add(aClass)
      }.get(aClass).apply {
          /*componentManager.asRegistration()
              .remove(aClass)*/
      }
    /* componentManager.instantiateClassWithConstructorInjection(aClass, aClass, pluginDescriptor.pluginId)*/
    }
  }
}