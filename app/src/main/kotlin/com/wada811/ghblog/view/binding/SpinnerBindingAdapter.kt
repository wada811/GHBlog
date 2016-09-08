package com.wada811.ghblog.view.binding

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.wada811.ghblog.BR
import com.wada811.rxviewmodel.RxViewModel

open class SpinnerBindingAdapter<TBinding, TViewModel>(context: Context, private var layoutId: Int, items: List<TViewModel>)
: ArrayAdapter<TViewModel>(context, layoutId, items)
where TBinding : ViewDataBinding, TViewModel : RxViewModel {
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    @Suppress("UNCHECKED_CAST")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        var viewHolder: BindingViewHolder<TBinding>
        if (view == null) {
            viewHolder = onCreateViewHolder(parent)
            view = viewHolder.view
            view.tag = viewHolder
        }
        viewHolder = view.tag as BindingViewHolder<TBinding>
        onBindViewHolder(viewHolder, position)
        return viewHolder.view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }

    private fun onBindViewHolder(viewHolder: BindingViewHolder<TBinding>, position: Int) {
        viewHolder.binding.setVariable(BR.viewModel, getItem(position))
        viewHolder.binding.executePendingBindings()
    }

    private fun onCreateViewHolder(parent: ViewGroup): BindingViewHolder<TBinding> {
        val binding = DataBindingUtil.inflate<TBinding>(layoutInflater, layoutId, parent, false)
        return BindingViewHolder(binding)
    }

    internal inner class BindingViewHolder<TBinding>(var binding: TBinding) where TBinding : ViewDataBinding {
        var view: View = binding.root
    }
}
