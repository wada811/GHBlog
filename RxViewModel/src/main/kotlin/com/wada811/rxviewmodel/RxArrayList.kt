package com.wada811.rxviewmodel

import android.databinding.ObservableArrayList
import com.wada811.observablemodel.IObservableSynchronizedArrayList
import rx.Subscription

class RxArrayList<T>(source: Collection<T>) : ObservableArrayList<T>(), IObservableSynchronizedArrayList<T> {
    init {
        addAll(source)
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

    override fun move(oldIndex: Int, newIndex: Int) = add(newIndex, removeAt(oldIndex))
    override fun <TResult> readLockAction(action: () -> TResult): TResult = action()
}
