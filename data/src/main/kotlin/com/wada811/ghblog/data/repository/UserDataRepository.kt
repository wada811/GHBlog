package com.wada811.ghblog.data.repository

import com.wada811.ghblog.data.datasource.factory.IUserDataSourceFactory
import com.wada811.ghblog.domain.model.User
import com.wada811.ghblog.domain.repository.UserRepository
import rx.Observable

class UserDataRepository(private val dataSourceFactory: IUserDataSourceFactory) : UserRepository {

    override fun getCurrentUser() = dataSourceFactory.createDatabaseDataSource().getUser()

    override fun getAccessToken(code: String, state: String) = dataSourceFactory.createCloudDataSource().getAccessToken(code, state)

    override fun getUser(accessToken: String): Observable<User> {
        return Observable.merge(
            dataSourceFactory.createDatabaseDataSource().getUser(accessToken),
            dataSourceFactory.createCloudDataSource().getUser(accessToken).doOnNext {
                dataSourceFactory.createDatabaseDataSource().saveUser(it)
            }
        )
    }
}