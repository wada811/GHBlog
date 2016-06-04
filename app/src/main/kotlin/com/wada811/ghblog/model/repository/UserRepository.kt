package com.wada811.ghblog.model.repository

import com.wada811.ghblog.model.domain.User
import rx.Observable

interface UserRepository {
    fun user() : Observable<User>
}