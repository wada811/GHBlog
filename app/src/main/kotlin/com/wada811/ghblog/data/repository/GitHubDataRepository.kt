package com.wada811.ghblog.data.repository

import com.wada811.ghblog.data.datasource.CloudGitHubDataStore
import com.wada811.ghblog.model.domain.GitTree
import com.wada811.ghblog.model.domain.Repository
import com.wada811.ghblog.model.domain.User
import com.wada811.ghblog.model.repository.GitHubRepository
import rx.Observable

object GitHubDataRepository : GitHubRepository {
    override fun getRepositoryList(user: User): Observable<List<Repository>> {
        return CloudGitHubDataStore(user).getAllRepository()
    }

    override fun getTree(user: User, repository: Repository): Observable<GitTree> {
        return CloudGitHubDataStore(user).getTree(repository)
    }
}