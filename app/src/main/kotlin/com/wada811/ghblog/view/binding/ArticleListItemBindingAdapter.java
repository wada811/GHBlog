package com.wada811.ghblog.view.binding;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.wada811.ghblog.databinding.ListItemArticleBinding;
import com.wada811.ghblog.viewmodel.ArticleListItemViewModel;

public class ArticleListItemBindingAdapter {

    private final ListItemArticleBinding binding;

    public ArticleListItemBindingAdapter(LayoutInflater inflater, int layoutId, ViewGroup parent){
        binding = DataBindingUtil.inflate(inflater, layoutId, parent, false);
    }

    public void setViewModel(ArticleListItemViewModel viewModel){
        binding.setViewModel(viewModel);
    }

    public ArticleListItemViewModel getViewModel(){
        return binding.getViewModel();
    }

    public View getRoot(){
        return binding.getRoot();
    }
}
