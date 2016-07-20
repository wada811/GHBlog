package com.wada811.ghblog.view.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.wada811.ghblog.R
import com.wada811.ghblog.view.binding.OAuthActivityBindingAdapter
import com.wada811.ghblog.viewmodel.OAuthViewModel
import com.wada811.rxviewmodel.RxMessenger
import rx.functions.Action1
import rx.subscriptions.CompositeSubscription

class OAuthActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context) = Intent(context, OAuthActivity::class.java)
    }

    lateinit var binding: OAuthActivityBindingAdapter
    val subscriptions = CompositeSubscription()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscriptions.add(RxMessenger.observe(AuthorizeAction::class.java).onBackpressureDrop().subscribe { it.call(this) })
        subscriptions.add(RxMessenger.observe(CompleteAction::class.java).onBackpressureDrop().subscribe { it.call(this) })
        Log.e("wada", "intent.action: ${intent.action}")
        Log.e("wada", "intent.categories: ${intent.categories}")
        Log.e("wada", "intent.data: ${intent.data}")
        Log.e("wada", "intent.dataString: ${intent.dataString}")
        binding = OAuthActivityBindingAdapter(this, R.layout.activity_oauth)
        binding.viewModel = OAuthViewModel(intent.dataString)
    }

    override fun onDestroy() {
        subscriptions.unsubscribe()
        binding.viewModel?.unsubscribe()
        super.onDestroy()
    }

    class AuthorizeAction(val url: String) : Action1<Activity> {
        override fun call(activity: Activity) {
            activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            activity.finish()
        }
    }

    class CompleteAction() : Action1<Activity> {
        override fun call(activity: Activity) {
            activity.finish()
        }
    }
}