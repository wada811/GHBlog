package com.wada811.observablemodel.events.collection

import java.util.*

interface INotifyCollectionChanged {
    companion object {
        private val eventHandlers: MutableMap<Int, SynchronizedEventHandler<Any, CollectionChangedEventArgs>> = Collections.synchronizedMap(mutableMapOf())
    }

    val CollectionChanged: SynchronizedEventHandler<Any, CollectionChangedEventArgs>
        get() = eventHandlers.getOrPut(hashCode(), { SynchronizedEventHandler() })

    fun NotifyAdd(index: Int, item: Any?) = CollectionChanged(this, CollectionChangedEventArgs.Add(index, item))

    fun NotifyRemove(index: Int, item: Any?) = CollectionChanged(this, CollectionChangedEventArgs.Remove(index, item))

    fun NotifyReplace(index: Int, oldItem: Any?, newItem: Any?) = CollectionChanged(this, CollectionChangedEventArgs.Replace(index, oldItem, newItem))

    fun NotifyMove(oldIndex: Int, newIndex: Int, item: Any?) = CollectionChanged(this, CollectionChangedEventArgs.Move(oldIndex, newIndex, item))

    fun NotifyReset() = CollectionChanged(this, CollectionChangedEventArgs.Reset())
}

