package com.wada811.ghblog.viewmodel

import com.wada811.ghblog.model.domain.RepositoryContentInfo
import com.wada811.rxviewmodel.RxProperty
import com.wada811.rxviewmodel.RxViewModel

class ArticleListItemViewModel(var repositoryContentInfo: RepositoryContentInfo) : RxViewModel() {
    var articleName = RxProperty(repositoryContentInfo.name).asManaged()
}