package com.wada811.ghblog.data.datasource.github

import android.content.Context
import com.wada811.ghblog.data.entity.data.OrmaDatabase
import com.wada811.ghblog.data.entity.data.RepositoryEntity
import com.wada811.ghblog.data.entity.mapper.data.RepositoryEntityDataMapper
import com.wada811.ghblog.domain.model.Repository
import com.wada811.ghblog.domain.model.User
import com.wada811.logforest.LogWood
import rx.Observable

class LocalGitHubDataSource(context: Context) : GitHubDataSource {
    private val database: OrmaDatabase = OrmaDatabase.builder(context).trace(true).build()
    override fun getRepositories(user: User): Observable<Repository> {
        return database.selectFromRepositoryEntity().userEq(user.id)
            .executeAsObservable()
            .doOnNext { LogWood.v("LocalUserDataSource#getCurrentUser#onNext") }
            .doOnError { LogWood.v("LocalUserDataSource#getCurrentUser#onError", it) }
            .doOnCompleted { LogWood.v("LocalUserDataSource#getCurrentUser#onCompleted") }
            .map { repositoryEntity: RepositoryEntity ->
                LogWood.v("LocalUserDataSource#getCurrentUser#map")
                RepositoryEntityDataMapper.toRepository(repositoryEntity)
            }
    }
}