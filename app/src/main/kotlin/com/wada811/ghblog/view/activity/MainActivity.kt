package com.wada811.ghblog.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.wada811.ghblog.R
import com.wada811.ghblog.view.binding.MainActivityBindingAdapter
import com.wada811.ghblog.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = MainActivityBindingAdapter(this, R.layout.activity_main)
        binding.viewModel = MainViewModel()
        Log.d("wada", "binding.viewModel.userName: " + binding.viewModel.userName)
        Log.d("wada", "binding.viewModel.accessToken: " + binding.viewModel.accessToken)
    }
}