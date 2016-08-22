package com.wada811.ghblog.viewmodel

import android.view.View
import com.wada811.ghblog.domain.GHBlogContext
import com.wada811.ghblog.view.activity.ArticleCreateActivity
import com.wada811.rxviewmodel.RxCommand
import com.wada811.rxviewmodel.RxMessenger
import com.wada811.rxviewmodel.RxViewModel
import com.wada811.rxviewmodel.extensions.ObserveProperty
import com.wada811.rxviewmodel.extensions.toRxProperty

class ArticleCreateViewModel : RxViewModel() {
    var back = RxCommand(View.OnClickListener {
        RxMessenger.send(ArticleCreateActivity.BackAction())
    }).asManaged()
    var article = GHBlogContext.currentUser.currentRepository!!.currentArticle!!
    val path = article.ObserveProperty("filePath", { it.filePath }).toRxProperty(article.filePath).asManaged()
    val isDraft = article.ObserveProperty("isDraft", { it.isDraft }).toRxProperty(article.isDraft).asManaged()
    val title = article.ObserveProperty("title", { it.title }).toRxProperty(article.title).asManaged()
    val body = article.ObserveProperty("body", { it.body }).toRxProperty(article.body).asManaged()
    var preview = RxCommand(View.OnClickListener {
        RxMessenger.send(ArticleCreateActivity.PreviewAction())
    }).asManaged()
    var editTag = RxCommand(View.OnClickListener {
        RxMessenger.send(ArticleCreateActivity.TagEditAction())
    }).asManaged()
    val save = RxCommand(View.OnClickListener {
        article.save(path.value!!, isDraft.value!!, title.value!!, body.value!!)
        RxMessenger.send(ArticleCreateActivity.SaveAction())
    }).asManaged()
}