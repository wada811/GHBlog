package com.wada811.ghblog.view.binding;

import android.app.Activity;
import com.wada811.ghblog.databinding.ActivityArticleCreateBinding;
import com.wada811.ghblog.viewmodel.ArticleCreateViewModel;
import org.jetbrains.annotations.NotNull;

public class ArticleCreateActivityBindingAdapter
    extends ActivityBindingAdapter<ActivityArticleCreateBinding, ArticleCreateViewModel> {

    public ArticleCreateActivityBindingAdapter(@NotNull Activity activity, int layoutId, @NotNull ArticleCreateViewModel viewModel){
        super(activity, layoutId, viewModel);
    }
}
