package com.wada811.rxviewmodel

import android.databinding.ObservableBoolean
import rx.Observable
import rx.Subscription
import rx.subjects.PublishSubject
import rx.subjects.Subject
import rx.subscriptions.CompositeSubscription

class RxCommand<in T>(action: (T) -> Unit, canExecuteSource: Observable<Boolean>? = null, canExecuteInitially: Boolean = true) : Subscription {

    private val trigger: Subject<T, T> = PublishSubject.create()
    var canExecute: ObservableBoolean
        get
        private set
    private var canExecuteSourceSubscription: Subscription?
    private val subscriptions = CompositeSubscription()

    init {
        canExecute = ObservableBoolean(canExecuteInitially)
        canExecuteSourceSubscription = canExecuteSource?.distinctUntilChanged()?.subscribe({ canExecute.set(it) })
        subscriptions.add(trigger.subscribe(action))
    }

    fun execute(parameter: T) = trigger.onNext(parameter)

    override fun isUnsubscribed(): Boolean = subscriptions.isUnsubscribed

    override fun unsubscribe() {
        if (isUnsubscribed) {
            trigger.onCompleted()
            subscriptions.unsubscribe()
            canExecute.set(false)
            canExecuteSourceSubscription?.unsubscribe()
        }
    }

    internal fun bind(subscription: Subscription) {
        subscriptions.add(subscription)
    }
}

fun <T> Observable<Boolean>.toRxCommand(action: (T) -> Unit, canExecuteInitially: Boolean = true) = RxCommand(action, this, canExecuteInitially)
fun <T> Observable<T>.bind(command: RxCommand<T>) {
    command.bind(this.filter { command.canExecute.get() }.subscribe { command.execute(it) })
}