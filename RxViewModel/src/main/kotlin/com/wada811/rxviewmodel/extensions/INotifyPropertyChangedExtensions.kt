package com.wada811.rxviewmodel.extensions

import com.wada811.observablemodel.events.property.INotifyPropertyChanged
import com.wada811.observablemodel.extensions.PropertyChangedAsObservable
import rx.Observable

fun <T, TProperty> T.ObserveProperty(propertyName: String, propertySelector: (T) -> TProperty): Observable<TProperty> where T : INotifyPropertyChanged {
    return this.PropertyChangedAsObservable()
        .filter { it.PropertyName == propertyName }
        .map { propertySelector(this) }
}
