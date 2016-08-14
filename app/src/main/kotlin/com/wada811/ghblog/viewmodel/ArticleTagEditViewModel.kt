package com.wada811.ghblog.viewmodel

import android.view.View
import android.widget.AdapterView
import com.wada811.ghblog.domain.GHBlogContext
import com.wada811.ghblog.view.activity.ArticleTagEditActivity
import com.wada811.logforest.LogWood
import com.wada811.rxviewmodel.RxCommand
import com.wada811.rxviewmodel.RxMessenger
import com.wada811.rxviewmodel.RxProperty
import com.wada811.rxviewmodel.RxViewModel
import com.wada811.rxviewmodel.extensions.ToRxArrayList
import rx.functions.Action0

class ArticleTagEditViewModel : RxViewModel() {
    var article = GHBlogContext.currentUser.currentRepository!!.currentArticle!!
    val save = RxCommand(View.OnClickListener {
        LogWood.d("article.tags: ${article.tags.map { it }}")
        LogWood.d("tags: ${tags.map { "${it.tagName.value}: ${it.checked.value}" }}")
        article.tags.clear()
        val selectedTags = tags.filter { it.checked.value!! }.map { it.tagName.value!! }
        article.tags.addAll(selectedTags)
        RxMessenger.send(ArticleTagEditActivity.SaveAction())
    }).asManaged()
    val tag = RxProperty("").asManaged()
    val done = RxCommand(Action0 {
        tags.add(ArticleTagListItemViewModel(tag.value!!, true))
        tag.value = ""
    }).asManaged()
    val tags = article.tags.ToRxArrayList { ArticleTagListItemViewModel(it, true) }
    val check = RxCommand(AdapterView.OnItemClickListener {
        parent: AdapterView<*>, view: View, position: Int, id: Long ->
        tags[position].checked.value = !tags[position].checked.value!!
        LogWood.d("check: ${tags.map { "${it.tagName.value}: ${it.checked.value}" }}")
    }).asManaged()
}