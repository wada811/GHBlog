package com.wada811.ghblog.view.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.wada811.ghblog.R
import com.wada811.ghblog.view.binding.RepositoryListActivityBindingAdapter
import com.wada811.ghblog.viewmodel.RepositoryListViewModel
import com.wada811.rxviewmodel.RxMessenger
import rx.functions.Action1
import rx.subscriptions.CompositeSubscription

class RepositoryListActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context) = Intent(context, RepositoryListActivity::class.java)
    }

    lateinit var binding: RepositoryListActivityBindingAdapter
    val subscriptions = CompositeSubscription()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RepositoryListActivityBindingAdapter(this, R.layout.activity_repository_list)
        binding.viewModel = RepositoryListViewModel()
        subscriptions.add(RxMessenger.observe(NextAction::class.java).onBackpressureDrop().subscribe { it.call(this) })
    }

    override fun onDestroy() {
        subscriptions.unsubscribe()
        binding.viewModel.unsubscribe()
        super.onDestroy()
    }

    class NextAction : Action1<Activity> {
        override fun call(activity: Activity) {
            activity.startActivity(ArticleListActivity.createIntent(activity))
        }
    }
}
