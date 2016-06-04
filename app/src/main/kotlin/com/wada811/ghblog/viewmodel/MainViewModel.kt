package com.wada811.ghblog.viewmodel

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.util.Log
import com.wada811.ghblog.App
import com.wada811.ghblog.BR

class MainViewModel : BaseObservable() {

    @Bindable var userName: String = ""
        get
        set(value) {
            if (!value.equals(field)) {
                field = value
                Log.i("wada", field)
                notifyPropertyChanged(BR.userName);
            }
        }
    @Bindable var accessToken: String = ""
        get
        set(value) {
            if (!value.equals(field)) {
                field = value
                Log.i("wada", field)
                notifyPropertyChanged(BR.accessToken);
            }
        }

    init {
        App.user.subscribe { user ->
            userName = user.userName
            accessToken = user.accessToken
        }
    }
}