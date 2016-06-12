package com.wada811.ghblog

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.wada811.ghblog.data.repository.UserDataRepository

object App : Application() {
    val user = UserDataRepository.user()
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}