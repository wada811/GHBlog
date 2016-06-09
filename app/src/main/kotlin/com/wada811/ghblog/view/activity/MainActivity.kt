package com.wada811.ghblog.view.activity

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.wada811.ghblog.R
import com.wada811.ghblog.view.binding.MainActivityBindingAdapter
import com.wada811.ghblog.viewmodel.MainViewModel
import com.wada811.rxviewmodel.RxMessenger
import rx.android.schedulers.AndroidSchedulers
import rx.subscriptions.CompositeSubscription
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    lateinit var binding: MainActivityBindingAdapter
    val subscriptions = CompositeSubscription()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBindingAdapter(this, R.layout.activity_main)
        binding.viewModel = MainViewModel()
        Log.d("wada", "binding.viewModel.userName: " + binding.viewModel.userName)
        Log.d("wada", "binding.viewModel.accessToken: " + binding.viewModel.accessToken)
        subscriptions.add(
                RxMessenger
                        .toObservable()
                        .sample(300, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            (it as? NextAction)?.invoke(this)
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
            context.startActivity(RepositorySettingActivity.createIntent(context))
        }
    }
}
