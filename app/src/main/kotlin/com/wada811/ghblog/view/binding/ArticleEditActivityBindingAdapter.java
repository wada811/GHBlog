package com.wada811.ghblog.view.binding;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import com.wada811.ghblog.databinding.ActivityArticleEditBinding;
import com.wada811.ghblog.viewmodel.ArticleEditViewModel;

public class ArticleEditActivityBindingAdapter {

    private final ActivityArticleEditBinding binding;

    public ArticleEditActivityBindingAdapter(Activity activity, int layoutId){
        binding = DataBindingUtil.setContentView(activity, layoutId);
    }

    public void setViewModel(ArticleEditViewModel viewModel){
        binding.setViewModel(viewModel);
    }

    public ArticleEditViewModel getViewModel(){
        return binding.getViewModel();
    }
}
