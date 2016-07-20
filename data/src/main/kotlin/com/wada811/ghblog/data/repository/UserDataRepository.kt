package com.wada811.ghblog.data.repository

import com.wada811.ghblog.data.datasource.CloudUserDataStore
import com.wada811.ghblog.domain.model.User
import com.wada811.ghblog.domain.repository.UserRepository
import rx.Observable

class UserDataRepository : UserRepository {
    override fun getAccessToken(code: String, state: String) = CloudUserDataStore().getAccessToken(code, state)
    override fun getUser(accessToken: String): Observable<User> = CloudUserDataStore().getUser(accessToken)
}