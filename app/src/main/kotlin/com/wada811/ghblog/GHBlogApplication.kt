package com.wada811.ghblog

import android.app.Application
import com.facebook.stetho.Stetho
import com.jakewharton.threetenabp.AndroidThreeTen
import com.squareup.leakcanary.LeakCanary
import com.wada811.ghblog.data.datasource.github.LocalGitHubDataSource
import com.wada811.ghblog.data.datasource.github.RemoteGitHubDataSource
import com.wada811.ghblog.data.datasource.user.LocalUserDataSource
import com.wada811.ghblog.data.datasource.user.RemoteUserDataSource
import com.wada811.ghblog.data.repository.GitHubDataRepository
import com.wada811.ghblog.data.repository.UserDataRepository
import com.wada811.ghblog.domain.GHBlogContext
import com.wada811.ghblog.domain.model.GitHubApp
import com.wada811.logforest.LogForest
import com.wada811.logforest.android.AndroidLogTree
import com.wada811.rxviewmodel.UIThreadScheduler
import rx.android.schedulers.AndroidSchedulers

class GHBlogApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        LeakCanary.install(this)
        AndroidThreeTen.init(this)
        UIThreadScheduler.DefaultScheduler = AndroidSchedulers.mainThread()
        val gitHubApp = GitHubApp(BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET)
        val userRepository = UserDataRepository(LocalUserDataSource(this), RemoteUserDataSource())
        val gitHubRepository = GitHubDataRepository(LocalGitHubDataSource(this), RemoteGitHubDataSource())
        GHBlogContext.init(gitHubApp, userRepository, gitHubRepository)
        LogForest.plant(AndroidLogTree)
    }
}