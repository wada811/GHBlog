package com.wada811.rxviewmodel

import android.databinding.ObservableField
import rx.Observable
import rx.Subscription
import rx.subjects.BehaviorSubject
import rx.subjects.PublishSubject
import rx.subjects.SerializedSubject
import java.util.*

class RxProperty<T>(source: Observable<T>?, initialValue: T? = null, mode: EnumSet<RxProperty.Mode> = RxProperty.DEFAULT_MODE) : ObservableField<T>(initialValue), Subscription {

    private companion object {
        private val DEFAULT_MODE = EnumSet.of(Mode.DISTINCT_UNTIL_CHANGED, Mode.RAISE_LATEST_VALUE_ON_SUBSCRIBE)
    }

    var value: T? = initialValue
        get() = super.get()
        set(value) {
            if (field != value) {
                field = value
                super.set(value)
                subject.onNext(value)
            } else if (!isDistinctUntilChanged) {
                subject.onNext(value)
            }
        }

    @Deprecated("For Data-Binding", ReplaceWith("value"), DeprecationLevel.HIDDEN)
    override fun get(): T? = value

    @Deprecated("For Data-Binding", ReplaceWith("this.value = value"), DeprecationLevel.HIDDEN)
    override fun set(value: T) {
        this.value = value
    }

    private var subject: SerializedSubject<T, T>
    private var sourceSubscription: Subscription?
    private var isDistinctUntilChanged: Boolean

    enum class Mode {
        /**
         * All mode is off.
         */
        NONE,
        /**
         * If next value is same as current, not set and not notify.
         */
        DISTINCT_UNTIL_CHANGED,
        /**
         * Sends notification on the instance created and subscribed.
         */
        RAISE_LATEST_VALUE_ON_SUBSCRIBE;
    }

    constructor() : this(null)

    constructor(initialValue: T?) : this(initialValue, RxProperty.DEFAULT_MODE)

    constructor(mode: EnumSet<Mode>) : this(null, mode)

    constructor(initialValue: T?, mode: EnumSet<Mode>) : this(null, initialValue, mode)

    constructor(source: Observable<T>) : this(source, RxProperty.DEFAULT_MODE)

    constructor(source: Observable<T>, initialValue: T?) : this(source, initialValue, RxProperty.DEFAULT_MODE)

    constructor(source: Observable<T>, mode: EnumSet<Mode>) : this(source, null, mode)

    init {
        isDistinctUntilChanged = !mode.contains(Mode.NONE) && mode.contains(Mode.DISTINCT_UNTIL_CHANGED)
        val isRaiseLatestValueOnSubscribe = !mode.contains(Mode.NONE) && mode.contains(Mode.RAISE_LATEST_VALUE_ON_SUBSCRIBE)
        if (isRaiseLatestValueOnSubscribe) {
            subject = SerializedSubject<T, T>(BehaviorSubject.create(initialValue))
        } else {
            subject = SerializedSubject<T, T>(PublishSubject.create())
        }
        if (source == null) {
            sourceSubscription = null
        } else {
            sourceSubscription = source.subscribe({ value = it }, { subject.onError(it) }, { subject.onCompleted() })
        }
    }

    fun asObservable(): Observable<T> = subject.asObservable()
    override fun isUnsubscribed(): Boolean = sourceSubscription == null || sourceSubscription!!.isUnsubscribed
    override fun unsubscribe() {
        if (sourceSubscription != null && !isUnsubscribed) {
            sourceSubscription!!.unsubscribe()
        }
        sourceSubscription = null
    }

}
