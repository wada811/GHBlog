package com.wada811.ghblog.view.activity

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.wada811.ghblog.R
import com.wada811.ghblog.view.binding.MainActivityBindingAdapter
import com.wada811.ghblog.viewmodel.MainViewModel
import com.wada811.rxviewmodel.RxMessenger
import rx.functions.Action1
import rx.subscriptions.CompositeSubscription

class MainActivity : AppCompatActivity() {

    lateinit var binding: MainActivityBindingAdapter
    val subscriptions = CompositeSubscription()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBindingAdapter(this, R.layout.activity_main)
        binding.viewModel = MainViewModel()
        subscriptions.add(RxMessenger.observe(NextAction::class.java).onBackpressureDrop().subscribe { it.call(this) })
        startActivity(OAuthActivity.createIntent(this))
    }

    override fun onDestroy() {
        subscriptions.unsubscribe()
        binding.viewModel.unsubscribe()
        super.onDestroy()
    }

    class NextAction : Action1<Activity> {
        override fun call(activity: Activity) {
            activity.startActivity(RepositoryListActivity.createIntent(activity))
            activity.finish()
        }
    }
}
