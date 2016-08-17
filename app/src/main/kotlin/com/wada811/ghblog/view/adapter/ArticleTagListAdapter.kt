package com.wada811.ghblog.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.wada811.ghblog.viewmodel.ArticleTagListItemViewModel
import com.wada811.view.binding.ArticleTagListItemBindingAdapter

class ArticleTagListAdapter(context: Context, var layoutId: Int, items: List<ArticleTagListItemViewModel>) :
    ArrayAdapter<ArticleTagListItemViewModel>(context, layoutId, items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ArticleTagListItemBindingAdapter
        var view = convertView
        if (view == null) {
            binding = ArticleTagListItemBindingAdapter(LayoutInflater.from(context), layoutId, parent)
            view = binding.view
            view.tag = binding
        } else {
            binding = view.tag as ArticleTagListItemBindingAdapter
        }
        binding.setViewModel(getItem(position))
        return view
    }
}