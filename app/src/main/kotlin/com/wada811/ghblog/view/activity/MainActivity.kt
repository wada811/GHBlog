package com.wada811.ghblog.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.wada811.ghblog.R
import com.wada811.ghblog.view.binding.MainActivityBindingAdapter
import com.wada811.ghblog.viewmodel.MainViewModel
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = MainActivityBindingAdapter(this, R.layout.activity_main)
        binding.viewModel = MainViewModel()
        Log.d("wada", "binding.viewModel.test: " + binding.viewModel.text)
        timer(initialDelay = 1000, period = 1000) {
            binding.viewModel.text = "" + System.currentTimeMillis()
            Log.d("wada", "binding.viewModel.test: " + binding.viewModel.text)
        }
    }

}