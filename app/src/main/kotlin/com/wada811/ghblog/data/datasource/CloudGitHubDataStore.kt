package com.wada811.ghblog.data.datasource

import com.wada811.ghblog.data.entity.RepositoryEntity
import com.wada811.ghblog.data.entity.RepositoryEntityDataMapper
import com.wada811.ghblog.data.net.GitHubApi
import com.wada811.ghblog.model.domain.Repository
import com.wada811.ghblog.model.domain.User
import rx.Observable

class CloudGitHubDataStore(var user: User) {
    fun getRepositoryList(): Observable<out List<Repository>> {
        return Observable.defer {
            GitHubApi(user).getRepositoryList().map {
                repositoryEntityList: List<RepositoryEntity> ->
                repositoryEntityList.map { entity ->
                    RepositoryEntityDataMapper.transform(entity)
                }
            }
        }
    }
}