package com.wada811.ghblog.view.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.wada811.ghblog.R
import com.wada811.ghblog.view.activity.extensions.addTo
import com.wada811.ghblog.view.binding.ArticleEditActivityBindingAdapter
import com.wada811.ghblog.viewmodel.ArticleEditViewModel
import com.wada811.rxviewmodel.RxMessenger
import rx.functions.Action1
import rx.subscriptions.CompositeSubscription

class ArticleEditActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context) = Intent(context, ArticleEditActivity::class.java)
    }

    private val subscriptions = CompositeSubscription()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ArticleEditActivityBindingAdapter(this, R.layout.activity_article_edit, ArticleEditViewModel()).addTo(subscriptions)
        RxMessenger.observe(PreviewAction::class.java).onBackpressureDrop().subscribe { it.call(this) }.addTo(subscriptions)
        RxMessenger.observe(TagEditAction::class.java).onBackpressureDrop().subscribe { it.call(this) }.addTo(subscriptions)
        RxMessenger.observe(SaveAction::class.java).onBackpressureDrop().subscribe { it.call(this) }.addTo(subscriptions)
    }

    override fun onDestroy() {
        subscriptions.unsubscribe()
        super.onDestroy()
    }

    class PreviewAction : Action1<Activity> {
        override fun call(activity: Activity) {
        }
    }

    class TagEditAction : Action1<Activity> {
        override fun call(activity: Activity) {
            activity.startActivity(ArticleTagEditActivity.createIntent(activity))
        }
    }

    class SaveAction : Action1<Activity> {
        override fun call(activity: Activity) {
            activity.finish()
        }
    }
}
