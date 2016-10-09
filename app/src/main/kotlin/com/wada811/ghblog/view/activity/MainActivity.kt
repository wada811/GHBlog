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

    private val subscriptions = CompositeSubscription()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RxMessenger.observe(OAuthAction::class.java).onBackpressureDrop().subscribe { it.call(this) }.addTo(subscriptions)
        RxMessenger.observe(SelectRepositoryAction::class.java).onBackpressureDrop().subscribe { it.call(this) }.addTo(subscriptions)
        RxMessenger.observe(ShowArticleListAction::class.java).onBackpressureDrop().subscribe { it.call(this) }.addTo(subscriptions)
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

    class SelectRepositoryAction : Action1<Activity> {
        override fun call(activity: Activity) {
            activity.startActivity(BlogSettingsActivity.createIntent(activity))
            activity.finish()
        }
    }

    class ShowArticleListAction : Action1<Activity> {
        override fun call(activity: Activity) {
            activity.startActivity(ArticleListActivity.createIntent(activity))
            activity.finish()
        }
    }
}
