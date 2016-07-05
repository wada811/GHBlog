package com.wada811.ghblog.domain.repository

import com.wada811.ghblog.domain.model.User
import rx.Observable

interface UserRepository {
    fun user(): Observable<User>
}