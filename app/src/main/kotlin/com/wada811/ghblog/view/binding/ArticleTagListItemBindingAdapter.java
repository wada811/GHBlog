package com.wada811.ghblog.view.binding;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.wada811.ghblog.databinding.ListItemTagBinding;
import com.wada811.ghblog.viewmodel.ArticleTagListItemViewModel;

public class ArticleTagListItemBindingAdapter {

    private final ListItemTagBinding binding;

    public ArticleTagListItemBindingAdapter(LayoutInflater inflater, int layoutId, ViewGroup parent){
        binding = DataBindingUtil.inflate(inflater, layoutId, parent, false);
    }

    public void setViewModel(ArticleTagListItemViewModel viewModel){
        binding.setViewModel(viewModel);
    }

    public ArticleTagListItemViewModel getViewModel(){
        return binding.getViewModel();
    }

    public View getRoot(){
        return binding.getRoot();
    }
}
