package com.wada811.ghblog.view.binding

import android.app.Activity
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import com.wada811.ghblog.BR
import com.wada811.rxviewmodel.RxViewModel
import rx.Subscription
import rx.subscriptions.CompositeSubscription

open class ActivityBindingAdapter<TBinding, TViewModel>(activity: Activity, layoutId: Int, viewModel: TViewModel) : Subscription
where TBinding : ViewDataBinding, TViewModel : RxViewModel {

    private val binding: TBinding
    private val subscriptions = CompositeSubscription()

    init {
        subscriptions.add(viewModel)
        binding = DataBindingUtil.setContentView<TBinding>(activity, layoutId)
        binding.setVariable(BR.viewModel, viewModel)
    }

    override fun isUnsubscribed(): Boolean = subscriptions.isUnsubscribed
    override fun unsubscribe() {
        if (!isUnsubscribed) {
            subscriptions.unsubscribe()
            binding.unbind()
        }
    }

}
