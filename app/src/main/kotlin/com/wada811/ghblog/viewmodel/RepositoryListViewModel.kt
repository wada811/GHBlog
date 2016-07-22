package com.wada811.ghblog.viewmodel

import android.view.View
import android.widget.AdapterView
import com.wada811.ghblog.domain.GHBlogContext
import com.wada811.ghblog.domain.model.Repository
import com.wada811.ghblog.view.activity.RepositoryListActivity
import com.wada811.logforest.LogWood
import com.wada811.rxviewmodel.RxCommand
import com.wada811.rxviewmodel.RxMessenger
import com.wada811.rxviewmodel.RxProperty
import com.wada811.rxviewmodel.RxViewModel
import com.wada811.rxviewmodel.extensions.ToRxArrayList
import com.wada811.rxviewmodel.extensions.toRxCommand

class RepositoryListViewModel : RxViewModel() {
    init {
        GHBlogContext.currentUser.loadRepositories()
    }

    var repositoryViewModelList = GHBlogContext.currentUser.repositories.ToRxArrayList { RepositoryListItemViewModel(it) }.asManaged()
    var selectedRepository = RxProperty<Repository?>().asManaged()
    var select = RxCommand(AdapterView.OnItemClickListener {
        parent: AdapterView<*>, view: View, position: Int, id: Long ->
        LogWood.e("${parent.getItemAtPosition(position)}")
        val viewModel = parent.getItemAtPosition(position) as RepositoryListItemViewModel
        selectedRepository.value = viewModel.repository
        LogWood.e("selectedRepository: ${viewModel.repositoryName.value}")
    }).asManaged()
    var save = selectedRepository
        .asObservable()
        .map { it != null }
        .toRxCommand(View.OnClickListener {
            LogWood.e("save button clicked!")
            LogWood.e("selectedRepository: ${selectedRepository.value!!.name}")
            GHBlogContext.currentUser.currentRepository = selectedRepository.value
            RxMessenger.send(RepositoryListActivity.NextAction())
        })
        .asManaged()
}