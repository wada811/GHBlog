package com.wada811.ghblog.viewmodel

import android.net.Uri
import com.wada811.ghblog.domain.GHBlogContext
import com.wada811.ghblog.view.activity.OAuthActivity
import com.wada811.logforest.LogWood
import com.wada811.rxviewmodel.RxMessenger
import com.wada811.rxviewmodel.RxProperty
import com.wada811.rxviewmodel.RxViewModel
import rx.schedulers.Schedulers

class OAuthViewModel(authorizedUrl: String?) : RxViewModel() {
    val loading: RxProperty<Boolean> = RxProperty(authorizedUrl == null).asManaged()

    init {
        if (authorizedUrl == null) {
            loading.value = true
            val authorizeUrl: String = Uri.Builder()
                .scheme("https")
                .authority("github.com")
                .path("login/oauth/authorize")
                .appendQueryParameter("client_id", GHBlogContext.gitHubApp.clientId)
                .appendQueryParameter("scope", GHBlogContext.gitHubApp.scopes)
                .appendQueryParameter("state", GHBlogContext.gitHubApp.state)
                .build()
                .toString()
            RxMessenger.send(OAuthActivity.AuthorizeAction(authorizeUrl))
        } else {
            LogWood.d("authorizedUrl: $authorizedUrl")
            val code = Uri.parse(authorizedUrl).getQueryParameter("code")
            val error = Uri.parse(authorizedUrl).getQueryParameter("error")
            LogWood.v("code: $code")
            LogWood.v("error: $error")
            if (code != null && error == null) {
                LogWood.d("code: $code")
                LogWood.d("GHBlogContext.state: ${GHBlogContext.gitHubApp.state}")
                GHBlogContext.userRepository.getAccessToken(code, GHBlogContext.gitHubApp.state)
                    .subscribeOn(Schedulers.newThread())
                    .subscribe {
                        loading.value = false
                        GHBlogContext.userRepository.getUser(it)
                            .subscribeOn(Schedulers.newThread())
                            .subscribe {
                                GHBlogContext.currentUser = it
                            }
                        RxMessenger.send(OAuthActivity.CompleteAction())
                    }
            } else {
                LogWood.e("error: $error")
            }
        }
    }


}