package com.wada811.ghblog.viewmodel

import com.wada811.ghblog.domain.GHBlogContext
import com.wada811.ghblog.view.activity.MainActivity
import com.wada811.logforest.LogWood
import com.wada811.rxviewmodel.RxMessenger
import com.wada811.rxviewmodel.RxViewModel
import com.wada811.rxviewmodel.extensions.ObserveProperty

class MainViewModel : RxViewModel() {
    init {
        if (GHBlogContext.authorized) {
            goNext()
        } else {
            GHBlogContext.userRepository.getCurrentUser()
                .subscribe({
                    GHBlogContext.currentUser = it
                    GHBlogContext.authorized = true
                }, {

                }, {
                    LogWood.d("GHBlogContext.userRepository.getCurrentUser(): ${GHBlogContext.authorized}")
                    if (GHBlogContext.authorized) {
                        goNext()
                    } else {
                        RxMessenger.send(MainActivity.OAuthAction())
                    }
                })
        }
    }

    private fun goNext() {
        LogWood.d("goNext")
        GHBlogContext.currentUser.ObserveProperty("initialized", { it.initialized })
            .subscribe({
                LogWood.d("initialized: GHBlogContext.currentUser.blogs: ${GHBlogContext.currentUser.blogs.size}")
                if (GHBlogContext.currentUser.blogs.isEmpty()) {
                    RxMessenger.send(MainActivity.SelectRepositoryAction())
                } else {
                    RxMessenger.send(MainActivity.ShowArticleListAction())
                }
            })
        GHBlogContext.currentUser.initialize()
    }
}