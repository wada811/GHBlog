package com.wada811.ghblog

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.squareup.leakcanary.LeakCanary
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
        LeakCanary.install(this)
        AndroidThreeTen.init(this)
        UIThreadScheduler.DefaultScheduler = AndroidSchedulers.mainThread()
        GHBlogContext.init(GitHubApp(BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET), UserDataRepository(), GitHubDataRepository())
        LogForest.plant(AndroidLogTree)
    }
}