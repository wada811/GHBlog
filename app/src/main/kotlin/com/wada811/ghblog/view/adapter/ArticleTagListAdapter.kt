package com.wada811.ghblog.view.adapter

import android.content.Context
import com.wada811.ghblog.databinding.ListItemTagBinding
import com.wada811.ghblog.view.binding.RecyclerViewBindingAdapter
import com.wada811.ghblog.viewmodel.ArticleTagListItemViewModel

class ArticleTagListAdapter(context: Context, layoutId: Int, items: List<ArticleTagListItemViewModel>) :
    RecyclerViewBindingAdapter<ListItemTagBinding, ArticleTagListItemViewModel>(context, layoutId, items) {
}