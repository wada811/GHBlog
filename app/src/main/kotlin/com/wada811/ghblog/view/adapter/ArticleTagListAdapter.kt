package com.wada811.ghblog.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.wada811.ghblog.view.binding.ArticleTagListItemBindingAdapter
import com.wada811.ghblog.viewmodel.ArticleTagListItemViewModel

class ArticleTagListAdapter(context: Context, var layoutId: Int, items: List<ArticleTagListItemViewModel>) :
    ArrayAdapter<ArticleTagListItemViewModel>(context, layoutId, items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val binding: ArticleTagListItemBindingAdapter
        var view = convertView
        if (convertView == null) {
            binding = ArticleTagListItemBindingAdapter(LayoutInflater.from(context), layoutId, parent)
            view = binding.root
            view.tag = binding
        } else {
            binding = convertView.tag as ArticleTagListItemBindingAdapter
        }
        binding.viewModel = getItem(position)
        return view
    }
}