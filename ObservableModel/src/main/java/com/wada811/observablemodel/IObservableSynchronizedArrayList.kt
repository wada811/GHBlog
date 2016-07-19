package com.wada811.observablemodel

import com.wada811.observablemodel.events.collection.INotifyCollectionChanged
import com.wada811.observablemodel.events.property.INotifyPropertyChanged
import rx.Subscription

interface IObservableSynchronizedArrayList<T> : MutableList<T>, INotifyPropertyChanged, INotifyCollectionChanged, Subscription {
    fun move(oldIndex: Int, newIndex: Int)
    fun <TResult> readLockAction(action: () -> TResult): TResult
}