package com.wada811.ghblog.viewmodel

import com.wada811.rxviewmodel.RxProperty
import com.wada811.rxviewmodel.RxViewModel

class ArticleTagListItemViewModel(tagName: String, checked: Boolean) : RxViewModel() {
    val tagName = RxProperty(tagName).asManaged()
    val checked = RxProperty(checked).asManaged()
}