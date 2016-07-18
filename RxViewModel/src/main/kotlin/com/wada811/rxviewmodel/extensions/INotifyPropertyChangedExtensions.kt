package com.wada811.rxviewmodel.extensions

import com.wada811.notifypropertychanged.INotifyPropertyChanged
import com.wada811.notifypropertychanged.PropertyChangedEventArgs
import rx.Observable
import rx.subjects.PublishSubject

fun <T> T.PropertyChangedAsObservable(): Observable<PropertyChangedEventArgs> where T : INotifyPropertyChanged {
    val subject = PublishSubject.create<PropertyChangedEventArgs>()
    val handler = { sender: Any, e: PropertyChangedEventArgs -> subject.onNext(e) }
    return subject.doOnSubscribe { this.PropertyChanged += handler }.doOnUnsubscribe { this.PropertyChanged -= handler }
}

fun <T, TProperty> T.ObserveProperty(propertyName: String, propertySelector: (T) -> TProperty): Observable<TProperty> where T : INotifyPropertyChanged {
    return this.PropertyChangedAsObservable()
        .filter { it.PropertyName == propertyName }
        .map { propertySelector(this) }
}
