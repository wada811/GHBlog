package com.wada811.ghblog.viewmodel

import android.util.Log
import android.view.View
import android.widget.AdapterView
import com.wada811.ghblog.domain.GHBlogContext
import com.wada811.ghblog.domain.model.Repository
import com.wada811.ghblog.view.activity.RepositoryListActivity
import com.wada811.observablemodel.extensions.ToObservableSynchronizedArrayList
import com.wada811.rxviewmodel.RxCommand
import com.wada811.rxviewmodel.RxMessenger
import com.wada811.rxviewmodel.RxProperty
import com.wada811.rxviewmodel.RxViewModel
import com.wada811.rxviewmodel.extensions.toRxCommand

class RepositoryListViewModel : RxViewModel() {
    var repositoryViewModelList = GHBlogContext.currentUser.repositories.ToObservableSynchronizedArrayList { RepositoryListItemViewModel(it) }
    var selectedRepository = RxProperty<Repository?>().asManaged()
    var select = RxCommand(AdapterView.OnItemClickListener {
        parent: AdapterView<*>, view: View, position: Int, id: Long ->
        Log.e("wada", "" + parent.getItemAtPosition(position))
        val viewModel = parent.getItemAtPosition(position) as RepositoryListItemViewModel
        selectedRepository.value = viewModel.repository
        Log.e("wada", "selectedRepository: " + viewModel.repositoryName.value)
    }).asManaged()
    var save = selectedRepository
        .asObservable()
        .map { it != null }
        .toRxCommand(View.OnClickListener {
            Log.e("wada", "save button clicked!")
            Log.e("wada", "selectedRepository: " + selectedRepository.value!!.name)
            GHBlogContext.currentUser.currentRepository = selectedRepository.value
            RxMessenger.send(RepositoryListActivity.NextAction())
        })
        .asManaged()
    init {
        GHBlogContext.currentUser.loadRepositories()
    }
}