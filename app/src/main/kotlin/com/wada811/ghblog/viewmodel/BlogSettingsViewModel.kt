package com.wada811.ghblog.viewmodel

import android.widget.AdapterView
import com.wada811.ghblog.domain.GHBlogContext
import com.wada811.ghblog.domain.model.Blog
import com.wada811.ghblog.domain.model.Repository
import com.wada811.ghblog.view.activity.BlogSettingsActivity
import com.wada811.logforest.LogWood
import com.wada811.rxviewmodel.*
import com.wada811.rxviewmodel.extensions.ToRxArrayList
import com.wada811.rxviewmodel.extensions.toRxProperty

class BlogSettingsViewModel : RxViewModel() {
    val back: RxCommand<Unit>
        = RxCommand<Unit>({ RxMessenger.send(BlogSettingsActivity.BackAction()) }).asManaged()
    val repositoryViewModels: RxArrayList<RepositoryListItemViewModel>
        = GHBlogContext.currentUser.repositories.ToRxArrayList { RepositoryListItemViewModel(it) }.asManaged()
    val select: RxCommand<Int>
        = RxCommand({ position: Int ->
        if (position != AdapterView.INVALID_POSITION) {
            selectedRepository.value = repositoryViewModels[position].repository
        }
    }).asManaged()
    val selectedRepository: RxProperty<Repository>
        = RxProperty<Repository>().asManaged()
    val url: RxProperty<String?>
        = selectedRepository.asObservable().map { it?.homepage }.toRxProperty().asManaged()
    val save: RxCommand<Unit>
        = selectedRepository.asObservable().map { it != null }
        .toRxCommand<Unit>({
            val repository = selectedRepository.value!!
            LogWood.d("GHBlogContext.currentUser.blogs: ${GHBlogContext.currentUser.blogs.size}")
            val blogTitle = repository.description ?: repository.fullName
            val blog = Blog(GHBlogContext.currentUser, repository, blogTitle, url.value!!)
            blog.save()
            GHBlogContext.currentUser.blogs.add(blog)
            GHBlogContext.currentUser.currentBlog = blog
            RxMessenger.send(BlogSettingsActivity.SaveAction())
        }).asManaged()
}