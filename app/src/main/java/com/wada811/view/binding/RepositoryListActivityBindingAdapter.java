package com.wada811.view.binding;

import android.app.Activity;
import android.databinding.BindingAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.wada811.ghblog.R;
import com.wada811.ghblog.databinding.ActivityRepositoryListBinding;
import com.wada811.ghblog.view.adapter.RepositoryListAdapter;
import com.wada811.ghblog.view.binding.ActivityBindingAdapter;
import com.wada811.ghblog.viewmodel.RepositoryListItemViewModel;
import com.wada811.ghblog.viewmodel.RepositoryListViewModel;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class RepositoryListActivityBindingAdapter
    extends ActivityBindingAdapter<ActivityRepositoryListBinding, RepositoryListViewModel> {

    public RepositoryListActivityBindingAdapter(@NotNull Activity activity, int layoutId, @NotNull RepositoryListViewModel viewModel){
        super(activity, layoutId, viewModel);
    }

    @BindingAdapter("items")
    public static void setItems(ListView listView, List<RepositoryListItemViewModel> items){
        ArrayAdapter adapter = (ArrayAdapter)listView.getAdapter();
        int position = listView.getVerticalScrollbarPosition();
        if(adapter == null){
            listView.setAdapter(new RepositoryListAdapter(listView.getContext(), R.layout.list_item_repository, items));
        }else{
            adapter.notifyDataSetChanged();
            listView.setVerticalScrollbarPosition(position);
        }
    }

    @BindingAdapter("onItemClick")
    public static void setOnItemClickListener(ListView listView, OnItemClickListener listener){
        listView.setOnItemClickListener(listener);
    }
}
