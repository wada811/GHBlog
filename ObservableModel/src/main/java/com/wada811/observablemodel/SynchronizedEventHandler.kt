package com.wada811.observablemodel

import com.wada811.notifypropertychanged.EventArgs

class SynchronizedEventHandler<TObj, TArg> where TArg : EventArgs {
    private val handlers = ObservableSynchronizedArrayList<(TObj, TArg) -> Unit>()

    operator fun plusAssign(handler: (TObj, TArg) -> Unit) {
        handlers.add(handler)
    }

    operator fun minusAssign(handler: (TObj, TArg) -> Unit) {
        handlers.remove(handler)
    }

    operator fun invoke(sender: TObj, e: TArg) {
        handlers.readLockAction({ for (handler in handlers) handler(sender, e) })
    }
}
