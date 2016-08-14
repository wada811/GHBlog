package com.wada811.ghblog.viewmodel

import android.view.View
import android.widget.AdapterView
import com.wada811.ghblog.domain.GHBlogContext
import com.wada811.ghblog.domain.model.Article
import com.wada811.ghblog.view.activity.ArticleListActivity
import com.wada811.logforest.LogWood
import com.wada811.rxviewmodel.RxCommand
import com.wada811.rxviewmodel.RxMessenger
import com.wada811.rxviewmodel.RxViewModel
import com.wada811.rxviewmodel.extensions.ToRxArrayList

class ArticleListViewModel : RxViewModel() {
    val repository = GHBlogContext.currentUser.currentRepository!!
    init {
        repository.loadContents("content/blog")
    }

    val articleViewModelList = repository.repositoryContents.ToRxArrayList { ArticleListItemViewModel(it) }.asManaged()
    val edit = RxCommand(AdapterView.OnItemClickListener {
        parent: AdapterView<*>, view: View, position: Int, id: Long ->
        LogWood.e("edit: " + parent.getItemAtPosition(position))
        val viewModel = parent.getItemAtPosition(position) as ArticleListItemViewModel
        repository.currentArticle = Article(GHBlogContext.currentUser, repository, viewModel.repositoryContentInfo)
        RxMessenger.send(ArticleListActivity.EditAction())
    }).asManaged()
    var new = RxCommand(View.OnClickListener {
        repository.currentArticle = Article(GHBlogContext.currentUser, repository)
        RxMessenger.send(ArticleListActivity.CreateAction())
    }).asManaged()

}