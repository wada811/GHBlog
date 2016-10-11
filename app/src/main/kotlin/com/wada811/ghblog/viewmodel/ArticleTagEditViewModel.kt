package com.wada811.ghblog.viewmodel

import android.view.inputmethod.EditorInfo
import com.wada811.ghblog.domain.GHBlogContext
import com.wada811.ghblog.view.activity.ArticleTagEditActivity
import com.wada811.logforest.LogWood
import com.wada811.rxviewmodel.RxCommand
import com.wada811.rxviewmodel.RxMessenger
import com.wada811.rxviewmodel.RxProperty
import com.wada811.rxviewmodel.RxViewModel
import com.wada811.rxviewmodel.extensions.ToRxArrayList

class ArticleTagEditViewModel : RxViewModel() {
    var back = RxCommand<Unit>({
        RxMessenger.send(ArticleTagEditActivity.BackAction())
    }).asManaged()
    var article = GHBlogContext.currentUser.currentBlog!!.currentArticle!!
    val save = RxCommand<Unit>({
        LogWood.d("article.tags: ${article.tags.map { it }}")
        LogWood.d("tags: ${tags.map { "${it.tagName.value}: ${it.checked.value}" }}")
        article.tags.clear()
        val selectedTags = tags.filter { it.checked.value!! }.map { it.tagName.value!! }
        article.tags.addAll(selectedTags)
        RxMessenger.send(ArticleTagEditActivity.SaveAction())
    }).asManaged()
    val tag = RxProperty("").asManaged()
    val done = RxCommand<Int>({
        if (it == EditorInfo.IME_ACTION_DONE) {
            tags.add(ArticleTagListItemViewModel(tag.value!!, true))
            tag.value = ""
            RxMessenger.send(ArticleTagEditActivity.DoneAction())
        }
    }).asManaged()
    val tags = article.tags.ToRxArrayList { ArticleTagListItemViewModel(it, true) }
    val check = RxCommand({ position: Int ->
        tags[position].checked.value = !tags[position].checked.value!!
        LogWood.d("check: ${tags.map { "${it.tagName.value}: ${it.checked.value}" }}")
    }).asManaged()
}