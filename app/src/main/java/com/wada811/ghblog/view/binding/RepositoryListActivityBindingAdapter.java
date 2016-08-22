package com.wada811.ghblog.view.binding;

import android.app.Activity;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.wada811.ghblog.R;
import com.wada811.ghblog.databinding.ActivityRepositoryListBinding;
import com.wada811.ghblog.databinding.ListItemRepositoryBinding;
import com.wada811.ghblog.viewmodel.RepositoryListItemViewModel;
import com.wada811.ghblog.viewmodel.RepositoryListViewModel;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class RepositoryListActivityBindingAdapter
    extends ActivityBindingAdapter<ActivityRepositoryListBinding, RepositoryListViewModel> {

    public RepositoryListActivityBindingAdapter(@NotNull Activity activity, int layoutId, @NotNull RepositoryListViewModel viewModel){
        super(activity, layoutId, viewModel);
    }

    private static class RepositoryListAdapter
        extends RecyclerViewBindingAdapter<ListItemRepositoryBinding, RepositoryListItemViewModel> {

        RepositoryListAdapter(@NotNull Context context, int layoutId, @NotNull List<RepositoryListItemViewModel> items){
            super(context, layoutId, items);
        }
    }

    @BindingAdapter("items")
    public static void setItems(RecyclerView recyclerView, List<RepositoryListItemViewModel> items){
        RepositoryListAdapter adapter = (RepositoryListAdapter)recyclerView.getAdapter();
        int position = recyclerView.getVerticalScrollbarPosition();
        if(adapter == null){
            adapter = new RepositoryListAdapter(recyclerView.getContext(), R.layout.list_item_repository, items);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        }else{
            adapter.notifyDataSetChanged();
            recyclerView.setVerticalScrollbarPosition(position);
        }
    }
}
