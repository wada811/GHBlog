package com.wada811.ghblog.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.wada811.ghblog.R
import com.wada811.ghblog.view.binding.ArticleCreateActivityBindingAdapter
import com.wada811.ghblog.viewmodel.ArticleCreateViewModel
import com.wada811.rxviewmodel.RxMessenger
import rx.android.schedulers.AndroidSchedulers
import rx.subscriptions.CompositeSubscription
import java.util.concurrent.TimeUnit

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
        subscriptions.add(RxMessenger
            .toObservable()
            .ofType(SaveAction::class.java)
            .throttleFirst(300, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                finish()
            }
        )
    }

    override fun onDestroy() {
        subscriptions.unsubscribe()
        binding.viewModel.unsubscribe()
        super.onDestroy()
    }

    class SaveAction {

    }
}
