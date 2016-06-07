package com.wada811.rxviewmodel

import android.databinding.ObservableBoolean
import rx.Observable
import rx.Subscription
import rx.subjects.PublishSubject

class RxCommand<T>(source: Observable<Boolean>? = null, initialValue: Boolean = true, var command: T? = null) : Subscription {

    var canExecute: ObservableBoolean
    var sourceSubscription: Subscription?
    var errorNotifier = PublishSubject.create<Throwable>()

    constructor(command: T) : this(null, command) {
    }

    constructor(source: Observable<Boolean>?, command: T) : this(source, true, command) {
    }

    init {
        canExecute = ObservableBoolean(initialValue)
        if (source == null) {
            sourceSubscription = null
        } else {
            sourceSubscription = source.distinctUntilChanged()
                    .subscribe({ canExecute.set(it) }, { errorNotifier.onNext(it) }, { errorNotifier.onCompleted() })
        }
    }

    fun observeErrors(): Observable<Throwable> = errorNotifier.asObservable()

    override fun isUnsubscribed(): Boolean = sourceSubscription == null || sourceSubscription!!.isUnsubscribed

    override fun unsubscribe() {
        if (sourceSubscription != null && !isUnsubscribed) {
            sourceSubscription!!.unsubscribe()
        }
        sourceSubscription = null
    }
}
