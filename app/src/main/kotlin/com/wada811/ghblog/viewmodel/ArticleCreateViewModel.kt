package com.wada811.ghblog.viewmodel

import com.wada811.ghblog.domain.GHBlogContext
import com.wada811.ghblog.view.activity.ArticleCreateActivity
import com.wada811.rxviewmodel.RxCommand
import com.wada811.rxviewmodel.RxMessenger
import com.wada811.rxviewmodel.RxViewModel
import com.wada811.rxviewmodel.extensions.ObserveProperty
import com.wada811.rxviewmodel.extensions.toRxProperty

class ArticleCreateViewModel : RxViewModel() {
    val back = RxCommand<Unit>({
        RxMessenger.send(ArticleCreateActivity.BackAction())
    }).asManaged()
    val article = GHBlogContext.currentUser.currentBlog!!.currentArticle!!
    val path = article.ObserveProperty("filePath", { it.filePath }).toRxProperty(article.filePath).asManaged()
    val isDraft = article.ObserveProperty("isDraft", { it.isDraft }).toRxProperty(article.isDraft).asManaged()
    val title = article.ObserveProperty("title", { it.title }).toRxProperty(article.title).asManaged()
    val body = article.ObserveProperty("body", { it.body }).toRxProperty(article.body).asManaged()
    val preview = RxCommand<Unit>({
        RxMessenger.send(ArticleCreateActivity.PreviewAction())
    }).asManaged()
    val editTag = RxCommand<Unit>({
        RxMessenger.send(ArticleCreateActivity.TagEditAction())
    }).asManaged()
    val save = RxCommand<Unit>({
        article.save(path.value!!, isDraft.value!!, title.value!!, body.value!!)
        RxMessenger.send(ArticleCreateActivity.SaveAction())
    }).asManaged()
}