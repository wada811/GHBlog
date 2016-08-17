package com.wada811.view.binding;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.wada811.ghblog.databinding.ListItemRepositoryBinding;
import com.wada811.ghblog.view.binding.LayoutInflaterBindingAdapter;
import com.wada811.ghblog.viewmodel.RepositoryListItemViewModel;
import org.jetbrains.annotations.NotNull;

public class RepositoryListItemBindingAdapter
    extends LayoutInflaterBindingAdapter<ListItemRepositoryBinding, RepositoryListItemViewModel> {

    public RepositoryListItemBindingAdapter(@NotNull LayoutInflater layoutInflater, int layoutId, @NotNull ViewGroup viewGroup){
        super(layoutInflater, layoutId, viewGroup);
    }
}
