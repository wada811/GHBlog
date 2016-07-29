package com.wada811.ghblog.view.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.wada811.ghblog.R
import com.wada811.ghblog.view.activity.extensions.addTo
import com.wada811.ghblog.view.binding.MainActivityBindingAdapter
import com.wada811.ghblog.viewmodel.MainViewModel
import com.wada811.rxviewmodel.RxMessenger
import rx.functions.Action1
import rx.subscriptions.CompositeSubscription

class MainActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context) = Intent(context, MainActivity::class.java)
    }

    lateinit var binding: MainActivityBindingAdapter
    val subscriptions = CompositeSubscription()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscriptions.add(RxMessenger.observe(OAuthAction::class.java).onBackpressureDrop().subscribe { it.call(this) })
        subscriptions.add(RxMessenger.observe(NextAction::class.java).onBackpressureDrop().subscribe { it.call(this) })
        binding = MainActivityBindingAdapter(this, R.layout.activity_main)
        binding.viewModel = MainViewModel().addTo(subscriptions)
    }

    override fun onDestroy() {
        subscriptions.unsubscribe()
        super.onDestroy()
    }

    class OAuthAction : Action1<Activity> {
        override fun call(activity: Activity) {
            activity.startActivity(OAuthActivity.createIntent(activity))
            activity.finish()
        }
    }

    class NextAction : Action1<Activity> {
        override fun call(activity: Activity) {
            activity.startActivity(RepositoryListActivity.createIntent(activity))
            activity.finish()
        }
    }
}
