package com.wada811.ghblog.data.repository

import com.wada811.ghblog.model.domain.User
import com.wada811.ghblog.model.repository.UserRepository
import rx.Observable

object  UserDataRepository : UserRepository {
    override fun user(): Observable<User> {
        return Observable.just(User("wada811", "c571c527a7f6fe1e6ccdcf60fc9b81afb62b49c8"))
    }
}