package com.wada811.ghblog.viewmodel

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.util.Log
import com.wada811.ghblog.BR

class MainViewModel : BaseObservable() {
    @Bindable var text : String = ""
    get
    set(value) {
        if(!field.equals(value)) {
            field = value
            Log.i("wada", field)
            notifyPropertyChanged(BR.text);
        }
    }
}