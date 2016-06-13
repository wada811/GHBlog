package com.wada811.ghblog.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.wada811.ghblog.view.binding.ArticleListItemBindingAdapter
import com.wada811.ghblog.viewmodel.ArticleListItemViewModel

class ArticleListAdapter(context: Context, var layoutId: Int, items: List<ArticleListItemViewModel>) :
        ArrayAdapter<ArticleListItemViewModel>(context, layoutId, items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val binding: ArticleListItemBindingAdapter
        var view = convertView
        if (convertView == null) {
            binding = ArticleListItemBindingAdapter(LayoutInflater.from(context), layoutId, parent)
            view = binding.root
            view.tag = binding
        } else {
            binding = convertView.tag as ArticleListItemBindingAdapter
        }
        binding.viewModel = getItem(position)
        return view
    }
}