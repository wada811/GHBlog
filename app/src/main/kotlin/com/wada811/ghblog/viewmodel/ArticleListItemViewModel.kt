package com.wada811.ghblog.viewmodel

import com.wada811.ghblog.domain.model.Article
import com.wada811.rxviewmodel.RxViewModel
import com.wada811.rxviewmodel.extensions.ObserveProperty
import com.wada811.rxviewmodel.extensions.toRxProperty

class ArticleListItemViewModel(var article: Article) : RxViewModel() {
    var articleTitle = article.ObserveProperty("title", { it.title }).toRxProperty(article.title).asManaged()
    var articleBody = article.ObserveProperty("body", { it.body }).toRxProperty(article.body).asManaged()
}