package com.wada811.ghblog.view.binding;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import com.wada811.ghblog.databinding.ActivityOauthBinding;
import com.wada811.ghblog.viewmodel.OAuthViewModel;

public class OAuthActivityBindingAdapter {

    private final ActivityOauthBinding binding;

    public OAuthActivityBindingAdapter(Activity activity, int layoutId){
        binding = DataBindingUtil.setContentView(activity, layoutId);
    }

    public void setViewModel(OAuthViewModel viewModel){
        binding.setViewModel(viewModel);
    }

    public OAuthViewModel getViewModel(){
        return binding.getViewModel();
    }
}
