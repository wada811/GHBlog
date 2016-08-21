package com.wada811.ghblog.view.helper;

import android.databinding.BindingAdapter;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;

public class RecyclerViewDividerBindingHelper {

    @BindingAdapter("dividerDrawable")
    public static void setDividerDrawable(final RecyclerView recyclerView, final Drawable drawable){
        recyclerView.addItemDecoration(new ItemDividerDecoration(drawable));
    }

    private static class ItemDividerDecoration extends RecyclerView.ItemDecoration {

        private Drawable divider;

        ItemDividerDecoration(Drawable divider){
            this.divider = divider;
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, State state){
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            int childCount = parent.getChildCount();
            for(int i = 0; i < childCount - 1; i++){
                View childView = parent.getChildAt(i);
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)childView.getLayoutParams();
                int top = childView.getBottom() + params.bottomMargin;
                int bottom = top + divider.getIntrinsicHeight();
                divider.setBounds(left, top, right, bottom);
                divider.draw(c);
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state){
            outRect.set(0, 0, 0, divider.getIntrinsicHeight());
        }
    }
}
