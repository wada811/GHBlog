package com.wada811.ghblog.view.helper;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;

public class RecyclerViewListenerBindingHelper {

    @BindingAdapter("onItemClick")
    public static void setOnItemClickListener(final RecyclerView recyclerView, final OnItemClickListener listener){
        RecyclerViewItemClickListener itemClickListener = new RecyclerViewItemClickListener(recyclerView, listener);
        recyclerView.addOnItemTouchListener(itemClickListener);
    }

    public interface OnItemClickListener {

        void onItemClick(int position);
    }

    private static class RecyclerViewItemClickListener extends RecyclerView.SimpleOnItemTouchListener {

        private OnItemClickListener listener;
        private GestureDetector gestureDetector;

        RecyclerViewItemClickListener(RecyclerView recyclerView, OnItemClickListener listener){
            this.listener = listener;
            gestureDetector = new GestureDetector(recyclerView.getContext(), new SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e){
                    return true;
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent e){
            View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if(childView != null && listener != null && gestureDetector.onTouchEvent(e)){
                int position = recyclerView.getChildAdapterPosition(childView);
                listener.onItemClick(position);
            }
            return false;
        }
    }
}
