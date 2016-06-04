package com.wada811.ghblog.view.binding;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import com.wada811.ghblog.databinding.ActivityMainBinding;
import com.wada811.ghblog.viewmodel.MainViewModel;

public class MainActivityBindingAdapter {

    private final ActivityMainBinding binding;

    public MainActivityBindingAdapter(Activity activity, int layoutId){
        binding = DataBindingUtil.setContentView(activity, layoutId);
    }

    public void setViewModel(MainViewModel viewModel){
        binding.setViewModel(viewModel);
    }

    public MainViewModel getViewModel(){
        return binding.getViewModel();
    }

}
