package com.wada811.rxviewmodel

import com.wada811.observablemodel.events.property.INotifyPropertyChanged
import com.wada811.observablemodel.events.property.PropertyChangedDelegate
import com.wada811.observablemodel.events.property.PropertyChangedEventArgs
import com.wada811.rxviewmodel.extensions.ObserveProperty
import com.wada811.rxviewmodel.extensions.PropertyChangedAsObservable
import com.wada811.rxviewmodel.extensions.toRxProperty
import org.junit.Assert
import org.junit.Test

class RxPropertyTest {
    class Model(property: String) : INotifyPropertyChanged {
        var property: String by PropertyChangedDelegate(property)
    }

    @Test
    fun PropertyChanged() {
        val model = Model("value1")
        val sub = { sender: Any, e: PropertyChangedEventArgs ->
            System.out.println("PropertyChanged: e.PropertyName: " + e.PropertyName)
            System.out.println("PropertyChanged: model.property: " + (sender as Model).property)
            Assert.assertEquals("value2", sender.property)
        }
        model.PropertyChanged += sub
        model.property = "value2"
        model.PropertyChanged -= sub
        model.property = "value3"
    }

    @Test
    fun PropertyChangedAsObservable() {
        val model = Model("value1")
        val subscribe = model.PropertyChangedAsObservable().subscribe {
            System.out.println("PropertyChangedAsObservable: it.PropertyName: " + it.PropertyName)
            System.out.println("PropertyChangedAsObservable: model.property: " + model.property)
            Assert.assertEquals("value2", model.property)
        }
        model.property = "value2"
        subscribe.unsubscribe()
        model.property = "value3"
    }

    @Test
    fun ObserveProperty() {
        val model = Model("value1")
        val subscribe = model.ObserveProperty("property", { it.property }).subscribe {
            System.out.println("ObserveProperty: it: " + it)
            System.out.println("ObserveProperty: model.property: " + model.property)
            Assert.assertEquals("value2", model.property)
        }
        model.property = "value2"
        subscribe.unsubscribe()
        model.property = "value3"
    }

    @Test
    fun RxProperty() {
        val model = Model("value1")
        val rxProperty = model.ObserveProperty("property", { it.property }).toRxProperty(model.property)
        model.property = "value2"
        rxProperty.unsubscribe()
        model.property = "value3"
        System.out.println("RxProperty: rxProperty.value: " + rxProperty.value)
        Assert.assertEquals("value2", rxProperty.value)
    }
}