package com.wada811.rxviewmodel

import rx.Subscription
import rx.subscriptions.CompositeSubscription

open class RxViewModel : Subscription {
    private val subscriptions = CompositeSubscription()
    override fun isUnsubscribed(): Boolean = subscriptions.isUnsubscribed
    override fun unsubscribe() {
        if (!isUnsubscribed) {
            subscriptions.unsubscribe()
        }
    }

    protected fun Subscription.asManaged(): Subscription {
        subscriptions.add(this)
        return this
    }

    protected fun <T> RxProperty<T>.asManaged(): RxProperty<T> {
        subscriptions.add(this)
        return this
    }

    protected fun <T> RxArrayList<T>.asManaged(): RxArrayList<T> {
        subscriptions.add(this)
        return this
    }

    protected fun <T> RxCommand<T>.asManaged(): RxCommand<T> {
        subscriptions.add(this)
        return this
    }
}