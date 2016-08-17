package com.wada811.view.binding;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.wada811.ghblog.databinding.ListItemArticleBinding;
import com.wada811.ghblog.view.binding.LayoutInflaterBindingAdapter;
import com.wada811.ghblog.viewmodel.ArticleListItemViewModel;

public class ArticleListItemBindingAdapter
    extends LayoutInflaterBindingAdapter<ListItemArticleBinding, ArticleListItemViewModel> {

    public ArticleListItemBindingAdapter(LayoutInflater inflater, int layoutId, ViewGroup parent){
        super(inflater, layoutId, parent);
    }
}
