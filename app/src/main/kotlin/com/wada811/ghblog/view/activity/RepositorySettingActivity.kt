package com.wada811.ghblog.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.wada811.ghblog.R
import com.wada811.ghblog.view.binding.RepositorySettingActivityBindingAdapter
import com.wada811.ghblog.viewmodel.RepositorySettingViewModel

class RepositorySettingActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context) = Intent(context, RepositorySettingActivity::class.java)
    }
    lateinit var binding: RepositorySettingActivityBindingAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RepositorySettingActivityBindingAdapter(this, R.layout.activity_repository_setting)
        binding.viewModel = RepositorySettingViewModel()
    }

    override fun onDestroy() {
        binding.viewModel.unsubscribe()
        super.onDestroy()
    }
}
