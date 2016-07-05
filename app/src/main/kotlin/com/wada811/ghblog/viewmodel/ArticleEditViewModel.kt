package com.wada811.ghblog.viewmodel

import android.util.Log
import android.view.View
import com.wada811.ghblog.App
import com.wada811.ghblog.domain.model.GitCommit
import com.wada811.ghblog.domain.model.RepositoryContent
import com.wada811.rxviewmodel.RxCommand
import com.wada811.rxviewmodel.RxViewModel
import com.wada811.rxviewmodel.extensions.ObserveProperty
import com.wada811.rxviewmodel.extensions.toRxProperty
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class ArticleEditViewModel() : RxViewModel() {
    val commit = GitCommit(App.currentArticleViewModel!!.repositoryContentInfo.path, "", "", App.currentArticleViewModel!!.repositoryContentInfo.sha)

    init {
        App.user.subscribe { user ->
            App.currentRepository!!.getContent(user, App.currentArticleViewModel!!.repositoryContentInfo.path)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ repositoryContent: RepositoryContent ->
                        Log.e("wada", "getContent: repositoryContent.content: " + repositoryContent.content)
                        commit.path = repositoryContent.path
                        commit.content = repositoryContent.content
                        commit.sha = repositoryContent.sha
                    }, { Log.e("wada", "getContent.onError: " + it) }, { Log.e("wada", "getContent.onComplete") })

        }
    }

    var path = commit.ObserveProperty("path", { it.path }).toRxProperty(commit.path).asManaged()
    var name = commit.ObserveProperty("content", { it.content })
            .map {
                Log.e("wada", "commit.ObserveProperty { it.content }: " + it)
                Log.e("wada", "commit.ObserveProperty { it.content }: name: " + it.splitToSequence(System.getProperty("line.separator")).first())
                it.splitToSequence(System.getProperty("line.separator")).first()
            }
            .toRxProperty(commit.content.splitToSequence(System.getProperty("line.separator")).first())
            .asManaged()
    var content = commit.ObserveProperty("content", { it.content })
            .map {
                Log.e("wada", "commit.ObserveProperty { it.content }: " + it)
                Log.e("wada", "commit.ObserveProperty { it.content }: content: " + it.splitToSequence(System.getProperty("line.separator"), limit = 2).last())
                it.splitToSequence(System.getProperty("line.separator"), limit = 2).last()
            }
            .toRxProperty(commit.content.splitToSequence(System.getProperty("line.separator"), limit = 2).last())
            .asManaged()
    var save = RxCommand(View.OnClickListener {
        App.user.subscribe { user ->
            Log.e("wada", "currentRepository.updateContent: path.value: " + path.value)
            Log.e("wada", "currentRepository.updateContent: name.value: " + name.value)
            Log.e("wada", "currentRepository.updateContent: content.value: " + content.value)
            commit.message = "message"
            Log.e("wada", "currentRepository.updateContent: commit.content: " + commit.content)
            commit.content = name.value + System.getProperty("line.separator") + content.value
            Log.e("wada", "currentRepository.updateContent: commit.content: " + commit.content)
            App.currentRepository!!.updateContent(user, commit)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Log.e("wada", "currentRepository.updateContent.onNext")
                    }, {
                        Log.e("wada", "currentRepository.updateContent.onError", it)
                    }, {
                        Log.e("wada", "currentRepository.updateContent.onComplete")
                    })
        }
    }).asManaged()

}