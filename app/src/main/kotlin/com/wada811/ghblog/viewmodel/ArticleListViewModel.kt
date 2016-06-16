package com.wada811.ghblog.viewmodel

import android.databinding.ObservableArrayList
import android.util.Log
import android.view.View
import android.widget.AdapterView
import com.wada811.ghblog.App
import com.wada811.rxviewmodel.RxCommand
import com.wada811.rxviewmodel.RxViewModel
import com.wada811.rxviewmodel.SynchronizedObservableArrayList
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class ArticleListViewModel : RxViewModel() {
    val articleViewModelList = SynchronizedObservableArrayList(ObservableArrayList<ArticleListItemViewModel>())
    val edit = RxCommand(AdapterView.OnItemClickListener {
        parent: AdapterView<*>, view: View, position: Int, id: Long ->
        Log.e("wada", "edit: " + parent.getItemAtPosition(position))
        val viewModel = parent.getItemAtPosition(position) as ArticleListItemViewModel
    }).asManaged()
    var new = RxCommand(View.OnClickListener { Log.e("wada", "new") }).asManaged()

    init {
        App.user.subscribe {
            user ->
            App.currentRepository!!.getContents(user, "content/blog")
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Log.e("wada", "currentRepository.getContents.onNext")
                        articleViewModelList.addAll(it.map { ArticleListItemViewModel(it) })
                    }, { Log.e("wada", "currentRepository.getContents.onError", it) }, { Log.e("wada", "currentRepository.getContents.onComplete") })
        }

    }
}