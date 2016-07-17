package com.wada811.observablemodel

import com.googlecode.concurentlocks.ReentrantReadWriteUpdateLock
import com.wada811.observablemodel.extensions.read
import com.wada811.observablemodel.extensions.writeAndRead
import com.wada811.observablemodel.extensions.writeWithUpdateAndRead
import rx.Subscription
import java.util.*

class ObservableSynchronizedArrayList<T>(source: Collection<T>) : IObservableSynchronizedArrayList<T> {
    internal class PropertyChangedEventArg {
        companion object {
            val Count: String = "Count"
            val Item: String = "Item[]"
        }
    }

    private lateinit var list: ArrayList<T>
    private var lock = ReentrantReadWriteUpdateLock()

    init {
        list = ArrayList(source)
    }

    constructor() : this(listOf())

    internal var sourceSubscription: Subscription? = null
    override fun isUnsubscribed(): Boolean = sourceSubscription == null || sourceSubscription!!.isUnsubscribed
    override fun unsubscribe() {
        if (sourceSubscription != null && !isUnsubscribed) {
            sourceSubscription!!.unsubscribe()
        }
        sourceSubscription = null
    }

    override val size: Int
        get() = lock.read { list.size }

    override fun add(element: T): Boolean {
        var result = false
        lock.writeAndRead({
            result = list.add(element)
        }, {
            if (result) {
                NotifyPropertyChanged(PropertyChangedEventArg.Count)
                NotifyPropertyChanged(PropertyChangedEventArg.Item)
                NotifyAdd(list.lastIndex, element)
            }
        })
        return result
    }

    override fun add(index: Int, element: T) {
        lock.writeAndRead({
            list.add(index, element)
        }, {
            NotifyPropertyChanged(PropertyChangedEventArg.Count)
            NotifyPropertyChanged(PropertyChangedEventArg.Item)
            NotifyAdd(index, element)
        })
    }

    override fun addAll(elements: Collection<T>): Boolean {
        var result = false
        lock.writeWithUpdateAndRead({
            list.size
        }, {
            size ->
            result = list.addAll(elements)
        }, {
            size ->
            if (result) {
                NotifyPropertyChanged(PropertyChangedEventArg.Count)
                NotifyPropertyChanged(PropertyChangedEventArg.Item)
                elements.forEachIndexed { index, element -> NotifyAdd(size + index, element) }
            }
        })
        return result
    }

    override fun addAll(index: Int, elements: Collection<T>): Boolean {
        var result = false
        lock.writeAndRead({
            result = list.addAll(index, elements)
        }, {
            if (result) {
                NotifyPropertyChanged(PropertyChangedEventArg.Count)
                NotifyPropertyChanged(PropertyChangedEventArg.Item)
                elements.forEachIndexed { i, element -> NotifyAdd(index + i, element) }
            }
        })
        return result
    }

    override fun clear() {
        lock.writeWithUpdateAndRead({
            list.size
        }, {
            size ->
            if (size != 0) {
                list.clear()
            }
        }, {
            size ->
            if (size != 0) {
                NotifyPropertyChanged(PropertyChangedEventArg.Count)
                NotifyPropertyChanged(PropertyChangedEventArg.Item)
                NotifyReset()
            }
        })
    }

    override fun contains(element: T): Boolean = lock.read { list.contains(element) }

    override fun containsAll(elements: Collection<T>): Boolean = lock.read { list.containsAll(elements) }

    override fun get(index: Int): T = lock.read { list[index] }

    override fun indexOf(element: T): Int = lock.read { list.indexOf(element) }

    override fun isEmpty(): Boolean = lock.read { list.isEmpty() }

    override fun iterator(): MutableIterator<T> = list.iterator()

    override fun lastIndexOf(element: T): Int = lock.read { list.lastIndexOf(element) }

    override fun listIterator(): MutableListIterator<T> = list.listIterator()

    override fun listIterator(index: Int): MutableListIterator<T> = list.listIterator(index)

    override fun remove(element: T): Boolean {
        var result = false
        lock.writeWithUpdateAndRead({
            list.indexOf(element)
        }, {
            index ->
            result = list.remove(element)
        }, {
            index ->
            if (result) {
                NotifyPropertyChanged(PropertyChangedEventArg.Count)
                NotifyPropertyChanged(PropertyChangedEventArg.Item)
                NotifyRemove(index, element)
            }
        })
        return result
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        var result = false
        lock.writeWithUpdateAndRead({
            elements.map { element -> Pair(list.indexOf(element), element) }
        }, { pairs ->
            result = list.removeAll(elements)
        }, { pairs ->
            if (result) {
                NotifyPropertyChanged(PropertyChangedEventArg.Count)
                NotifyPropertyChanged(PropertyChangedEventArg.Item)
                pairs.forEach { pair -> NotifyRemove(pair.first, pair.second) }
            }
        })
        return result
    }

    override fun removeAt(index: Int): T {
        return lock.writeWithUpdateAndRead({
            list[index]
        }, {
            item ->
            list.removeAt(index)
        }, {
            item ->
            NotifyPropertyChanged(PropertyChangedEventArg.Count)
            NotifyPropertyChanged(PropertyChangedEventArg.Item)
            NotifyRemove(index, item)
        })
    }

    override fun retainAll(elements: Collection<T>): Boolean {
        var result = false
        lock.writeWithUpdateAndRead({
            list.filter { !elements.contains(it) }.mapIndexed { index, item -> Pair(index, item) }
        }, {
            pairs ->
            result = list.retainAll(elements)
        }, {
            pairs ->
            if (result) {
                NotifyPropertyChanged(PropertyChangedEventArg.Count)
                NotifyPropertyChanged(PropertyChangedEventArg.Item)
                pairs.forEach { pair -> NotifyRemove(pair.first, pair.second) }
            }
        })
        return true
    }

    override fun set(index: Int, element: T): T {
        return lock.writeWithUpdateAndRead({
            list[index]
        }, {
            oldItem ->
            list[index] = element
        }, {
            oldItem ->
            NotifyPropertyChanged(PropertyChangedEventArg.Item)
            NotifyReplace(index, oldItem, element)
        })
    }

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<T> = lock.read { list.subList(fromIndex, toIndex) }

    override fun move(oldIndex: Int, newIndex: Int) {
        lock.writeWithUpdateAndRead({
            list[oldIndex]
        }, {
            item ->
            list.removeAt(oldIndex)
            list.add(newIndex, item)
        }, {
            item ->
            NotifyPropertyChanged(PropertyChangedEventArg.Item)
            NotifyMove(oldIndex, newIndex, item)
        })
    }

    override fun <TResult> readLockAction(action: () -> TResult): TResult {
        return lock.read(action)
    }
}