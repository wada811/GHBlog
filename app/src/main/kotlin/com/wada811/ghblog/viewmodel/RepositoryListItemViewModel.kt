package com.wada811.ghblog.viewmodel

import com.wada811.ghblog.domain.model.Repository
import com.wada811.rxviewmodel.RxViewModel
import com.wada811.rxviewmodel.extensions.ObserveProperty
import com.wada811.rxviewmodel.extensions.toRxProperty

class RepositoryListItemViewModel(val repository: Repository) : RxViewModel() {
    val repositoryName = repository.ObserveProperty("fullName", { it.fullName }).toRxProperty(repository.fullName).asManaged()
}