package com.wada811.ghblog.domain.repository

import com.wada811.ghblog.domain.model.User
import rx.Observable

interface UserRepository {
    fun getCurrentUser(): Observable<User>
    fun getAccessToken(code: String, state: String): Observable<String>
    fun getUser(accessToken: String): Observable<User>
}