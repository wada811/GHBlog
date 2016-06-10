package com.wada811.ghblog.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.wada811.ghblog.view.binding.RepositoryListItemBindingAdapter
import com.wada811.ghblog.viewmodel.RepositoryListItemViewModel

class RepositoryListAdapter(context: Context, var layoutId: Int, items: List<RepositoryListItemViewModel>) :
        ArrayAdapter<RepositoryListItemViewModel>(context, layoutId, items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val binding: RepositoryListItemBindingAdapter
        var view = convertView
        if (convertView == null) {
            binding = RepositoryListItemBindingAdapter(LayoutInflater.from(context), layoutId, parent)
            view = binding.root
            view.tag = binding
        } else {
            binding = convertView.tag as RepositoryListItemBindingAdapter
        }
        binding.viewModel = getItem(position)
        return view
    }
}