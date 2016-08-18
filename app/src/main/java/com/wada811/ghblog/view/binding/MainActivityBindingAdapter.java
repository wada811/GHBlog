package com.wada811.ghblog.view.binding;

import android.app.Activity;
import com.wada811.ghblog.databinding.ActivityMainBinding;
import com.wada811.ghblog.viewmodel.MainViewModel;
import org.jetbrains.annotations.NotNull;

public class MainActivityBindingAdapter extends ActivityBindingAdapter<ActivityMainBinding, MainViewModel> {

    public MainActivityBindingAdapter(@NotNull Activity activity, int layoutId, @NotNull MainViewModel viewModel){
        super(activity, layoutId, viewModel);
    }
}
