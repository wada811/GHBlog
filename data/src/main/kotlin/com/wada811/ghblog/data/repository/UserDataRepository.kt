package com.wada811.ghblog.data.repository

import com.wada811.ghblog.data.datasource.CodeUserDataStore
import com.wada811.ghblog.domain.model.User
import com.wada811.ghblog.domain.repository.UserRepository
import rx.Observable

class UserDataRepository : UserRepository {
    override fun user(): Observable<User> = CodeUserDataStore().getUser()
}