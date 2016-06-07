package com.wada811.ghblog.viewmodel

import android.databinding.ObservableField
import android.util.Log
import android.view.View
import com.wada811.ghblog.App
import com.wada811.rxviewmodel.RxProperty
import com.wada811.rxviewmodel.RxViewModel
import com.wada811.rxviewmodel.extensions.toRxCommand
import com.wada811.rxviewmodel.extensions.toRxProperty
import rx.Observable
import java.util.concurrent.TimeUnit

class MainViewModel : RxViewModel() {

    var userName: RxProperty<String> = App.user.map { it.userName }.toRxProperty().asManaged()
    var accessToken: RxProperty<String> = App.user.map { it.accessToken }.toRxProperty().asManaged()
    var input: Observable<Long> = Observable.interval(2, TimeUnit.SECONDS)
    var next = input.map { it.toInt() % 2 == 0 }.toRxCommand(View.OnClickListener { Log.d("wada", "Next Button is Tapped!") }).asManaged()
    var text: ObservableField<String> = ObservableField("")

    init {
        App.user.subscribe { Log.d("wada", "user.userName: " + it.userName + ", user.accessToken: " + it.accessToken) }
        input.subscribe { Log.d("wada", "interval: " + it) }
        input.subscribe { text.set("" + it) }
    }
}