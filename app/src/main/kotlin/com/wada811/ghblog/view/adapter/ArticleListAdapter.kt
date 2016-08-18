package com.wada811.ghblog.view.adapter

import android.content.Context
import com.wada811.ghblog.databinding.ListItemArticleBinding
import com.wada811.ghblog.view.binding.RecyclerViewBindingAdapter
import com.wada811.ghblog.viewmodel.ArticleListItemViewModel

class ArticleListAdapter(context: Context, layoutId: Int, items: List<ArticleListItemViewModel>) :
    RecyclerViewBindingAdapter<ListItemArticleBinding, ArticleListItemViewModel>(context, layoutId, items) {
}