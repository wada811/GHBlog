package com.wada811.ghblog.view.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.wada811.ghblog.R
import com.wada811.ghblog.view.activity.extensions.addTo
import com.wada811.ghblog.view.binding.BlogSettingsActivityBindingAdapter
import com.wada811.ghblog.viewmodel.BlogSettingsViewModel
import com.wada811.rxviewmodel.RxMessenger
import rx.functions.Action1
import rx.subscriptions.CompositeSubscription

class BlogSettingsActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context) = Intent(context, BlogSettingsActivity::class.java)
    }

    val subscriptions = CompositeSubscription()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BlogSettingsActivityBindingAdapter(this, R.layout.activity_blog_settings, BlogSettingsViewModel()).addTo(subscriptions)
        RxMessenger.observe(BackAction::class.java).onBackpressureDrop().subscribe { it.call(this) }.addTo(subscriptions)
        RxMessenger.observe(SaveAction::class.java).onBackpressureDrop().subscribe { it.call(this) }.addTo(subscriptions)
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

    class SaveAction : Action1<Activity> {
        override fun call(activity: Activity) {
            activity.startActivity(ArticleListActivity.createIntent(activity))
            activity.finish()
        }
    }
}