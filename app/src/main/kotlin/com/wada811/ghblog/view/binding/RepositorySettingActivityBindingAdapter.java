package com.wada811.ghblog.view.binding;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import com.wada811.ghblog.databinding.ActivityRepositorySettingBinding;
import com.wada811.ghblog.viewmodel.RepositorySettingViewModel;

public class RepositorySettingActivityBindingAdapter {

    private final ActivityRepositorySettingBinding binding;

    public RepositorySettingActivityBindingAdapter(Activity activity, int layoutId){
        binding = DataBindingUtil.setContentView(activity, layoutId);
    }

    public void setViewModel(RepositorySettingViewModel viewModel){
        binding.setViewModel(viewModel);
    }

    public RepositorySettingViewModel getViewModel(){
        return binding.getViewModel();
    }

}
