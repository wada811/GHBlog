package com.wada811.ghblog.viewmodel

import android.view.View
import com.wada811.ghblog.domain.GHBlogContext
import com.wada811.ghblog.domain.model.Article
import com.wada811.ghblog.view.activity.ArticleCreateActivity
import com.wada811.observablemodel.ObservableSynchronizedArrayList
import com.wada811.rxviewmodel.RxCommand
import com.wada811.rxviewmodel.RxMessenger
import com.wada811.rxviewmodel.RxProperty
import com.wada811.rxviewmodel.RxViewModel
import org.threeten.bp.ZonedDateTime

class ArticleCreateViewModel : RxViewModel() {
    val path = RxProperty("").asManaged()
    val isDraft = RxProperty(false).asManaged()
    val title = RxProperty("").asManaged()
    val body = RxProperty("").asManaged()
    val save = RxCommand(View.OnClickListener {
        Article.Builder(GHBlogContext.currentUser, GHBlogContext.currentUser.currentRepository!!)
            .filePath(path.value!!)
            .publishDateTime(ZonedDateTime.now())
            .isDraft(isDraft.value!!)
            .title(title.value!!)
            .tags(ObservableSynchronizedArrayList())
            .body(body.value!!)
            .build()
            .save()
        RxMessenger.send(ArticleCreateActivity.SaveAction())
    }).asManaged()
}