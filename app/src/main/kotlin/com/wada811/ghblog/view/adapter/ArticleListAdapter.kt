package com.wada811.ghblog.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.wada811.ghblog.viewmodel.ArticleListItemViewModel
import com.wada811.view.binding.ArticleListItemBindingAdapter

class ArticleListAdapter(context: Context, var layoutId: Int, items: List<ArticleListItemViewModel>) :
        ArrayAdapter<ArticleListItemViewModel>(context, layoutId, items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ArticleListItemBindingAdapter
        var view = convertView
        if (view == null) {
            binding = ArticleListItemBindingAdapter(LayoutInflater.from(context), layoutId, parent)
            view = binding.view
            view.tag = binding
        } else {
            binding = view.tag as ArticleListItemBindingAdapter
        }
        binding.setViewModel(getItem(position))
        return view
    }
}