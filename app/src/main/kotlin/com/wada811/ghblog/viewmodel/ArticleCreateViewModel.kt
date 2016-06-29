package com.wada811.ghblog.viewmodel

import android.view.View
import com.wada811.rxviewmodel.RxCommand
import com.wada811.rxviewmodel.RxProperty
import com.wada811.rxviewmodel.RxViewModel

class ArticleCreateViewModel : RxViewModel() {
    var content = RxProperty("").asManaged()
    var save = RxCommand(View.OnClickListener { }).asManaged()
}