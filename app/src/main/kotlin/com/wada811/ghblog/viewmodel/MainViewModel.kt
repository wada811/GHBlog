package com.wada811.ghblog.viewmodel

import com.wada811.ghblog.domain.GHBlogContext
import com.wada811.ghblog.view.activity.MainActivity
import com.wada811.logforest.LogWood
import com.wada811.rxviewmodel.RxMessenger
import com.wada811.rxviewmodel.RxViewModel

class MainViewModel : RxViewModel() {
    init {
        if (GHBlogContext.authorized) {
            RxMessenger.send(MainActivity.NextAction())
        } else {
            GHBlogContext.userRepository.getCurrentUser().subscribe({
                GHBlogContext.currentUser = it
                GHBlogContext.authorized = true
            }, {}, {
                LogWood.d("GHBlogContext.userRepository.getCurrentUser(): ${GHBlogContext.authorized}")
                if (GHBlogContext.authorized) {
                    RxMessenger.send(MainActivity.NextAction())
                } else {
                    RxMessenger.send(MainActivity.OAuthAction())
                }
            })
        }
    }
}