package com.wada811.ghblog.viewmodel

import android.util.Log
import com.wada811.ghblog.App
import com.wada811.ghblog.domain.model.GitCommit
import com.wada811.ghblog.domain.model.RepositoryContent
import com.wada811.rxviewmodel.RxViewModel
import com.wada811.rxviewmodel.extensions.ObserveProperty
import com.wada811.rxviewmodel.extensions.toRxProperty
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class ArticleListItemViewModel(var repositoryContentInfo: RepositoryContent) : RxViewModel() {
    val commit = GitCommit(repositoryContentInfo.path, "", "", repositoryContentInfo.sha)

    init {
        App.user.subscribe { user ->
            App.currentRepository!!.getContent(user, repositoryContentInfo.path)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ repositoryContent: RepositoryContent ->
                        repositoryContentInfo.content = repositoryContent.content
                        repositoryContentInfo.encoding = repositoryContent.encoding
                        repositoryContentInfo.encodedContent = repositoryContent.encodedContent
                        Log.e("wada", "getContent: repositoryContent.content: " + repositoryContent.content)
                        commit.content = repositoryContent.content
                    }, { Log.e("wada", "getContent.onError: " + it) }, { Log.e("wada", "getContent.onComplete") })

        }
    }

    var articleName = repositoryContentInfo.ObserveProperty("name", { it.name }).toRxProperty(repositoryContentInfo.name).asManaged()
    var articleBody = repositoryContentInfo.ObserveProperty("content", { it.content }).toRxProperty(repositoryContentInfo.content).asManaged()
}