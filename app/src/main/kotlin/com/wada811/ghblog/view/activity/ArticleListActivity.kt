package com.wada811.ghblog.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.wada811.ghblog.R
import com.wada811.ghblog.view.binding.ArticleListActivityBindingAdapter
import com.wada811.ghblog.viewmodel.ArticleListViewModel

class ArticleListActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context) = Intent(context, ArticleListActivity::class.java)
    }
    lateinit var binding: ArticleListActivityBindingAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ArticleListActivityBindingAdapter(this, R.layout.activity_article_list)
        binding.viewModel = ArticleListViewModel()
    }

    override fun onDestroy() {
        binding.viewModel.unsubscribe()
        super.onDestroy()
    }
}