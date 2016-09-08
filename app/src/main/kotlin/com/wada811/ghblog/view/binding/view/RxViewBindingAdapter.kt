package com.wada811.ghblog.view.binding.view

import android.databinding.BindingAdapter
import android.view.View
import com.jakewharton.rxbinding.view.clicks
import com.wada811.rxviewmodel.RxCommand
import com.wada811.rxviewmodel.bind

@BindingAdapter("click")
fun View.setOnClick(command: RxCommand<Unit>) = this.clicks().map { Unit }.bind(command)
