// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.dingyi.myluaapp.openapi.extensions

import java.nio.file.Path
import java.util.Date

class DefaultPluginDescriptor : PluginDescriptor {
    private val myPluginId: PluginId
    private val myPluginClassLoader: ClassLoader?

    constructor(pluginId: String) {
        myPluginId = PluginId.getId(pluginId)
        myPluginClassLoader = null
    }

    constructor(pluginId: PluginId) {
        myPluginId = pluginId
        myPluginClassLoader = null
    }

    constructor(pluginId: PluginId, pluginClassLoader: ClassLoader?) {
        myPluginId = pluginId
        myPluginClassLoader = pluginClassLoader
    }

    override fun getPluginId(): PluginId {
        return myPluginId
    }

    override fun getPluginClassLoader(): ClassLoader? {
        return myPluginClassLoader
    }

    override fun getPluginPath(): Path? {
        return null
    }

    override fun getDescription(): String? {
        return null
    }

    override fun getChangeNotes(): String? {
        return null
    }

    override fun getName(): String? {
        return null
    }

    override fun getProductCode(): String? {
        return null
    }

    override fun getReleaseDate(): Date? {
        return null
    }

    override fun isLicenseOptional(): Boolean {
        return false
    }

    override fun getReleaseVersion(): Int {
        return 0
    }

    override fun getOptionalDependentPluginIds(): Array<PluginId> {
        return PluginId.EMPTY_ARRAY
    }

    override fun getVendor(): String? {
        return null
    }

    override fun getVersion(): String? {
        return null
    }

    override fun getResourceBundleBaseName(): String? {
        return null
    }

    override fun getCategory(): String? {
        return null
    }

    override fun getVendorEmail(): String? {
        return null
    }

    override fun getVendorUrl(): String? {
        return null
    }

    override fun getUrl(): String? {
        return null
    }

    override fun getSinceBuild(): String? {
        return null
    }

    override fun getUntilBuild(): String? {
        return null
    }

    override fun isEnabled(): Boolean {
        return false
    }

    override fun setEnabled(enabled: Boolean) {}
    override fun toString(): String {
        return "Default plugin descriptor for $myPluginId"
    }
}