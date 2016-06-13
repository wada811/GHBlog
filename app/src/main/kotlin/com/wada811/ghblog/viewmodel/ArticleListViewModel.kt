package com.wada811.ghblog.viewmodel

import android.databinding.ObservableArrayList
import android.util.Log
import android.view.View
import android.widget.AdapterView
import com.wada811.ghblog.App
import com.wada811.rxviewmodel.RxCommand
import com.wada811.rxviewmodel.RxViewModel
import com.wada811.rxviewmodel.SynchronizedObservableArrayList

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
            App.currentRepository!!.tree(user).subscribe {
                tree ->
                articleViewModelList.addAll(tree.tree.map { tree -> ArticleListItemViewModel(tree) })
            }
        }

    }
}