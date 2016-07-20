package com.wada811.rxviewmodel

import rx.Observable
import rx.functions.Action
import rx.subjects.PublishSubject
import rx.subjects.SerializedSubject
import rx.subjects.Subject

object RxMessenger {
    val messenger: Subject<Action, Action> = SerializedSubject(PublishSubject.create())
    fun send(action: Action) = messenger.onNext(action)
    fun toObservable() = messenger
    fun <T> observe(clazz: Class<T>): Observable<T> = messenger.ofType(clazz).observeOn(UIThreadScheduler.DefaultScheduler)
    fun hasObservers(): Boolean = messenger.hasObservers()
}