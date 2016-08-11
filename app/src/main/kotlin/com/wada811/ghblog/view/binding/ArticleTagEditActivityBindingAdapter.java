package com.wada811.ghblog.view.binding;

import android.app.Activity;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.wada811.ghblog.R;
import com.wada811.ghblog.databinding.ActivityArticleTagEditBinding;
import com.wada811.ghblog.util.Keyboard;
import com.wada811.ghblog.view.adapter.ArticleTagListAdapter;
import com.wada811.ghblog.viewmodel.ArticleTagEditViewModel;
import com.wada811.ghblog.viewmodel.ArticleTagListItemViewModel;
import java.util.List;
import rx.functions.Action0;

public class ArticleTagEditActivityBindingAdapter {

    private final ActivityArticleTagEditBinding binding;

    public ArticleTagEditActivityBindingAdapter(Activity activity, int layoutId){
        binding = DataBindingUtil.setContentView(activity, layoutId);
    }

    public void setViewModel(ArticleTagEditViewModel viewModel){
        binding.setViewModel(viewModel);
    }

    public ArticleTagEditViewModel getViewModel(){
        return binding.getViewModel();
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
    public static void setItems(ListView listView, List<ArticleTagListItemViewModel> items){
        ArrayAdapter adapter = (ArrayAdapter)listView.getAdapter();
        int position = listView.getVerticalScrollbarPosition();
        if(adapter == null){
            listView.setAdapter(new ArticleTagListAdapter(listView.getContext(), R.layout.list_item_tag, items));
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
