package com.wada811.ghblog.view.binding.widget

import android.databinding.BindingAdapter
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.jakewharton.rxbinding.widget.editorActions
import com.wada811.rxviewmodel.RxCommand
import com.wada811.rxviewmodel.bind
import rx.functions.Func1

@BindingAdapter("onEditDone")
fun TextView.setOnEditDone(command: RxCommand<Int>) = this.editorActions(Func1 { it == EditorInfo.IME_ACTION_DONE }).bind(command)
