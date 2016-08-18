package com.wada811.ghblog.view.adapter

import android.content.Context
import com.wada811.ghblog.databinding.ListItemRepositoryBinding
import com.wada811.ghblog.view.binding.RecyclerViewBindingAdapter
import com.wada811.ghblog.viewmodel.RepositoryListItemViewModel

class RepositoryListAdapter(context: Context, layoutId: Int, items: List<RepositoryListItemViewModel>) :
    RecyclerViewBindingAdapter<ListItemRepositoryBinding, RepositoryListItemViewModel>(context, layoutId, items) {
}