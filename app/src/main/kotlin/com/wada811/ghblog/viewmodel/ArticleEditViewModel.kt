package com.wada811.ghblog.viewmodel

import android.view.View
import com.wada811.ghblog.domain.GHBlogContext
import com.wada811.ghblog.view.activity.ArticleEditActivity
import com.wada811.rxviewmodel.RxCommand
import com.wada811.rxviewmodel.RxMessenger
import com.wada811.rxviewmodel.RxProperty
import com.wada811.rxviewmodel.RxViewModel
import com.wada811.rxviewmodel.extensions.ObserveProperty
import com.wada811.rxviewmodel.extensions.toRxProperty

class ArticleEditViewModel() : RxViewModel() {
    var repositoryContent = GHBlogContext.currentUser.currentRepository!!.currentRepositoryContent!!

    init {
        repositoryContent.loadContent()
    }

    var path = repositoryContent.ObserveProperty("path", { it.path }).toRxProperty(repositoryContent.path).asManaged()
    var name: RxProperty<String> = repositoryContent.ObserveProperty("content", { it.content })
        .map { it.splitToSequence(System.getProperty("line.separator")).first() }
        .toRxProperty(repositoryContent.content.splitToSequence(System.getProperty("line.separator")).first())
        .asManaged()
    var content: RxProperty<String> = repositoryContent.ObserveProperty("content", { it.content })
        .map { it.splitToSequence(System.getProperty("line.separator"), limit = 2).last() }
        .toRxProperty(repositoryContent.content.splitToSequence(System.getProperty("line.separator"), limit = 2).last())
        .asManaged()
    var save = RxCommand(View.OnClickListener {
        val contentString = name.value + System.getProperty("line.separator") + content.value
        repositoryContent.update(path.value!!, "Update ${path.value!!}", contentString)
        RxMessenger.send(ArticleEditActivity.SaveAction())
    }).asManaged()
}