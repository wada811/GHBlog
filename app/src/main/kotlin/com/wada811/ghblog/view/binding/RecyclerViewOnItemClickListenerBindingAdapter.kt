package com.wada811.ghblog.view.binding

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import com.wada811.rxviewmodel.RxCommand
import com.wada811.rxviewmodel.bind
import rx.Observable
import rx.subjects.PublishSubject

object RecyclerViewOnItemClickListenerBindingAdapter {

    @BindingAdapter("onItemClick")
    fun RecyclerView.setOnItemClick(command: RxCommand<Int>) = this.itemClickAsObservable().bind(command)

    private fun RecyclerView.itemClickAsObservable(): Observable<Int> {
        val subject = PublishSubject.create<Int>()
        val listener = RecyclerViewItemClickListener(this, object : OnItemClickListener {
            override fun onItemClick(position: Int) = subject.onNext(position)
        })
        return subject.doOnSubscribe { this.addOnItemTouchListener(listener) }.doOnUnsubscribe { this.removeOnItemTouchListener(listener) }
    }

    private interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    private class RecyclerViewItemClickListener(recyclerView: RecyclerView, private val listener: OnItemClickListener) : RecyclerView.SimpleOnItemTouchListener() {
        private val gestureDetector = GestureDetector(recyclerView.context, object : SimpleOnGestureListener() {
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
}
