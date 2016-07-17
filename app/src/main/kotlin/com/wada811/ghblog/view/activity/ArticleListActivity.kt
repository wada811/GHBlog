package com.wada811.ghblog.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.wada811.ghblog.R
import com.wada811.ghblog.view.binding.ArticleListActivityBindingAdapter
import com.wada811.ghblog.viewmodel.ArticleListViewModel
import com.wada811.rxviewmodel.RxMessenger
import rx.android.schedulers.AndroidSchedulers
import rx.subscriptions.CompositeSubscription
import java.util.concurrent.TimeUnit

class ArticleListActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context) = Intent(context, ArticleListActivity::class.java)
    }

    lateinit var binding: ArticleListActivityBindingAdapter
    var subscriptions = CompositeSubscription()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ArticleListActivityBindingAdapter(this, R.layout.activity_article_list)
        binding.viewModel = ArticleListViewModel()
        subscriptions.add(RxMessenger
            .toObservable()
            .ofType(CreateAction::class.java)
            .throttleFirst(300, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                it.invoke(this)
            }
        )
        subscriptions.add(RxMessenger
            .toObservable()
            .ofType(EditAction::class.java)
            .throttleFirst(300, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                it.invoke(this)
            }
        )
    }

    override fun onDestroy() {
        subscriptions.unsubscribe()
        binding.viewModel.unsubscribe()
        super.onDestroy()
    }

    class CreateAction {
        fun invoke(context: Context) {
            context.startActivity(ArticleCreateActivity.createIntent(context))
        }
    }

    class EditAction {
        fun invoke(context: Context) {
            context.startActivity(ArticleEditActivity.createIntent(context))
        }
    }
}