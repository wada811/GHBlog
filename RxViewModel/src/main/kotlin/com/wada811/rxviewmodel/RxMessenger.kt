package com.wada811.rxviewmodel

import rx.subjects.PublishSubject
import rx.subjects.SerializedSubject
import rx.subjects.Subject

object  RxMessenger {
    val messenger: Subject<Any, Any> = SerializedSubject(PublishSubject.create())
    fun send(event: Any) = messenger.onNext(event)
    fun toObservable() = messenger
    fun hasObservers() : Boolean = messenger.hasObservers()
}