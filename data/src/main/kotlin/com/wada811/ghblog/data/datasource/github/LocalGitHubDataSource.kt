package com.wada811.ghblog.data.datasource.github

import com.wada811.ghblog.data.entity.data.OrmaDatabase
import com.wada811.ghblog.data.entity.data.RepositoryEntity
import com.wada811.ghblog.data.entity.mapper.data.RepositoryEntityDataMapper
import com.wada811.ghblog.domain.model.Repository
import com.wada811.ghblog.domain.model.User
import com.wada811.logforest.LogWood
import rx.Observable

class LocalGitHubDataSource(private val database: OrmaDatabase) : GitHubDataSource {
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

    override fun saveRepository(repository: Repository) {
        database.relationOfRepositoryEntity().upserter().executeAsObservable(RepositoryEntityDataMapper.fromRepository(repository))
    }

}