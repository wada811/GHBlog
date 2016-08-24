package com.wada811.ghblog.data.datasource.github

import com.wada811.ghblog.domain.model.Repository
import com.wada811.ghblog.domain.model.User
import rx.Observable

interface GitHubDataSource {
    fun getRepositories(user: User): Observable<Repository>
    fun saveRepository(repository: Repository)
}