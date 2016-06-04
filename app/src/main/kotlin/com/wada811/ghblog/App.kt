package com.wada811.ghblog

import android.app.Application
import com.wada811.ghblog.data.repository.UserDataRepository

object App : Application() {
    val user = UserDataRepository.user()
}