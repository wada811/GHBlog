package com.wada811.ghblog.viewmodel

import android.util.Log
import com.wada811.ghblog.domain.GHBlogContext
import com.wada811.ghblog.domain.model.RepositoryContent
import com.wada811.rxviewmodel.RxViewModel
import com.wada811.rxviewmodel.extensions.ObserveProperty
import com.wada811.rxviewmodel.extensions.toRxProperty
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class ArticleListItemViewModel(var repositoryContentInfo: RepositoryContent) : RxViewModel() {
    init {
        GHBlogContext.currentUser.currentRepository!!.getContent(repositoryContentInfo.path)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                repositoryContentInfo.encoding = it.encoding
                repositoryContentInfo.encodedContent = it.encodedContent
                repositoryContentInfo.content = it.content
                Log.e("wada", "getContent: repositoryContent.content: " + repositoryContentInfo.content)
            }, {
                Log.e("wada", "getContent.onError: " + it)
            }, {
                Log.e("wada", "getContent.onComplete")
            })
    }

    var articleName = repositoryContentInfo.ObserveProperty("name", { it.name }).toRxProperty(repositoryContentInfo.name).asManaged()
    var articleBody = repositoryContentInfo.ObserveProperty("content", { it.content }).toRxProperty(repositoryContentInfo.content).asManaged()
}