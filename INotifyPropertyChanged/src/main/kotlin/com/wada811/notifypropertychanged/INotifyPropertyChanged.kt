package com.wada811.notifypropertychanged

import java.util.*
import kotlin.reflect.KProperty

open class EventArgs

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

class PropertyChangedEventArgs(val PropertyName: String = "") : EventArgs()

interface INotifyPropertyChanged {

    val PropertyChanged: EventHandler<Any, PropertyChangedEventArgs>
        get() = eventHandlers.getOrPut(hashCode(), { EventHandler() })

    fun NotifyPropertyChanged(propertyName: String = "") = PropertyChanged(this, PropertyChangedEventArgs(propertyName))

    companion object {
        private val eventHandlers: MutableMap<Int, EventHandler<Any, PropertyChangedEventArgs>> = Collections.synchronizedMap(mutableMapOf())
    }

}

class PropertyChangedDelegate<T>(initialValue: T) {
    private var value: T = initialValue

    operator fun getValue(thisRef: INotifyPropertyChanged, property: KProperty<*>): T = value

    operator fun setValue(thisRef: INotifyPropertyChanged, property: KProperty<*>, value: T) {
        if (this.value != value) {
            this.value = value
            thisRef.NotifyPropertyChanged(property.name)
        }
    }
}

