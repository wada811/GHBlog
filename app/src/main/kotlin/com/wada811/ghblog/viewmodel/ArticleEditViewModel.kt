package com.wada811.ghblog.viewmodel

import android.util.Log
import android.view.View
import com.wada811.ghblog.domain.GHBlogContext
import com.wada811.ghblog.view.activity.ArticleEditActivity
import com.wada811.rxviewmodel.RxCommand
import com.wada811.rxviewmodel.RxMessenger
import com.wada811.rxviewmodel.RxProperty
import com.wada811.rxviewmodel.RxViewModel
import com.wada811.rxviewmodel.extensions.ObserveProperty
import com.wada811.rxviewmodel.extensions.toRxProperty
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class ArticleEditViewModel() : RxViewModel() {
    var repositoryContent = GHBlogContext.currentUser.currentRepository!!.currentRepositoryContent!!

    init {
        GHBlogContext.currentUser.currentRepository!!
            .getContent(repositoryContent.path)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                repositoryContent.encoding = it.encoding
                repositoryContent.encodedContent = it.encodedContent
                repositoryContent.content = it.content
            }, {
                Log.e("wada", "getContent.onError: " + it)
            }, {
                Log.e("wada", "getContent.onComplete")
            })
    }

    var path = repositoryContent.ObserveProperty("path", { it.path }).toRxProperty(repositoryContent.path).asManaged()
    var name: RxProperty<String> = repositoryContent.ObserveProperty("content", { it.content })
        .map { it.splitToSequence(System.getProperty("line.separator")).first() }
        .toRxProperty(repositoryContent.content.splitToSequence(System.getProperty("line.separator")).first())
        .asManaged()
    var content: RxProperty<String> = repositoryContent.ObserveProperty("content", { it.content })
        .map { it.splitToSequence(System.getProperty("line.separator"), limit = 2).last() }
        .toRxProperty(repositoryContent.content.splitToSequence(System.getProperty("line.separator"), limit = 2).last())
        .asManaged()
    var save = RxCommand(View.OnClickListener {
        val contentString = name.value + System.getProperty("line.separator") + content.value
        repositoryContent.update(path.value!!, "Update ${path.value!!}", contentString)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                repositoryContent.sha = it.content!!.sha
                repositoryContent.path = it.content!!.path
                repositoryContent.content = contentString
                Log.e("wada", "currentRepository.updateContent.onNext")
                RxMessenger.send(ArticleEditActivity.SaveAction())
            }, {
                Log.e("wada", "currentRepository.updateContent.onError", it)
            }, {
                Log.e("wada", "currentRepository.updateContent.onComplete")
            })
    }).asManaged()
}