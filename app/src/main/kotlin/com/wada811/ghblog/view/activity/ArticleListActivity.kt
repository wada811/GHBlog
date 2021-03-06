package com.wada811.ghblog.view.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.wada811.ghblog.R
import com.wada811.ghblog.view.activity.extensions.addTo
import com.wada811.ghblog.view.binding.ArticleListActivityBindingAdapter
import com.wada811.ghblog.viewmodel.ArticleListViewModel
import com.wada811.rxviewmodel.RxMessenger
import rx.functions.Action1
import rx.subscriptions.CompositeSubscription

class ArticleListActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context) = Intent(context, ArticleListActivity::class.java)
    }

    private var subscriptions = CompositeSubscription()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ArticleListActivityBindingAdapter(this, R.layout.activity_article_list, ArticleListViewModel()).addTo(subscriptions)
        RxMessenger.observe(CreateAction::class.java).onBackpressureDrop().subscribe { it.call(this) }.addTo(subscriptions)
        RxMessenger.observe(EditAction::class.java).onBackpressureDrop().subscribe { it.call(this) }.addTo(subscriptions)
    }

    override fun onDestroy() {
        subscriptions.unsubscribe()
        super.onDestroy()
    }

    class CreateAction : Action1<Activity> {
        override fun call(activity: Activity) {
            activity.startActivity(ArticleCreateActivity.createIntent(activity))
        }
    }

    class EditAction : Action1<Activity> {
        override fun call(activity: Activity) {
            activity.startActivity(ArticleEditActivity.createIntent(activity))
        }
    }
}
