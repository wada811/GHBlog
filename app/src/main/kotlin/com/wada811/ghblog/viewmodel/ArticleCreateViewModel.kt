package com.wada811.ghblog.viewmodel

import android.util.Log
import android.view.View
import com.wada811.ghblog.domain.GHBlogContext
import com.wada811.ghblog.domain.model.GitCommit
import com.wada811.rxviewmodel.RxCommand
import com.wada811.rxviewmodel.RxProperty
import com.wada811.rxviewmodel.RxViewModel
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class ArticleCreateViewModel : RxViewModel() {
    var path = RxProperty("").asManaged()
    var name = RxProperty("name").asManaged()
    var content = RxProperty("content").asManaged()
    var save = RxCommand(View.OnClickListener {
        val commit = GitCommit(path.value!!, "message", name.value + System.getProperty("line.separator") + content.value)
        GHBlogContext.currentUser.currentRepository!!.createContent(GHBlogContext.currentUser, commit)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.e("wada", "currentRepository.createContent.onNext")
                }, {
                    Log.e("wada", "currentRepository.createContent.onError", it)
                }, {
                    Log.e("wada", "currentRepository.createContent.onComplete")
                })
    }).asManaged()
}