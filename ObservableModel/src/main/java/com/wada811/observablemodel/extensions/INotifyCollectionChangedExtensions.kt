package com.wada811.observablemodel.extensions

import com.wada811.observablemodel.IObservableSynchronizedArrayList
import com.wada811.observablemodel.events.collection.CollectionChangedEventArgs
import rx.Observable
import rx.subjects.PublishSubject
import rx.subjects.SerializedSubject

fun <T> IObservableSynchronizedArrayList<T>.CollectionChangedAsObservable(): Observable<CollectionChangedEventArgs> {
    val subject = SerializedSubject<CollectionChangedEventArgs, CollectionChangedEventArgs>(PublishSubject.create<CollectionChangedEventArgs>())
    val handler = { sender: Any, e: CollectionChangedEventArgs -> subject.onNext(e) }
    return subject.doOnSubscribe { this.CollectionChanged += handler }.doOnUnsubscribe { this.CollectionChanged -= handler }
}
