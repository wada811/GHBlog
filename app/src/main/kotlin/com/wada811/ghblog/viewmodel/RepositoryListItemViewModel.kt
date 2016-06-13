package com.wada811.ghblog.viewmodel

import com.wada811.ghblog.model.domain.Repository
import com.wada811.rxviewmodel.RxProperty
import com.wada811.rxviewmodel.RxViewModel

class RepositoryListItemViewModel(var repository: Repository) : RxViewModel() {
    var repositoryName = RxProperty(repository.name).asManaged()
}