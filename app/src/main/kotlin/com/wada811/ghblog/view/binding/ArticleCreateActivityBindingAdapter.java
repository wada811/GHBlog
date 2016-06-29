package com.wada811.ghblog.view.binding;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import com.wada811.ghblog.databinding.ActivityArticleCreateBinding;
import com.wada811.ghblog.viewmodel.ArticleCreateViewModel;

public class ArticleCreateActivityBindingAdapter {

    private final ActivityArticleCreateBinding binding;

    public ArticleCreateActivityBindingAdapter(Activity activity, int layoutId){
        binding = DataBindingUtil.setContentView(activity, layoutId);
    }

    public void setViewModel(ArticleCreateViewModel viewModel){
        binding.setViewModel(viewModel);
    }

    public ArticleCreateViewModel getViewModel(){
        return binding.getViewModel();
    }
}
