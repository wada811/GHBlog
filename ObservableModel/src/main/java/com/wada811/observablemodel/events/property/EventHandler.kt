package com.wada811.observablemodel.events.property

import com.wada811.observablemodel.events.EventArgs
import java.util.*

class EventHandler<TObj, TArg> where TArg : EventArgs {
    private val handlers = Collections.synchronizedList(arrayListOf<(TObj, TArg) -> Unit>())

    operator fun plusAssign(handler: (TObj, TArg) -> Unit) {
        handlers.add(handler)
    }

    operator fun minusAssign(handler: (TObj, TArg) -> Unit) {
        handlers.remove(handler)
    }

    operator fun invoke(sender: TObj, e: TArg) {
        for (handler in handlers) handler(sender, e)
    }
}