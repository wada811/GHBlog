package com.wada811.ghblog.data.datasource.user

import com.wada811.ghblog.domain.model.User
import rx.Observable

interface UserDataSource {
    fun getUser(accessToken: String): Observable<User>
}