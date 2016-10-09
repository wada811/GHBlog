package com.wada811.ghblog.viewmodel

import com.wada811.ghblog.domain.GHBlogContext
import com.wada811.ghblog.domain.model.Article
import com.wada811.ghblog.view.activity.ArticleListActivity
import com.wada811.logforest.LogWood
import com.wada811.rxviewmodel.RxCommand
import com.wada811.rxviewmodel.RxMessenger
import com.wada811.rxviewmodel.RxViewModel
import com.wada811.rxviewmodel.extensions.ToRxArrayList

class ArticleListViewModel : RxViewModel() {
    val repository = GHBlogContext.currentUser.currentBlog!!.repository

    init {
        repository.loadContents("content/blog")
    }

    val articleViewModelList = repository.repositoryContents.ToRxArrayList { ArticleListItemViewModel(it) }.asManaged()
    val edit = RxCommand({ position: Int ->
        LogWood.e("edit: " + articleViewModelList[position])
        val viewModel = articleViewModelList[position]
        repository.currentArticle = Article(GHBlogContext.currentUser, repository, viewModel.repositoryContentInfo)
        RxMessenger.send(ArticleListActivity.EditAction())
    }).asManaged()
    var new = RxCommand<Unit>({
        repository.currentArticle = Article(GHBlogContext.currentUser, repository)
        RxMessenger.send(ArticleListActivity.CreateAction())
    }).asManaged()

}