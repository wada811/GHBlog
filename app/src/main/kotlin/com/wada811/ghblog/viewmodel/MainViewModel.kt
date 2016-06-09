package com.wada811.ghblog.viewmodel

import android.databinding.ObservableField
import android.util.Log
import android.view.View
import com.wada811.ghblog.App
import com.wada811.ghblog.view.activity.MainActivity
import com.wada811.rxviewmodel.RxCommand
import com.wada811.rxviewmodel.RxMessenger
import com.wada811.rxviewmodel.RxProperty
import com.wada811.rxviewmodel.RxViewModel
import com.wada811.rxviewmodel.extensions.toRxProperty
import rx.Observable
import java.util.concurrent.TimeUnit

class MainViewModel : RxViewModel() {

    var userName: RxProperty<String> = App.user.map { it.userName }.toRxProperty().asManaged()
    var accessToken: RxProperty<String> = App.user.map { it.accessToken }.toRxProperty().asManaged()
    var input: Observable<Long> = Observable.interval(2, TimeUnit.SECONDS)
    var next = RxCommand(View.OnClickListener { RxMessenger.send(MainActivity.NextAction()) }).asManaged()
    var text: ObservableField<String> = ObservableField("")

    init {
        App.user.subscribe { Log.d("wada", "user.userName: " + it.userName + ", user.accessToken: " + it.accessToken) }.asManaged()
        input.subscribe { Log.d("wada", "interval: " + it) }.asManaged()
        input.subscribe { text.set("" + it) }.asManaged()
    }
}