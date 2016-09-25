package com.wada811.ghblog.view.binding.widget

import android.databinding.BindingAdapter
import android.widget.TextView
import com.jakewharton.rxbinding.widget.editorActions
import com.wada811.rxviewmodel.RxCommand
import com.wada811.rxviewmodel.bind

@BindingAdapter("editorActions")
fun TextView.editorActions(command: RxCommand<Int>) = this.editorActions().bind(command)
