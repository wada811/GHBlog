package com.wada811.ghblog.view.binding;

import android.app.Activity;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.wada811.ghblog.R;
import com.wada811.ghblog.databinding.ActivityArticleListBinding;
import com.wada811.ghblog.view.adapter.ArticleListAdapter;
import com.wada811.ghblog.viewmodel.ArticleListItemViewModel;
import com.wada811.ghblog.viewmodel.ArticleListViewModel;
import java.util.List;

public class ArticleListActivityBindingAdapter {

    private final ActivityArticleListBinding binding;

    public ArticleListActivityBindingAdapter(Activity activity, int layoutId){
        binding = DataBindingUtil.setContentView(activity, layoutId);
    }

    public void setViewModel(ArticleListViewModel viewModel){
        binding.setViewModel(viewModel);
    }

    public ArticleListViewModel getViewModel(){
        return binding.getViewModel();
    }

    @BindingAdapter("items")
    public static void setItems(ListView listView, List<ArticleListItemViewModel> items){
        ArrayAdapter adapter = (ArrayAdapter)listView.getAdapter();
        int position = listView.getVerticalScrollbarPosition();
        if(adapter == null){
            listView.setAdapter(new ArticleListAdapter(listView.getContext(), R.layout.list_item_article, items));
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
