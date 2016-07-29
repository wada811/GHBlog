package com.wada811.ghblog.view.activity.extensions

import rx.Subscription
import rx.subscriptions.CompositeSubscription

fun <T> T.addTo(compositeSubscription: CompositeSubscription): T where T : Subscription {
    compositeSubscription.add(this)
    return this
}
