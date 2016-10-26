package com.wada811.ghblog.viewmodel

import com.wada811.ghblog.domain.GHBlogContext
import com.wada811.ghblog.domain.model.Article
import com.wada811.ghblog.view.activity.ArticleListActivity
import com.wada811.logforest.LogWood
import com.wada811.observablemodel.extensions.ToSortedObservableSynchronizedArrayList
import com.wada811.rxviewmodel.RxCommand
import com.wada811.rxviewmodel.RxMessenger
import com.wada811.rxviewmodel.RxViewModel
import com.wada811.rxviewmodel.extensions.ObserveProperty
import com.wada811.rxviewmodel.extensions.ToRxArrayList
import com.wada811.rxviewmodel.extensions.toRxProperty

class ArticleListViewModel : RxViewModel() {
    val blog = GHBlogContext.currentUser.currentBlog!!
    val blogTitle = blog.ObserveProperty("title", { it.title }).toRxProperty(blog.title).asManaged()
    val articleViewModelList = blog.articles
        .ToSortedObservableSynchronizedArrayList({ -it.publishDateTime.toEpochSecond() })
        .ToRxArrayList({ ArticleListItemViewModel(it) }).asManaged()
    val edit = RxCommand({ position: Int ->
        LogWood.e("edit: " + articleViewModelList[position])
        val viewModel = articleViewModelList[position]
        blog.currentArticle = viewModel.article
        RxMessenger.send(ArticleListActivity.EditAction())
    }).asManaged()
    val new = RxCommand<Unit>({
        blog.currentArticle = Article(blog)
        RxMessenger.send(ArticleListActivity.CreateAction())
    }).asManaged()

}