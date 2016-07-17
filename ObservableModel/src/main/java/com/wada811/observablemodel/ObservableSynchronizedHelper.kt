package com.wada811.observablemodel

import com.wada811.observablemodel.extensions.CollectionChangedAsObservable
import rx.Scheduler
import rx.Subscription
import rx.schedulers.Schedulers

object ObservableSynchronizedHelper {
    fun <TSource, TResult> subscribeCollectionChanged(
        source: IObservableSynchronizedArrayList<TSource>,
        target: IObservableSynchronizedArrayList<TResult>,
        converter: (TSource) -> TResult,
        scheduler: Scheduler = Schedulers.immediate()): Subscription
        = source.CollectionChangedAsObservable()
        .observeOn(scheduler)
        .subscribe {
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