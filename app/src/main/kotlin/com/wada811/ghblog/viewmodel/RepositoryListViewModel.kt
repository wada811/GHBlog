package com.wada811.ghblog.viewmodel

import com.wada811.ghblog.domain.GHBlogContext
import com.wada811.ghblog.domain.model.Repository
import com.wada811.ghblog.view.activity.RepositoryListActivity
import com.wada811.logforest.LogWood
import com.wada811.rxviewmodel.*
import com.wada811.rxviewmodel.extensions.ToRxArrayList

class RepositoryListViewModel : RxViewModel() {
    var back = RxCommand<Unit>({
        RxMessenger.send(RepositoryListActivity.BackAction())
    }).asManaged()
    var repositoryViewModelList = GHBlogContext.currentUser.repositories.ToRxArrayList { RepositoryListItemViewModel(it) }.asManaged()
    var selectedRepository = RxProperty<Repository?>().asManaged()
    var select = RxCommand({ position: Int ->
        val viewModel = repositoryViewModelList[position]
        LogWood.e("$viewModel")
        selectedRepository.value = viewModel.repository
        LogWood.e("selectedRepository: ${viewModel.repositoryName.value}")
    }).asManaged()
    var save = selectedRepository
        .asObservable()
        .map { it != null }
        .toRxCommand<Unit>({
            LogWood.e("save button clicked!")
            LogWood.e("selectedRepository: ${selectedRepository.value!!.name}")
            GHBlogContext.currentUser.currentRepository = selectedRepository.value
            RxMessenger.send(RepositoryListActivity.NextAction())
        })
        .asManaged()
}