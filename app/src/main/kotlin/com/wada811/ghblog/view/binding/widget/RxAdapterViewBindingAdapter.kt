package com.wada811.ghblog.view.binding.widget

import android.databinding.BindingAdapter
import android.widget.AdapterView
import com.jakewharton.rxbinding.widget.itemSelections
import com.wada811.rxviewmodel.RxCommand
import com.wada811.rxviewmodel.bind

@BindingAdapter("onItemSelected")
fun AdapterView<*>.setOnItemSelected(command: RxCommand<Int>) = this.itemSelections().bind(command)
