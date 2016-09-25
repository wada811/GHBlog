package com.wada811.ghblog.view.binding;

import android.app.Activity;
import com.wada811.ghblog.databinding.ActivityArticleListBinding;
import com.wada811.ghblog.viewmodel.ArticleListViewModel;
import org.jetbrains.annotations.NotNull;

public class ArticleListActivityBindingAdapter
    extends ActivityBindingAdapter<ActivityArticleListBinding, ArticleListViewModel> {

    public ArticleListActivityBindingAdapter(@NotNull Activity activity, int layoutId, @NotNull ArticleListViewModel viewModel){
        super(activity, layoutId, viewModel);
    }
}
