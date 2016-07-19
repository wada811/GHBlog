package com.wada811.ghblog.viewmodel

import android.view.View
import com.wada811.ghblog.domain.GHBlogContext
import com.wada811.ghblog.domain.model.Article
import com.wada811.ghblog.view.activity.ArticleEditActivity
import com.wada811.rxviewmodel.RxCommand
import com.wada811.rxviewmodel.RxMessenger
import com.wada811.rxviewmodel.RxViewModel
import com.wada811.rxviewmodel.extensions.ObserveProperty
import com.wada811.rxviewmodel.extensions.toRxProperty

class ArticleEditViewModel() : RxViewModel() {
    var repositoryContent = GHBlogContext.currentUser.currentRepository!!.currentRepositoryContent!!
    val article = Article.Builder.repositoryContent(repositoryContent).build().asManaged()

    init {
        repositoryContent.loadContent()
    }

    var path = article.ObserveProperty("filePath", { it.filePath }).toRxProperty(article.filePath).asManaged()
    val isDraft = article.ObserveProperty("isDraft", { it.isDraft }).toRxProperty(article.isDraft).asManaged()
    var title = article.ObserveProperty("title", { it.title }).toRxProperty(article.title).asManaged()
    var body = article.ObserveProperty("body", { it.body }).toRxProperty(article.body).asManaged()
    var save = RxCommand(View.OnClickListener {
        article.filePath = path.value!!
        article.isDraft = isDraft.value!!
        article.title = title.value!!
        article.body = body.value!!
        article.save()
        RxMessenger.send(ArticleEditActivity.SaveAction())
    }).asManaged()
}