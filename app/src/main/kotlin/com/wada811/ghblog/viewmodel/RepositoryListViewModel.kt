package com.wada811.ghblog.viewmodel

import android.databinding.ObservableArrayList
import android.util.Log
import android.view.View
import android.widget.AdapterView
import com.wada811.ghblog.App
import com.wada811.ghblog.model.domain.Repository
import com.wada811.rxviewmodel.RxCommand
import com.wada811.rxviewmodel.RxProperty
import com.wada811.rxviewmodel.RxViewModel
import com.wada811.rxviewmodel.SynchronizedObservableArrayList
import com.wada811.rxviewmodel.extensions.toRxCommand
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class RepositoryListViewModel : RxViewModel() {
    var repositoryViewModelList = SynchronizedObservableArrayList(ObservableArrayList<RepositoryListItemViewModel>())
    var selectedRepositoryName = RxProperty<String?>().asManaged()
    var select = RxCommand(AdapterView.OnItemClickListener {
        parent: AdapterView<*>, view: View, position: Int, id: Long ->
        Log.e("wada", "" + parent.getItemAtPosition(position))
        val viewModel = parent.getItemAtPosition(position) as RepositoryListItemViewModel
        selectedRepositoryName.value = viewModel.repositoryName.value
        Log.e("wada", "selectedRepositoryName.value: " + selectedRepositoryName.value)
    }).asManaged()
    var save = selectedRepositoryName
            .asObservable()
            .map { it != null }
            .toRxCommand(View.OnClickListener {
                Log.e("wada", "save button clicked!")
                Log.e("wada", "selectedRepositoryName: " + selectedRepositoryName.value)
            })
            .asManaged()

    init {
        App.user.subscribe { user ->
            user.repositoryList
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ list ->
                        val repositoryList = list as List<*>
                        repositoryList.forEach { element ->
                            val repository = element as Repository
                            Log.e("wada", repository.toString())
                            repositoryViewModelList.add(RepositoryListItemViewModel(repository))
                        }
                    }, { Log.e("wada", "user.repositoryList.subscribe", it) }, { Log.e("wada", "onComplete") })
        }
    }
}