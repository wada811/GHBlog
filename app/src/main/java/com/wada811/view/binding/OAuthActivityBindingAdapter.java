package com.wada811.view.binding;

import android.app.Activity;
import com.wada811.ghblog.databinding.ActivityOauthBinding;
import com.wada811.ghblog.view.binding.ActivityBindingAdapter;
import com.wada811.ghblog.viewmodel.OAuthViewModel;
import org.jetbrains.annotations.NotNull;

public class OAuthActivityBindingAdapter extends ActivityBindingAdapter<ActivityOauthBinding, OAuthViewModel> {

    public OAuthActivityBindingAdapter(@NotNull Activity activity, int layoutId, @NotNull OAuthViewModel viewModel){
        super(activity, layoutId, viewModel);
    }
}
