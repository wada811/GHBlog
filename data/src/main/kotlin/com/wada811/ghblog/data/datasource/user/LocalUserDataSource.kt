package com.wada811.ghblog.data.datasource.user

import com.wada811.ghblog.data.entity.data.OrmaDatabase
import com.wada811.ghblog.data.entity.mapper.UserEntityDataMapper
import com.wada811.ghblog.domain.model.User
import com.wada811.logforest.LogWood
import rx.Observable

class LocalUserDataSource(private val database: OrmaDatabase) : UserDataSource {
    override fun getCurrentUser(): Observable<User> {
        return database.selectFromUserEntity()
            .limit(1)
            .executeAsObservable()
            .doOnNext { LogWood.v("LocalUserDataSource#getCurrentUser#onNext") }
            .doOnError { LogWood.v("LocalUserDataSource#getCurrentUser#onError", it) }
            .doOnCompleted { LogWood.v("LocalUserDataSource#getCurrentUser#onCompleted") }
            .map { userEntity ->
                LogWood.v("LocalUserDataSource#getCurrentUser#map")
                UserEntityDataMapper.toUser(userEntity)
            }
    }

    override fun getAccessToken(code: String, state: String): Observable<String> {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUser(accessToken: String): Observable<User> {
        LogWood.v("LocalUserDataSource#getUser")
        return database.selectFromUserEntity()
            .access_tokenEq(accessToken)
            .executeAsObservable()
            .doOnNext { LogWood.v("LocalUserDataSource#getUser#onNext") }
            .doOnError { LogWood.v("LocalUserDataSource#getUser#onError", it) }
            .doOnCompleted { LogWood.v("LocalUserDataSource#getUser#onCompleted") }
            .map { userEntity ->
                LogWood.v("LocalUserDataSource#getUser#map")
                UserEntityDataMapper.toUser(userEntity)
            }
    }

    override fun saveUser(user: User) {
        LogWood.d("LocalUserDataSource#saveUser")
        database.relationOfUserEntity().upserter().execute(UserEntityDataMapper.fromUser(user))
    }

}