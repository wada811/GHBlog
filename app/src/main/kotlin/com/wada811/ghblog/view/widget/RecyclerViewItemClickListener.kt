package com.wada811.ghblog.view.widget

import android.support.v7.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent

class RecyclerViewItemClickListener(recyclerView: RecyclerView, private val listener: OnItemClickListener)
: RecyclerView.SimpleOnItemTouchListener() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    private val gestureDetector = GestureDetector(recyclerView.context, object : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent): Boolean = true
    })

    override fun onInterceptTouchEvent(recyclerView: RecyclerView, e: MotionEvent): Boolean {
        val childView = recyclerView.findChildViewUnder(e.x, e.y)
        if (childView != null && gestureDetector.onTouchEvent(e)) {
            val position = recyclerView.getChildAdapterPosition(childView)
            listener.onItemClick(position)
        }
        return false
    }
}