package com.wada811.ghblog.view.binding;

import android.app.Activity;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.widget.Spinner;
import com.wada811.ghblog.R;
import com.wada811.ghblog.databinding.ActivityBlogSettingsBinding;
import com.wada811.ghblog.databinding.ListItemRepositoryBinding;
import com.wada811.ghblog.viewmodel.BlogSettingsViewModel;
import com.wada811.ghblog.viewmodel.RepositoryListItemViewModel;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class BlogSettingsActivityBindingAdapter
    extends ActivityBindingAdapter<ActivityBlogSettingsBinding, BlogSettingsViewModel> {

    public BlogSettingsActivityBindingAdapter(@NotNull Activity activity, int layoutId, @NotNull BlogSettingsViewModel viewModel){
        super(activity, layoutId, viewModel);
    }

    private static class RepositorySpinnerAdapter
        extends SpinnerBindingAdapter<ListItemRepositoryBinding, RepositoryListItemViewModel> {

        RepositorySpinnerAdapter(@NotNull Context context, int layoutId, @NotNull List<RepositoryListItemViewModel> items){
            super(context, layoutId, items);
        }
    }

    @BindingAdapter("items")
    public static void setItems(Spinner spinner, List<RepositoryListItemViewModel> items){
        RepositorySpinnerAdapter adapter = (RepositorySpinnerAdapter)spinner.getAdapter();
        if(adapter == null){
            adapter = new RepositorySpinnerAdapter(spinner.getContext(), R.layout.list_item_repository, items);
            spinner.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }
    }
}
