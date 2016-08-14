package com.wada811.ghblog.view.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.wada811.ghblog.R
import com.wada811.ghblog.view.binding.ArticleCreateActivityBindingAdapter
import com.wada811.ghblog.viewmodel.ArticleCreateViewModel
import com.wada811.rxviewmodel.RxMessenger
import rx.functions.Action1
import rx.subscriptions.CompositeSubscription

class ArticleCreateActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context) = Intent(context, ArticleCreateActivity::class.java)
    }

    lateinit var binding: ArticleCreateActivityBindingAdapter
    val subscriptions = CompositeSubscription()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ArticleCreateActivityBindingAdapter(this, R.layout.activity_article_create)
        binding.viewModel = ArticleCreateViewModel()
        subscriptions.add(RxMessenger.observe(TagEditAction::class.java).onBackpressureDrop().subscribe { it.call(this) })
        subscriptions.add(RxMessenger.observe(SaveAction::class.java).onBackpressureDrop().subscribe { it.call(this) })
    }

    override fun onDestroy() {
        subscriptions.unsubscribe()
        binding.viewModel.unsubscribe()
        super.onDestroy()
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
