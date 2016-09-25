package com.wada811.ghblog.view.binding;

import android.app.Activity;
import com.wada811.ghblog.databinding.ActivityRepositoryListBinding;
import com.wada811.ghblog.viewmodel.RepositoryListViewModel;
import org.jetbrains.annotations.NotNull;

public class RepositoryListActivityBindingAdapter
    extends ActivityBindingAdapter<ActivityRepositoryListBinding, RepositoryListViewModel> {

    public RepositoryListActivityBindingAdapter(@NotNull Activity activity, int layoutId, @NotNull RepositoryListViewModel viewModel){
        super(activity, layoutId, viewModel);
    }
}
