package com.wada811.ghblog.data.datasource.user

import com.wada811.ghblog.data.entity.data.OrmaDatabase
import com.wada811.ghblog.data.entity.mapper.UserEntityDataMapper
import com.wada811.ghblog.domain.model.User
import com.wada811.logforest.LogWood
import rx.Observable

class DatabaseUserDataSource(private val database: OrmaDatabase) : UserDataSource {
    fun saveUser(user: User) {
        LogWood.d("DatabaseUserDataSource#saveUser")
        database.relationOfUserEntity().upserter().execute(UserEntityDataMapper.fromUser(user))
    }

    override fun getUser(accessToken: String): Observable<User> {
        LogWood.i("DatabaseUserDataSource#getUser")
        return database.selectFromUserEntity()
            .access_tokenEq(accessToken)
            .executeAsObservable()
            .doOnNext {
                LogWood.i("DatabaseUserDataSource#getUser#onNext")
            }
            .doOnError {
                LogWood.i("DatabaseUserDataSource#getUser#onError")
            }
            .doOnCompleted {
                LogWood.i("DatabaseUserDataSource#getUser#onCompleted")
            }
            .map { userEntity ->
                LogWood.i("DatabaseUserDataSource#getUser#map")
                UserEntityDataMapper.toUser(userEntity)
            }
    }

    fun getUser(): Observable<User> {
        return database.selectFromUserEntity()
            .limit(1)
            .executeAsObservable()
            .doOnNext {
                LogWood.i("DatabaseUserDataSource#getUser#onNext")
            }
            .doOnError {
                LogWood.i("DatabaseUserDataSource#getUser#onError")
            }
            .doOnCompleted {
                LogWood.i("DatabaseUserDataSource#getUser#onCompleted")
            }
            .map { userEntity ->
                LogWood.i("DatabaseUserDataSource#getUser#map")
                UserEntityDataMapper.toUser(userEntity)
            }
    }

}