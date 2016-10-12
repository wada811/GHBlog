package com.wada811.ghblog.viewmodel

import com.wada811.ghblog.domain.model.Article
import com.wada811.rxviewmodel.RxProperty
import com.wada811.rxviewmodel.RxViewModel
import com.wada811.rxviewmodel.extensions.ObserveProperty
import com.wada811.rxviewmodel.extensions.toRxProperty
import org.threeten.bp.format.DateTimeFormatter

class ArticleListItemViewModel(val article: Article) : RxViewModel() {
    val articleTitle = article.ObserveProperty("title", { it.title }).toRxProperty(article.title).asManaged()
    val articlePublishDate: RxProperty<String>
        = article.ObserveProperty("publishDateTime", { it.publishDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE) })
        .toRxProperty(article.publishDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE)).asManaged()
}