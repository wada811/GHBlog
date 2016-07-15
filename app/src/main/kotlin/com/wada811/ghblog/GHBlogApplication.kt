package com.wada811.ghblog

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.squareup.leakcanary.LeakCanary
import com.wada811.ghblog.data.repository.GitHubDataRepository
import com.wada811.ghblog.data.repository.UserDataRepository
import com.wada811.ghblog.domain.GHBlogContext
import rx.android.schedulers.AndroidSchedulers

class GHBlogApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        LeakCanary.install(this)
        AndroidThreeTen.init(this)
        GHBlogContext.init(AndroidSchedulers.mainThread(), UserDataRepository(), GitHubDataRepository())
    }
}