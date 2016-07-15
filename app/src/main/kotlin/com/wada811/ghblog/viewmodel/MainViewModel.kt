package com.wada811.ghblog.viewmodel

import android.view.View
import com.wada811.ghblog.domain.GHBlogContext
import com.wada811.ghblog.domain.model.User
import com.wada811.ghblog.view.activity.MainActivity
import com.wada811.rxviewmodel.RxCommand
import com.wada811.rxviewmodel.RxMessenger
import com.wada811.rxviewmodel.RxViewModel
import com.wada811.rxviewmodel.extensions.ObserveProperty
import com.wada811.rxviewmodel.extensions.toRxProperty

class MainViewModel : RxViewModel() {
    var userName = GHBlogContext.currentUser
        .ObserveProperty("userName", { it.userName })
        .toRxProperty(GHBlogContext.currentUser.userName)
        .asManaged()
    var accessToken = GHBlogContext.currentUser
        .ObserveProperty("accessToken", { it.accessToken })
        .toRxProperty(GHBlogContext.currentUser.accessToken)
        .asManaged()
    var next = RxCommand(View.OnClickListener {
        GHBlogContext.currentUser = User(userName.value!!, accessToken.value!!)
        RxMessenger.send(MainActivity.NextAction())
    }).asManaged()
}