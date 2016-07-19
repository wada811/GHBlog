package com.wada811.observablemodel.events.property

import java.util.*

interface INotifyPropertyChanged {

    val PropertyChanged: EventHandler<Any, PropertyChangedEventArgs>
        get() = eventHandlers.getOrPut(hashCode(), { EventHandler() })

    fun NotifyPropertyChanged(propertyName: String = "") = PropertyChanged(this, PropertyChangedEventArgs(propertyName))

    companion object {
        private val eventHandlers: MutableMap<Int, EventHandler<Any, PropertyChangedEventArgs>> = Collections.synchronizedMap(mutableMapOf())
    }

}
