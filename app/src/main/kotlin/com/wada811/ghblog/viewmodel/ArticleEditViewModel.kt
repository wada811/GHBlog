package com.wada811.ghblog.viewmodel

import com.wada811.ghblog.domain.GHBlogContext
import com.wada811.ghblog.view.activity.ArticleEditActivity
import com.wada811.rxviewmodel.RxCommand
import com.wada811.rxviewmodel.RxMessenger
import com.wada811.rxviewmodel.RxViewModel
import com.wada811.rxviewmodel.extensions.ObserveProperty
import com.wada811.rxviewmodel.extensions.toRxProperty

class ArticleEditViewModel() : RxViewModel() {
    var back = RxCommand<Unit>({
        RxMessenger.send(ArticleEditActivity.BackAction())
    }).asManaged()
    var article = GHBlogContext.currentUser.currentBlog!!.currentArticle!!
    var path = article.ObserveProperty("filePath", { it.filePath }).toRxProperty(article.filePath).asManaged()
    val isDraft = article.ObserveProperty("isDraft", { it.isDraft }).toRxProperty(article.isDraft).asManaged()
    var title = article.ObserveProperty("title", { it.title }).toRxProperty(article.title).asManaged()
    var body = article.ObserveProperty("body", { it.body }).toRxProperty(article.body).asManaged()
    var preview = RxCommand<Unit>({
        RxMessenger.send(ArticleEditActivity.PreviewAction())
    }).asManaged()
    var editTag = RxCommand<Unit>({
        RxMessenger.send(ArticleEditActivity.TagEditAction())
    }).asManaged()
    var save = RxCommand<Unit>({
        article.save(path.value!!, isDraft.value!!, title.value!!, body.value!!)
        RxMessenger.send(ArticleEditActivity.SaveAction())
    }).asManaged()
}