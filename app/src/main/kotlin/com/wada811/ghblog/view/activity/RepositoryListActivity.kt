package com.wada811.ghblog.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.wada811.ghblog.R
import com.wada811.ghblog.view.binding.RepositoryListActivityBindingAdapter
import com.wada811.ghblog.viewmodel.RepositoryListViewModel

class RepositoryListActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context) = Intent(context, RepositoryListActivity::class.java)
    }
    lateinit var binding: RepositoryListActivityBindingAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RepositoryListActivityBindingAdapter(this, R.layout.activity_repository_list)
        binding.viewModel = RepositoryListViewModel()
    }

    override fun onDestroy() {
        binding.viewModel.unsubscribe()
        super.onDestroy()
    }
}
