package com.wada811.ghblog

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.wada811.ghblog.data.repository.UserDataRepository
import com.wada811.ghblog.model.domain.Repository

object App : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }

    val user = UserDataRepository.user()
    var currentRepository: Repository? = null
}