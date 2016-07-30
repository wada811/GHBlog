package com.wada811.ghblog.data.repository

import com.wada811.ghblog.data.datasource.user.UserDataSource
import com.wada811.ghblog.domain.model.User
import com.wada811.ghblog.domain.repository.UserRepository
import rx.Observable

class UserDataRepository(private val localDataSource: UserDataSource, private val remoteDataSource: UserDataSource) : UserRepository {

    override fun getCurrentUser() = localDataSource.getCurrentUser()

    override fun getAccessToken(code: String, state: String) = remoteDataSource.getAccessToken(code, state)

    override fun getUser(accessToken: String): Observable<User> {
        return Observable.merge(
            localDataSource.getUser(accessToken),
            remoteDataSource.getUser(accessToken).doOnNext { localDataSource.saveUser(it) }
        )
    }
}