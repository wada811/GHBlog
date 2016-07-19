package com.wada811.observablemodel.extensions

import com.wada811.observablemodel.events.property.INotifyPropertyChanged
import com.wada811.observablemodel.events.property.PropertyChangedEventArgs
import rx.Observable
import rx.subjects.PublishSubject

fun <T> T.PropertyChangedAsObservable(): Observable<PropertyChangedEventArgs> where T : INotifyPropertyChanged {
    val subject = PublishSubject.create<PropertyChangedEventArgs>()
    val handler = { sender: Any, e: PropertyChangedEventArgs -> subject.onNext(e) }
    return subject.doOnSubscribe { this.PropertyChanged += handler }.doOnUnsubscribe { this.PropertyChanged -= handler }
}
