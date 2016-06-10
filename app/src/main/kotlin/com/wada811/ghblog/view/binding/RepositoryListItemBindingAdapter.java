package com.wada811.ghblog.view.binding;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.wada811.ghblog.databinding.ListItemRepositoryBinding;
import com.wada811.ghblog.viewmodel.RepositoryListItemViewModel;

public class RepositoryListItemBindingAdapter {

    private final ListItemRepositoryBinding binding;

    public RepositoryListItemBindingAdapter(LayoutInflater inflater, int layoutId, ViewGroup parent){
        binding = DataBindingUtil.inflate(inflater, layoutId, parent, false);
    }

    public void setViewModel(RepositoryListItemViewModel viewModel){
        binding.setViewModel(viewModel);
    }

    public RepositoryListItemViewModel getViewModel(){
        return binding.getViewModel();
    }

    public View getRoot(){
        return binding.getRoot();
    }
}
