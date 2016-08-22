package com.wada811.ghblog.view.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.wada811.ghblog.R
import com.wada811.ghblog.view.activity.extensions.addTo
import com.wada811.ghblog.view.binding.RepositoryListActivityBindingAdapter
import com.wada811.ghblog.viewmodel.RepositoryListViewModel
import com.wada811.rxviewmodel.RxMessenger
import rx.functions.Action1
import rx.subscriptions.CompositeSubscription

class RepositoryListActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context) = Intent(context, RepositoryListActivity::class.java)
    }

    private val subscriptions = CompositeSubscription()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RepositoryListActivityBindingAdapter(this, R.layout.activity_repository_list, RepositoryListViewModel()).addTo(subscriptions)
        RxMessenger.observe(BackAction::class.java).onBackpressureDrop().subscribe { it.call(this) }.addTo(subscriptions)
        RxMessenger.observe(NextAction::class.java).onBackpressureDrop().subscribe { it.call(this) }.addTo(subscriptions)
    }

    override fun onDestroy() {
        subscriptions.unsubscribe()
        super.onDestroy()
    }

    class BackAction : Action1<Activity> {
        override fun call(activity: Activity) {
            activity.finish()
        }
    }

    class NextAction : Action1<Activity> {
        override fun call(activity: Activity) {
            activity.startActivity(ArticleListActivity.createIntent(activity))
        }
    }
}
