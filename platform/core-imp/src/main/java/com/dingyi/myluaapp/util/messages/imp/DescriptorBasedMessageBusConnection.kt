package com.dingyi.myluaapp.util.messages.imp

import com.dingyi.myluaapp.openapi.extensions.PluginId
import com.dingyi.myluaapp.util.messages.Topic
import java.util.function.Predicate


internal class DescriptorBasedMessageBusConnection<L>(
    val pluginId: PluginId,
    val topic: Topic<L>,
    val handlers: List<L>
) : MessageBusImpl.MessageHandlerHolder {
    override fun <L1> collectHandlers(topic: Topic<L1>, result: MutableList<L1>) {
        if (this.topic === topic) {
            result.addAll((handlers as Collection<L1>))
        }
    }

    override fun disconnectIfNeeded(predicate: Predicate<Class<*>>) {}

    // never empty
    override val isDisposed: Boolean
        get() =// never empty
            false

    override fun toString(): String {
        return "DescriptorBasedMessageBusConnection(" +
                "handlers=" + handlers +
                ')'
    }

    companion object {

        fun <L : Any> computeNewHandlers(
            handlers: List<L>,
            excludeClassNames: Set<String?>
        ): List<L>? {
            var newHandlers: MutableList<L>? = null
            var i = 0
            val size = handlers.size
            while (i < size) {
                val handler = handlers[i]
                if (excludeClassNames.contains(handler::class.java.getName())) {
                    if (newHandlers == null) {
                        newHandlers = if (i == 0) ArrayList() else ArrayList(handlers.subList(0, i))
                    }
                } else newHandlers?.add(handler)
                i++
            }
            return newHandlers
        }
    }
}
