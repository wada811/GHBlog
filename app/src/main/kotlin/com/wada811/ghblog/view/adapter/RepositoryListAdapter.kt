package com.wada811.ghblog.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.wada811.ghblog.viewmodel.RepositoryListItemViewModel
import com.wada811.view.binding.RepositoryListItemBindingAdapter

class RepositoryListAdapter(context: Context, var layoutId: Int, items: List<RepositoryListItemViewModel>) :
        ArrayAdapter<RepositoryListItemViewModel>(context, layoutId, items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: RepositoryListItemBindingAdapter
        var view = convertView
        if (view == null) {
            binding = RepositoryListItemBindingAdapter(LayoutInflater.from(context), layoutId, parent)
            view = binding.view
            view.tag = binding
        } else {
            binding = view.tag as RepositoryListItemBindingAdapter
        }
        binding.setViewModel(getItem(position))
        return view
    }
}