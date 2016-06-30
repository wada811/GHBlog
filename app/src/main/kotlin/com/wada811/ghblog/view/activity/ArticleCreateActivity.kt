package com.wada811.ghblog.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.wada811.ghblog.R
import com.wada811.ghblog.view.binding.ArticleCreateActivityBindingAdapter
import com.wada811.ghblog.viewmodel.ArticleCreateViewModel
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
    }

    override fun onDestroy() {
        subscriptions.unsubscribe()
        binding.viewModel.unsubscribe()
        super.onDestroy()
    }
}