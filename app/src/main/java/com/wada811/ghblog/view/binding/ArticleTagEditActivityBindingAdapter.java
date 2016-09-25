package com.wada811.ghblog.view.binding;

import android.app.Activity;
import com.wada811.ghblog.databinding.ActivityArticleTagEditBinding;
import com.wada811.ghblog.viewmodel.ArticleTagEditViewModel;
import org.jetbrains.annotations.NotNull;

public class ArticleTagEditActivityBindingAdapter
    extends ActivityBindingAdapter<ActivityArticleTagEditBinding, ArticleTagEditViewModel> {

    public ArticleTagEditActivityBindingAdapter(@NotNull Activity activity, int layoutId, @NotNull ArticleTagEditViewModel viewModel){
        super(activity, layoutId, viewModel);
    }
}
