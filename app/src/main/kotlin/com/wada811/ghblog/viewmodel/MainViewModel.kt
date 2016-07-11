package com.wada811.ghblog.viewmodel

import android.databinding.ObservableField
import android.util.Log
import android.view.View
import com.wada811.ghblog.domain.GHBlogContext
import com.wada811.ghblog.view.activity.MainActivity
import com.wada811.rxviewmodel.RxCommand
import com.wada811.rxviewmodel.RxMessenger
import com.wada811.rxviewmodel.RxViewModel
import com.wada811.rxviewmodel.extensions.ObserveProperty
import com.wada811.rxviewmodel.extensions.toRxProperty
import rx.Observable
import java.util.concurrent.TimeUnit

class MainViewModel : RxViewModel() {

    var userName = GHBlogContext.currentUser
        .ObserveProperty("userName", { it.userName })
        .toRxProperty(GHBlogContext.currentUser.userName)
        .asManaged()
    var accessToken = GHBlogContext.currentUser
        .ObserveProperty("accessToken", { it.accessToken })
        .toRxProperty(GHBlogContext.currentUser.accessToken)
        .asManaged()
    var input = Observable.interval(2, TimeUnit.SECONDS)
    var next = RxCommand(View.OnClickListener { RxMessenger.send(MainActivity.NextAction()) }).asManaged()
    var text = ObservableField("")

    init {
        input.subscribe { Log.d("wada", "interval: " + it) }.asManaged()
        input.subscribe { text.set("" + it) }.asManaged()
    }
}