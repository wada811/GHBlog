package com.wada811.ghblog.data.repository

import com.wada811.ghblog.data.datasource.CodeUserDataStore
import com.wada811.ghblog.model.domain.User
import com.wada811.ghblog.model.repository.UserRepository
import rx.Observable

object UserDataRepository : UserRepository {
    override fun user(): Observable<User> = CodeUserDataStore().getUser()
}