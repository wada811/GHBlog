package com.wada811.ghblog.viewmodel

import com.wada811.ghblog.model.domain.GitTree
import com.wada811.rxviewmodel.RxProperty
import com.wada811.rxviewmodel.RxViewModel

class ArticleListItemViewModel(node: GitTree.Node) : RxViewModel() {
    var articleName = RxProperty(node.path).asManaged()
}