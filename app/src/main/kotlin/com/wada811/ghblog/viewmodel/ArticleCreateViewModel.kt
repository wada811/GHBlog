package com.wada811.ghblog.viewmodel

import android.util.Log
import android.view.View
import com.wada811.ghblog.domain.GHBlogContext
import com.wada811.ghblog.view.activity.ArticleCreateActivity
import com.wada811.rxviewmodel.RxCommand
import com.wada811.rxviewmodel.RxMessenger
import com.wada811.rxviewmodel.RxProperty
import com.wada811.rxviewmodel.RxViewModel
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class ArticleCreateViewModel : RxViewModel() {
    var path = RxProperty("").asManaged()
    var name = RxProperty("").asManaged()
    var content = RxProperty("").asManaged()
    var save = RxCommand(View.OnClickListener {
        GHBlogContext.currentUser.currentRepository!!
            .createContent(path.value!!, "Create ${path.value}", name.value + System.getProperty("line.separator") + content.value)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.e("wada", "currentRepository.createContent.onNext")
                RxMessenger.send(ArticleCreateActivity.SaveAction())
            }, {
                Log.e("wada", "currentRepository.createContent.onError", it)
            }, {
                Log.e("wada", "currentRepository.createContent.onComplete")
            })
    }).asManaged()
}