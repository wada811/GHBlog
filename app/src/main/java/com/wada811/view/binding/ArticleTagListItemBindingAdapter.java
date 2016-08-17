package com.wada811.view.binding;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.wada811.ghblog.databinding.ListItemTagBinding;
import com.wada811.ghblog.view.binding.LayoutInflaterBindingAdapter;
import com.wada811.ghblog.viewmodel.ArticleTagListItemViewModel;
import org.jetbrains.annotations.NotNull;

public class ArticleTagListItemBindingAdapter
    extends LayoutInflaterBindingAdapter<ListItemTagBinding, ArticleTagListItemViewModel> {

    public ArticleTagListItemBindingAdapter(@NotNull LayoutInflater layoutInflater, int layoutId, @NotNull ViewGroup viewGroup){
        super(layoutInflater, layoutId, viewGroup);
    }
}
