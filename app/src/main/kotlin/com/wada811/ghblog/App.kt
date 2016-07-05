package com.wada811.ghblog

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.squareup.leakcanary.LeakCanary
import com.wada811.ghblog.data.repository.GitHubDataRepository
import com.wada811.ghblog.data.repository.UserDataRepository
import com.wada811.ghblog.domain.GHBlogContext
import com.wada811.ghblog.domain.model.Repository
import com.wada811.ghblog.viewmodel.ArticleListItemViewModel

object App : Application() {
    init {
        GHBlogContext.userRepository = UserDataRepository()
        GHBlogContext.gitHubRepository = GitHubDataRepository()
    }

    override fun onCreate() {
        super.onCreate()
        LeakCanary.install(this)
        AndroidThreeTen.init(this)
    }

    val user = GHBlogContext.userRepository.user()
    var currentRepository: Repository? = null
    var currentArticleViewModel: ArticleListItemViewModel? = null
}