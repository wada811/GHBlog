package com.wada811.rxviewmodel.extensions

import com.wada811.observablemodel.IObservableSynchronizedArrayList
import com.wada811.observablemodel.extensions.ObserveCollection
import com.wada811.observablemodel.extensions.map
import com.wada811.rxviewmodel.RxArrayList
import com.wada811.rxviewmodel.RxProperty
import com.wada811.rxviewmodel.UIThreadScheduler
import rx.Observable
import java.util.*

fun <T> Observable<T>.toRxProperty(): RxProperty<T> = RxProperty(this)
fun <T> Observable<T>.toRxProperty(initialValue: T?) = RxProperty(this, initialValue)
fun <T> Observable<T>.toRxProperty(mode: EnumSet<RxProperty.Mode>) = RxProperty(this, mode)
fun <T> Observable<T>.toRxProperty(initialValue: T?, mode: EnumSet<RxProperty.Mode>) = RxProperty(this, initialValue, mode)
fun <T, TResult> IObservableSynchronizedArrayList<T>.ToRxArrayList(converter: (T) -> TResult): RxArrayList<TResult> {
    return this.readLockAction({
        val result = RxArrayList(this.map(converter))
        result.sourceSubscription = this.ObserveCollection(UIThreadScheduler.DefaultScheduler).map(result, converter)
        return@readLockAction result
    })
}
