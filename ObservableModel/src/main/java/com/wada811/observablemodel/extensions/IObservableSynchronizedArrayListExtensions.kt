package com.wada811.observablemodel.extensions

import com.wada811.observablemodel.IObservableSynchronizedArrayList
import com.wada811.observablemodel.ObservableSynchronizedArrayList
import com.wada811.observablemodel.events.collection.CollectionChangedEventAction
import com.wada811.observablemodel.events.collection.CollectionChangedEventArgs
import rx.Observable
import rx.Scheduler
import rx.Subscription
import rx.schedulers.Schedulers

fun <T, TResult> IObservableSynchronizedArrayList<T>.ToObservableSynchronizedArrayList(converter: (T) -> TResult): IObservableSynchronizedArrayList<TResult> {
    return this.readLockAction({
        val result = ObservableSynchronizedArrayList(this.map(converter))
        result.sourceSubscription = this.ObserveCollection().map(result, converter)
        return@readLockAction result
    })
}

fun <TSource> IObservableSynchronizedArrayList<TSource>.ObserveCollection(scheduler: Scheduler = Schedulers.immediate())
    : Observable<CollectionChangedEventArgs> = this.CollectionChangedAsObservable().onBackpressureBuffer().observeOn(scheduler)

fun <TSource, TResult> Observable<CollectionChangedEventArgs>.map(
    target: IObservableSynchronizedArrayList<TResult>,
    converter: (TSource) -> TResult): Subscription {
    return this.subscribe {
        when (it.action) {
            CollectionChangedEventAction.Add -> {
                val e = it as CollectionChangedEventArgs.Add
                target.add(e.index, @Suppress("UNCHECKED_CAST") converter(e.item as TSource))
            }
            CollectionChangedEventAction.Remove -> {
                val e = it as CollectionChangedEventArgs.Remove
                target.removeAt(e.index)
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
