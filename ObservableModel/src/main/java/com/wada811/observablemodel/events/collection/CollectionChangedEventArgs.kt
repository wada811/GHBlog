package com.wada811.observablemodel.events.collection

import com.wada811.observablemodel.events.EventArgs

sealed class CollectionChangedEventArgs(val action: CollectionChangedEventAction) : EventArgs() {
    class Add(val index: Int, val item: Any?) : CollectionChangedEventArgs(CollectionChangedEventAction.Add)
    class AddRange(val startIndex: Int, val items: Collection<Any?>) : CollectionChangedEventArgs(CollectionChangedEventAction.AddRange)
    class Remove(val index: Int, val item: Any?) : CollectionChangedEventArgs(CollectionChangedEventAction.Remove)
    class RemoveRange(val items: Collection<Any?>) : CollectionChangedEventArgs(CollectionChangedEventAction.RemoveRange)
    class Replace(val index: Int, val oldItem: Any?, val newItem: Any?) : CollectionChangedEventArgs(CollectionChangedEventAction.Replace)
    class Move(val oldIndex: Int, val newIndex: Int, val item: Any?) : CollectionChangedEventArgs(CollectionChangedEventAction.Move)
    class Reset() : CollectionChangedEventArgs(CollectionChangedEventAction.Reset)
}