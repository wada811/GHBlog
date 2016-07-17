package com.wada811.ghblog.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.wada811.ghblog.R
import com.wada811.ghblog.view.binding.RepositoryListActivityBindingAdapter
import com.wada811.ghblog.viewmodel.RepositoryListViewModel
import com.wada811.rxviewmodel.RxMessenger
import rx.android.schedulers.AndroidSchedulers
import rx.subscriptions.CompositeSubscription
import java.util.concurrent.TimeUnit

class RepositoryListActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context) = Intent(context, RepositoryListActivity::class.java)
    }

    lateinit var binding: RepositoryListActivityBindingAdapter
    val subscriptions = CompositeSubscription()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RepositoryListActivityBindingAdapter(this, R.layout.activity_repository_list)
        binding.viewModel = RepositoryListViewModel()
        subscriptions.add(RxMessenger
            .toObservable()
            .ofType(NextAction::class.java)
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

    class NextAction {
        fun invoke(context: Context) {
            context.startActivity(ArticleListActivity.createIntent(context))
        }
    }
}
