package com.wada811.ghblog.view.binding.widget

import android.databinding.BindingAdapter
import android.databinding.ViewDataBinding
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.wada811.ghblog.view.binding.RecyclerViewBindingAdapter
import com.wada811.ghblog.view.widget.RecyclerViewItemClickListener
import com.wada811.rxviewmodel.RxCommand
import com.wada811.rxviewmodel.RxViewModel
import com.wada811.rxviewmodel.bind
import rx.Observable
import rx.subjects.PublishSubject

@BindingAdapter("onItemClick")
fun RecyclerView.setOnItemClick(command: RxCommand<Int>) = this.itemClickAsObservable().bind(command)

fun RecyclerView.itemClickAsObservable(): Observable<Int> {
    val subject = PublishSubject.create<Int>()
    val listener = RecyclerViewItemClickListener(this, object : RecyclerViewItemClickListener.OnItemClickListener {
        override fun onItemClick(position: Int) = subject.onNext(position)
    })
    return subject.doOnSubscribe { this.addOnItemTouchListener(listener) }.doOnUnsubscribe { this.removeOnItemTouchListener(listener) }
}

@Suppress("UNCHECKED_CAST")
@BindingAdapter("itemLayout", "items")
fun <TBinding : ViewDataBinding, TViewModel : RxViewModel> RecyclerView.setAdapter(layoutId: Int, items: List<TViewModel>) {
    var adapter = this.adapter as RecyclerViewBindingAdapter<TBinding, TViewModel>?
    val position = this.verticalScrollbarPosition
    if (adapter == null) {
        adapter = RecyclerViewBindingAdapter<TBinding, TViewModel>(this.context, layoutId, items)
        this.adapter = adapter
        this.layoutManager = LinearLayoutManager(this.context)
    } else {
        adapter.notifyDataSetChanged()
        this.verticalScrollbarPosition = position
    }
}