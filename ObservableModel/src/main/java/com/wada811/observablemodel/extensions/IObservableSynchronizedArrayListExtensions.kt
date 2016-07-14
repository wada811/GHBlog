package com.wada811.observablemodel.extensions

import com.wada811.observablemodel.CollectionChangedEventAction
import com.wada811.observablemodel.CollectionChangedEventArgs
import com.wada811.observablemodel.IObservableSynchronizedArrayList
import com.wada811.observablemodel.ObservableSynchronizedArrayList
import rx.Observable
import rx.subjects.PublishSubject

internal fun <T> IObservableSynchronizedArrayList<T>.CollectionChangedAsObservable(): Observable<CollectionChangedEventArgs> {
    val subject = PublishSubject.create<CollectionChangedEventArgs>()
    val handler = { sender: Any, e: CollectionChangedEventArgs -> subject.onNext(e) }
    return subject.doOnSubscribe { this.CollectionChanged += handler }.doOnUnsubscribe { this.CollectionChanged -= handler }
}

fun <T, TResult> IObservableSynchronizedArrayList<T>.ToObservableSynchronizedArrayList(converter: (T) -> TResult): IObservableSynchronizedArrayList<TResult> {
    return this.readLockAction({
        val result = ObservableSynchronizedArrayList(this.map(converter))
        result.sourceSubscription = this.subscribe({
            when (it.action) {
                CollectionChangedEventAction.Add -> {
                    val e = it as CollectionChangedEventArgs.Add
                    result.add(e.index, @Suppress("UNCHECKED_CAST") converter(e.item as T))
                }
                CollectionChangedEventAction.Remove -> {
                    val e = it as CollectionChangedEventArgs.Remove
                    result.removeAt(e.index)
                }
                CollectionChangedEventAction.Replace -> {
                    val e = it as CollectionChangedEventArgs.Replace
                    result[e.index] = @Suppress("UNCHECKED_CAST") converter(e.newItem as T)
                }
                CollectionChangedEventAction.Move -> {
                    val e = it as CollectionChangedEventArgs.Move
                    result.move(e.oldIndex, e.newIndex)
                }
                CollectionChangedEventAction.Reset -> {
                    result.clear()
                }
            }
        })
        return@readLockAction result
    })
}