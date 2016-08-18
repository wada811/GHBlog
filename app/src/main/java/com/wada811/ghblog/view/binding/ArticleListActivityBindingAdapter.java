package com.wada811.ghblog.view.binding;

import android.app.Activity;
import android.databinding.BindingAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.wada811.ghblog.R;
import com.wada811.ghblog.databinding.ActivityArticleListBinding;
import com.wada811.ghblog.view.adapter.ArticleListAdapter;
import com.wada811.ghblog.viewmodel.ArticleListItemViewModel;
import com.wada811.ghblog.viewmodel.ArticleListViewModel;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class ArticleListActivityBindingAdapter
    extends ActivityBindingAdapter<ActivityArticleListBinding, ArticleListViewModel> {

    public ArticleListActivityBindingAdapter(@NotNull Activity activity, int layoutId, @NotNull ArticleListViewModel viewModel){
        super(activity, layoutId, viewModel);
    }

    @BindingAdapter("items")
    public static void setItems(RecyclerView recyclerView, List<ArticleListItemViewModel> items){
        ArticleListAdapter adapter = (ArticleListAdapter)recyclerView.getAdapter();
        int position = recyclerView.getVerticalScrollbarPosition();
        if(adapter == null){
            adapter = new ArticleListAdapter(recyclerView.getContext(), R.layout.list_item_article, items);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        }else{
            adapter.notifyDataSetChanged();
            recyclerView.setVerticalScrollbarPosition(position);
        }
    }
}
