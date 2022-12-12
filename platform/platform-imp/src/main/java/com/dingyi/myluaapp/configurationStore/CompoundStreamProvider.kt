package com.dingyi.myluaapp.configurationStore



import com.intellij.util.containers.ContainerUtil
import java.io.InputStream

class CompoundStreamProvider : StreamProvider {
    val providers = ContainerUtil.createConcurrentList<StreamProvider>()

    override val enabled: Boolean
        get() = providers.any { it.enabled }

    override val isExclusive: Boolean
        get() = providers.any { it.isExclusive }

    val isExclusivelyEnabled: Boolean
        get() = enabled && isExclusive

    override fun isApplicable(fileSpec: String) = providers.any { it.isApplicable(fileSpec) }

    override fun read(fileSpec: String, consumer: (InputStream?) -> Unit) = providers.any { it.read(fileSpec, consumer) }

    override fun processChildren(path: String,
                                 filter: Function1<String, Boolean>,
                                 processor: Function3<String, InputStream, Boolean, Boolean>): Boolean {
        return providers.any { it.processChildren(path, filter, processor) }
    }

    override fun write(fileSpec: String, content: ByteArray, size: Int) {
        providers.forEach {
            if (it.isApplicable(fileSpec)) {
                it.write(fileSpec, content, size)
            }
        }
    }

    override fun delete(fileSpec: String) = providers.any { it.delete(fileSpec) }
}