package com.dingyi.myluaapp.ide.plugins

import com.dingyi.myluaapp.plaform.util.plugins.DataLoader
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.io.Reader

fun readModuleDescriptor(
    input: InputStream?,
    readContext: DescriptorListLoadingContext,
    pathResolver: PathResolver,
    dataLoader: DataLoader,
    readInto: RawPluginDescriptor?,
    locationSource: String?
): RawPluginDescriptor {


    val descriptor = readInto ?: RawPluginDescriptor()

    val mainClassName = input?.readBytes()?.decodeToString()


}


fun readModuleDescriptor(
    reader: ByteArray,
    readContext: DescriptorListLoadingContext,
    pathResolver: PathResolver,
    dataLoader: DataLoader,
    readInto: RawPluginDescriptor?,
    locationSource: String?
): RawPluginDescriptor = readModuleDescriptor(
    ByteArrayInputStream(reader), readContext, pathResolver, dataLoader, readInto, locationSource
)