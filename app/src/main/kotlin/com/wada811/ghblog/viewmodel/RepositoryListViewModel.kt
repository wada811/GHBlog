package com.wada811.ghblog.viewmodel

import android.databinding.ObservableArrayList
import android.util.Log
import android.view.View
import android.widget.AdapterView
import com.wada811.rxviewmodel.RxCommand
import com.wada811.rxviewmodel.RxProperty
import com.wada811.rxviewmodel.RxViewModel
import com.wada811.rxviewmodel.extensions.toRxCommand
import rx.Observable
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class RepositoryListViewModel : RxViewModel() {
    var repositoryViewModelList = ObservableArrayList<RepositoryListItemViewModel>()
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
        Observable.interval(1, TimeUnit.SECONDS).subscribe {
            repositoryViewModelList.add(RepositoryListItemViewModel("io: " + it))
        }
        Observable.interval(1, TimeUnit.SECONDS, Schedulers.newThread()).subscribe {
            repositoryViewModelList.add(RepositoryListItemViewModel("thread: " + it))
        }
    }
}