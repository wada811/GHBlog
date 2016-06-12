package com.wada811.ghblog.data.repository

import com.wada811.ghblog.data.datasource.CloudGitHubDataStore
import com.wada811.ghblog.model.domain.Repository
import com.wada811.ghblog.model.domain.User
import com.wada811.ghblog.model.repository.GitHubRepository
import rx.Observable

object GitHubDataRepository : GitHubRepository {
    override fun getRepositoryList(user: User): Observable<out List<Repository>> {
        return CloudGitHubDataStore(user).getRepositoryList()
    }
}