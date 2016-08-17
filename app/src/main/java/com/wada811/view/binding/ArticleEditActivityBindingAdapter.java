package com.wada811.view.binding;

import android.app.Activity;
import com.wada811.ghblog.databinding.ActivityArticleEditBinding;
import com.wada811.ghblog.view.binding.ActivityBindingAdapter;
import com.wada811.ghblog.viewmodel.ArticleEditViewModel;
import org.jetbrains.annotations.NotNull;

public class ArticleEditActivityBindingAdapter extends ActivityBindingAdapter<ActivityArticleEditBinding, ArticleEditViewModel> {

    public ArticleEditActivityBindingAdapter(@NotNull Activity activity, int layoutId, @NotNull ArticleEditViewModel viewModel){
        super(activity, layoutId, viewModel);
    }
}
