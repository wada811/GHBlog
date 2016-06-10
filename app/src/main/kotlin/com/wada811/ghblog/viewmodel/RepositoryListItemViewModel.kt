package com.wada811.ghblog.viewmodel

import com.wada811.rxviewmodel.RxProperty
import com.wada811.rxviewmodel.RxViewModel

class RepositoryListItemViewModel(repositoryName: String) : RxViewModel() {
    var repositoryName = RxProperty(repositoryName).asManaged()
}