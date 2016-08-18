package com.wada811.ghblog.view.binding;

import android.app.Activity;
import android.databinding.BindingAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.wada811.ghblog.R;
import com.wada811.ghblog.databinding.ActivityArticleTagEditBinding;
import com.wada811.ghblog.util.Keyboard;
import com.wada811.ghblog.view.adapter.ArticleTagListAdapter;
import com.wada811.ghblog.viewmodel.ArticleTagEditViewModel;
import com.wada811.ghblog.viewmodel.ArticleTagListItemViewModel;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import rx.functions.Action0;

public class ArticleTagEditActivityBindingAdapter
    extends ActivityBindingAdapter<ActivityArticleTagEditBinding, ArticleTagEditViewModel> {

    public ArticleTagEditActivityBindingAdapter(@NotNull Activity activity, int layoutId, @NotNull ArticleTagEditViewModel viewModel){
        super(activity, layoutId, viewModel);
    }

    @BindingAdapter("onEditDone")
    public static void setOnEditDone(final EditText editText, final Action0 doneAction){
        editText.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent){
                boolean handled = false;
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    Keyboard.INSTANCE.close(editText);
                    doneAction.call();
                    handled = true;
                }
                return handled;
            }
        });
    }

    @BindingAdapter("items")
    public static void setItems(RecyclerView recyclerView, List<ArticleTagListItemViewModel> items){
        ArticleTagListAdapter adapter = (ArticleTagListAdapter)recyclerView.getAdapter();
        int position = recyclerView.getVerticalScrollbarPosition();
        if(adapter == null){
            adapter = new ArticleTagListAdapter(recyclerView.getContext(), R.layout.list_item_tag, items);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        }else{
            adapter.notifyDataSetChanged();
            recyclerView.setVerticalScrollbarPosition(position);
        }
    }
}
