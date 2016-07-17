package com.wada811.observablemodel.extensions

import com.wada811.observablemodel.CollectionChangedEventAction
import com.wada811.observablemodel.CollectionChangedEventArgs
import com.wada811.observablemodel.IObservableSynchronizedArrayList
import com.wada811.observablemodel.ObservableSynchronizedArrayList
import rx.Observable
import rx.Scheduler
import rx.Subscription
import rx.schedulers.Schedulers
import rx.subjects.PublishSubject
import rx.subjects.SerializedSubject

fun <T> IObservableSynchronizedArrayList<T>.CollectionChangedAsObservable(): Observable<CollectionChangedEventArgs> {
    val subject = SerializedSubject<CollectionChangedEventArgs, CollectionChangedEventArgs>(PublishSubject.create<CollectionChangedEventArgs>())
    val handler = { sender: Any, e: CollectionChangedEventArgs -> subject.onNext(e) }
    return subject.doOnSubscribe { this.CollectionChanged += handler }.doOnUnsubscribe { this.CollectionChanged -= handler }
}

fun <T, TResult> IObservableSynchronizedArrayList<T>.ToObservableSynchronizedArrayList(converter: (T) -> TResult): IObservableSynchronizedArrayList<TResult> {
    return this.readLockAction({
        val result = ObservableSynchronizedArrayList(this.map(converter))
        result.sourceSubscription = this.subscribeCollectionChanged(result, converter)
        return@readLockAction result
    })
}

fun <TSource, TResult> IObservableSynchronizedArrayList<TSource>.subscribeCollectionChanged(
    target: IObservableSynchronizedArrayList<TResult>,
    converter: (TSource) -> TResult,
    scheduler: Scheduler = Schedulers.immediate()
): Subscription {
    return this.CollectionChangedAsObservable()
        .onBackpressureBuffer()
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