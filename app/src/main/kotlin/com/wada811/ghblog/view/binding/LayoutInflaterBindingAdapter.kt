package com.wada811.ghblog.view.binding

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wada811.rxviewmodel.BR
import com.wada811.rxviewmodel.RxViewModel
import rx.Subscription
import rx.subscriptions.CompositeSubscription

abstract class LayoutInflaterBindingAdapter<out TBinding : ViewDataBinding, in TViewModel : RxViewModel>
(layoutInflater: LayoutInflater, layoutId: Int, viewGroup: ViewGroup) : Subscription {

    protected val binding: TBinding
    private val subscriptions = CompositeSubscription()

    init {
        binding = DataBindingUtil.inflate<TBinding>(layoutInflater, layoutId, viewGroup, false)
    }

    val view: View = binding.root
    fun setViewModel(viewModel: TViewModel) {
        subscriptions.add(viewModel)
        binding.setVariable(BR.viewModel, viewModel)
        binding.executePendingBindings()
    }

    override fun isUnsubscribed(): Boolean = subscriptions.isUnsubscribed
    override fun unsubscribe() {
        if (!isUnsubscribed) {
            subscriptions.unsubscribe()
            binding.unbind()
        }
    }

}
