package com.wada811.ghblog.view.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.wada811.ghblog.R
import com.wada811.ghblog.view.activity.extensions.addTo
import com.wada811.ghblog.viewmodel.RepositoryListViewModel
import com.wada811.rxviewmodel.RxMessenger
import com.wada811.view.binding.RepositoryListActivityBindingAdapter
import rx.functions.Action1
import rx.subscriptions.CompositeSubscription

class RepositoryListActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context) = Intent(context, RepositoryListActivity::class.java)
    }

    val subscriptions = CompositeSubscription()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RepositoryListActivityBindingAdapter(this, R.layout.activity_repository_list, RepositoryListViewModel()).addTo(subscriptions)
        subscriptions.add(RxMessenger.observe(NextAction::class.java).onBackpressureDrop().subscribe { it.call(this) })
    }

    override fun onDestroy() {
        subscriptions.unsubscribe()
        super.onDestroy()
    }

    class NextAction : Action1<Activity> {
        override fun call(activity: Activity) {
            activity.startActivity(ArticleListActivity.createIntent(activity))
        }
    }
}
