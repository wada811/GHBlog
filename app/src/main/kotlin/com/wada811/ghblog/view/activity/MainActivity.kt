package com.wada811.ghblog.view.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.wada811.ghblog.R
import com.wada811.ghblog.view.activity.extensions.addTo
import com.wada811.ghblog.viewmodel.MainViewModel
import com.wada811.rxviewmodel.RxMessenger
import com.wada811.view.binding.MainActivityBindingAdapter
import rx.functions.Action1
import rx.subscriptions.CompositeSubscription

class MainActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context) = Intent(context, MainActivity::class.java)
    }

    val subscriptions = CompositeSubscription()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscriptions.add(RxMessenger.observe(OAuthAction::class.java).onBackpressureDrop().subscribe { it.call(this) })
        subscriptions.add(RxMessenger.observe(NextAction::class.java).onBackpressureDrop().subscribe { it.call(this) })
        MainActivityBindingAdapter(this, R.layout.activity_main, MainViewModel()).addTo(subscriptions)
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
