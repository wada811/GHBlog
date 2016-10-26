package com.wada811.observablemodel.extensions

import com.wada811.observablemodel.IObservableSynchronizedArrayList
import com.wada811.observablemodel.ObservableSynchronizedArrayList
import com.wada811.observablemodel.events.collection.CollectionChangedEventAction
import com.wada811.observablemodel.events.collection.CollectionChangedEventArgs
import rx.Observable
import rx.Scheduler
import rx.Subscription
import rx.schedulers.Schedulers
import java.util.*
import kotlin.comparisons.compareBy

fun <TSource> IObservableSynchronizedArrayList<TSource>.ObserveCollection(scheduler: Scheduler = Schedulers.computation())
    : Observable<CollectionChangedEventArgs> = this.CollectionChangedAsObservable().observeOn(scheduler).onBackpressureBuffer()

fun <T, TResult> IObservableSynchronizedArrayList<T>.ToObservableSynchronizedArrayList(converter: (T) -> TResult): IObservableSynchronizedArrayList<TResult> {
    return this.readLockAction({
        val result = ObservableSynchronizedArrayList(this.map(converter))
        result.sourceSubscription = this.ObserveCollection().map(result, converter)
        return@readLockAction result
    })
}

fun <TSource, TResult> Observable<CollectionChangedEventArgs>.map(
    target: IObservableSynchronizedArrayList<TResult>,
    converter: (TSource) -> TResult): Subscription {
    return this.subscribe {
        when (it.action) {
            CollectionChangedEventAction.Add -> {
                val e = it as CollectionChangedEventArgs.Add
                target.add(e.index, @Suppress("UNCHECKED_CAST") converter(e.item as TSource))
            }
            CollectionChangedEventAction.AddRange -> {
                val e = it as CollectionChangedEventArgs.AddRange
                target.addAll(e.startIndex, e.items.map { @Suppress("UNCHECKED_CAST") converter(it as TSource) })
            }
            CollectionChangedEventAction.Remove -> {
                val e = it as CollectionChangedEventArgs.Remove
                target.removeAt(e.index)
            }
            CollectionChangedEventAction.RemoveRange -> {
                val e = it as CollectionChangedEventArgs.RemoveRange
                target.removeAll(e.items.map { @Suppress("UNCHECKED_CAST") converter(it as TSource) })
            }
            CollectionChangedEventAction.Replace -> {
                val e = it as CollectionChangedEventArgs.Replace
                target[e.index] = @Suppress("UNCHECKED_CAST") converter(e.newItem as TSource)
            }
            CollectionChangedEventAction.Move -> {
                val e = it as CollectionChangedEventArgs.Move
                target.move(e.oldIndex, e.newIndex)
            }
            CollectionChangedEventAction.Reset -> {
                target.clear()
            }
        }
    }
}

fun <T> IObservableSynchronizedArrayList<T>.ToSortedObservableSynchronizedArrayList(vararg sortFunction: (T) -> Comparable<*>?): IObservableSynchronizedArrayList<T> {
    return this.readLockAction({
        val result = ObservableSynchronizedArrayList(this.sortedWith(compareBy(*sortFunction)))
        result.sourceSubscription = this.ObserveCollection().sort(result, compareBy(*sortFunction))
        return@readLockAction result
    })
}

@Suppress("UNCHECKED_CAST")
fun <T> Observable<CollectionChangedEventArgs>.sort(target: IObservableSynchronizedArrayList<T>, comparator: Comparator<T>): Subscription {
    return this.subscribe({
        val findSortedIndex: (Int, T) -> Int = { index: Int, item: T ->
            val result = Collections.binarySearch(target, item, comparator)
            val newSortedIndex = if (result >= 0) result else result.inv()
            newSortedIndex
        }
        when (it.action) {
            CollectionChangedEventAction.Add -> {
                val e = it as CollectionChangedEventArgs.Add
                val item = e.item as T
                target.add(findSortedIndex(e.index, item), item)
            }
            CollectionChangedEventAction.AddRange -> {
                val e = it as CollectionChangedEventArgs.AddRange
                e.items.map { it as T }.forEachIndexed { i, item ->
                    System.out.println("e.startIndex: ${e.startIndex}")
                    System.out.println("i: $i")
                    System.out.println("findSortedIndex(e.startIndex + i, item): ${findSortedIndex(e.startIndex + i, item)}")
                    target.add(findSortedIndex(e.startIndex + i, item), item)
                }
            }
            CollectionChangedEventAction.Remove -> {
                val e = it as CollectionChangedEventArgs.Remove
                target.remove(e.item)
            }
            CollectionChangedEventAction.RemoveRange -> {
                val e = it as CollectionChangedEventArgs.RemoveRange
                target.removeAll(e.items)
            }
            CollectionChangedEventAction.Replace -> {
                val e = it as CollectionChangedEventArgs.Replace
                val item = e.newItem as T
                target.remove(e.oldItem)
                target.add(findSortedIndex(e.index, item), item)
            }
            CollectionChangedEventAction.Move -> {
                // do-nothing
            }
            CollectionChangedEventAction.Reset -> {
                target.clear()
            }
        }
    }, {
        System.out.println("onError: $it")
        it.printStackTrace()
    })
}
