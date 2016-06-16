package com.wada811.ghblog.viewmodel

import com.wada811.ghblog.model.domain.Content
import com.wada811.rxviewmodel.RxProperty
import com.wada811.rxviewmodel.RxViewModel

class ArticleListItemViewModel(content: Content) : RxViewModel() {
    var articleName = RxProperty(content.name).asManaged()
}