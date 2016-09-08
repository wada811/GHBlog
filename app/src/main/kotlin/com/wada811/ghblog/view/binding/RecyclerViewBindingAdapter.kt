package com.wada811.ghblog.view.binding

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.wada811.ghblog.BR
import com.wada811.rxviewmodel.RxViewModel
import rx.Subscription
import rx.subscriptions.CompositeSubscription

open class RecyclerViewBindingAdapter<TBinding, TViewModel>(context: Context, private var layoutId: Int, private var items: List<TViewModel>)
: RecyclerView.Adapter<RecyclerViewBindingAdapter.BindingViewHolder<TBinding>>(), Subscription
where TBinding : ViewDataBinding, TViewModel : RxViewModel {

    private val subscriptions = CompositeSubscription()
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<TBinding> {
        val binding = DataBindingUtil.inflate<TBinding>(layoutInflater, layoutId, parent, false)
        return BindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingViewHolder<TBinding>, position: Int) {
        holder.binding.setVariable(BR.viewModel, items[position])
        holder.binding.executePendingBindings()
    }

    override fun onViewRecycled(holder: BindingViewHolder<TBinding>) {
        super.onViewRecycled(holder)
        holder.binding.unbind()
    }

    override fun isUnsubscribed(): Boolean = subscriptions.isUnsubscribed
    override fun unsubscribe() {
        if (!isUnsubscribed) {
            subscriptions.unsubscribe()
        }
    }

    class BindingViewHolder<TBinding>(var binding: TBinding) : RecyclerView.ViewHolder(binding.root) where TBinding : ViewDataBinding
}