package com.dingyi.myluaapp.ide.plugins

import com.dingyi.myluaapp.plaform.util.plugins.DataLoader


interface PathResolver {

    fun resolvePath(
        dataLoader: DataLoader,
        relativePath: String,
        readInto: RawPluginDescriptor?
    ): RawPluginDescriptor?
}