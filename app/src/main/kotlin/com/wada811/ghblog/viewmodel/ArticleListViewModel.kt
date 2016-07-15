package com.wada811.ghblog.viewmodel

import android.util.Log
import android.view.View
import android.widget.AdapterView
import com.wada811.ghblog.domain.GHBlogContext
import com.wada811.ghblog.domain.model.RepositoryContent
import com.wada811.ghblog.view.activity.ArticleListActivity
import com.wada811.observablemodel.ObservableSynchronizedArrayList
import com.wada811.rxviewmodel.RxCommand
import com.wada811.rxviewmodel.RxMessenger
import com.wada811.rxviewmodel.RxViewModel
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class ArticleListViewModel : RxViewModel() {
    val articleViewModelList = ObservableSynchronizedArrayList<ArticleListItemViewModel>()
    val edit = RxCommand(AdapterView.OnItemClickListener {
        parent: AdapterView<*>, view: View, position: Int, id: Long ->
        Log.e("wada", "edit: " + parent.getItemAtPosition(position))
        val viewModel = parent.getItemAtPosition(position) as ArticleListItemViewModel
        GHBlogContext.currentUser.currentRepository!!.currentRepositoryContent = viewModel.repositoryContentInfo
        RxMessenger.send(ArticleListActivity.EditAction())
    }).asManaged()
    var new = RxCommand(View.OnClickListener {
        RxMessenger.send(ArticleListActivity.CreateAction())
    }).asManaged()

    init {
        GHBlogContext.currentUser.currentRepository!!.getContents(GHBlogContext.currentUser, "content/blog")
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.e("wada", "currentRepository.getContents.onNext")
                articleViewModelList.addAll(it.map { ArticleListItemViewModel(RepositoryContent(it)) })
            }, {
                Log.e("wada", "currentRepository.getContents.onError", it)
            }, {
                Log.e("wada", "currentRepository.getContents.onComplete")
            })
    }
}