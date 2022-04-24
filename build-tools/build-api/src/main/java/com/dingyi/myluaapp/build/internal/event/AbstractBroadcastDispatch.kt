package com.dingyi.myluaapp.build.internal.event

import com.dingyi.myluaapp.build.internal.dispatch.Dispatch
import com.dingyi.myluaapp.build.internal.dispatch.MethodInvocation


abstract class AbstractBroadcastDispatch<T>(
    protected val type: Class<T>
) :
    Dispatch<MethodInvocation> {


    private fun getErrorMessage(): String {
        val typeDescription =
            type.simpleName.replace("(\\p{Upper})".toRegex(), " $1").trim { it <= ' ' }
                .toLowerCase()
        return "Failed to notify $typeDescription."
    }

    protected fun dispatch(
        invocation: MethodInvocation,
        handler: Dispatch<MethodInvocation>
    ) {

        handler.dispatch(invocation)
    }

    protected fun dispatch(
        invocation: MethodInvocation,
        handlers: Iterator<Dispatch<MethodInvocation>>
    ) {
        handlers.forEachRemaining { handler ->
            handler.dispatch(invocation)
        }
    }
}
