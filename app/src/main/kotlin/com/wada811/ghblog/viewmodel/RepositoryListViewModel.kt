package com.wada811.ghblog.viewmodel

import android.databinding.ObservableArrayList
import android.util.Log
import android.view.View
import android.widget.AdapterView
import com.wada811.ghblog.domain.GHBlogContext
import com.wada811.ghblog.domain.model.Repository
import com.wada811.ghblog.view.activity.RepositoryListActivity
import com.wada811.rxviewmodel.*
import com.wada811.rxviewmodel.extensions.toRxCommand
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class RepositoryListViewModel : RxViewModel() {
    var repositoryViewModelList = ObservableArrayList<RepositoryListItemViewModel>().toSynchronizedObservableArrayList()
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
        GHBlogContext.currentUser.repositoryList
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.e("wada", "user.repositoryList.onNext")
                    repositoryViewModelList.addAll(it.map { RepositoryListItemViewModel(it) })
                }, {
                    Log.e("wada", "user.repositoryList.onError", it)
                }, {
                    Log.e("wada", "user.repositoryList.onComplete")
                })
    }
}