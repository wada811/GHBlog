package com.wada811.ghblog.data.datasource.user

import com.wada811.ghblog.domain.model.User
import rx.Observable

interface UserDataSource {
    fun getCurrentUser(): Observable<User>
    fun getAccessToken(code: String, state: String): Observable<String>
    fun getUser(accessToken: String): Observable<User>
    fun saveUser(user: User)
}