package com.wada811.ghblog.view.binding;

import android.app.Activity;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.wada811.ghblog.R;
import com.wada811.ghblog.databinding.ActivityRepositoryListBinding;
import com.wada811.ghblog.view.adapter.RepositoryListAdapter;
import com.wada811.ghblog.viewmodel.RepositoryListItemViewModel;
import com.wada811.ghblog.viewmodel.RepositoryListViewModel;
import java.util.List;

public class RepositoryListActivityBindingAdapter {

    private final ActivityRepositoryListBinding binding;

    public RepositoryListActivityBindingAdapter(Activity activity, int layoutId){
        binding = DataBindingUtil.setContentView(activity, layoutId);
    }

    public void setViewModel(RepositoryListViewModel viewModel){
        binding.setViewModel(viewModel);
    }

    public RepositoryListViewModel getViewModel(){
        return binding.getViewModel();
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
