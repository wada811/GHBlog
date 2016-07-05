package com.wada811.ghblog.data.datasource

import com.wada811.ghblog.domain.model.User
import rx.Observable

class CodeUserDataStore {
    fun getUser(): Observable<User> = Observable.just(User("wada811", "c571c527a7f6fe1e6ccdcf60fc9b81afb62b49c8"))
}