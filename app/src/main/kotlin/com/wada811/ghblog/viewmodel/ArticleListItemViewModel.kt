package com.wada811.ghblog.viewmodel

import com.wada811.ghblog.domain.model.RepositoryContent
import com.wada811.rxviewmodel.RxViewModel
import com.wada811.rxviewmodel.extensions.ObserveProperty
import com.wada811.rxviewmodel.extensions.toRxProperty

class ArticleListItemViewModel(var repositoryContentInfo: RepositoryContent) : RxViewModel() {
    init {
        repositoryContentInfo.loadContent()
    }

    var articleName = repositoryContentInfo.ObserveProperty("name", { it.name }).toRxProperty(repositoryContentInfo.name).asManaged()
    var articleBody = repositoryContentInfo.ObserveProperty("content", { it.content }).toRxProperty(repositoryContentInfo.content).asManaged()
}