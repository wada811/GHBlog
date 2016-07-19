package com.wada811.observablemodel.events.property

import kotlin.reflect.KProperty

class PropertyChangedDelegate<T>(initialValue: T) {
    private var value: T = initialValue

    operator fun getValue(thisRef: INotifyPropertyChanged, property: KProperty<*>): T = value

    operator fun setValue(thisRef: INotifyPropertyChanged, property: KProperty<*>, value: T) {
        if (this.value != value) {
            this.value = value
            thisRef.NotifyPropertyChanged(property.name)
        }
    }
}
