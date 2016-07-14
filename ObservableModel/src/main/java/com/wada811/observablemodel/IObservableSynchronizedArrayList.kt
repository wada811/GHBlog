package com.wada811.observablemodel

import com.wada811.notifypropertychanged.INotifyPropertyChanged
import rx.Subscription

interface IObservableSynchronizedArrayList<T> : MutableList<T>, INotifyPropertyChanged, INotifyCollectionChanged, Subscription {
    fun move(oldIndex: Int, newIndex: Int)
    fun <TResult> readLockAction(action: () -> TResult): TResult
    fun subscribe(onNext: (CollectionChangedEventArgs) -> Unit, onError: (Throwable) -> Unit = {}, onComplete: () -> Unit = {}): Subscription
}