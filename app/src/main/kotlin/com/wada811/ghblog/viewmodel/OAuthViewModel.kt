package com.wada811.ghblog.viewmodel

import android.net.Uri
import com.wada811.ghblog.domain.GHBlogContext
import com.wada811.ghblog.view.activity.OAuthActivity
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
            System.out.println("authorizedUrl: $authorizedUrl")
            val code = Uri.parse(authorizedUrl).getQueryParameter("code")
            val error = Uri.parse(authorizedUrl).getQueryParameter("error")
            System.out.println("code: $code")
            System.out.println("error: $error")
            if (code != null && error == null) {
                System.out.println("code: $code")
                System.out.println("GHBlogContext.state: ${GHBlogContext.gitHubApp.state}")
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
                System.out.println("error: $error")
            }
        }
    }


}