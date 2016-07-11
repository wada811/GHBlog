package com.wada811.ghblog.data.repository

import com.wada811.ghblog.data.datasource.CloudGitHubDataStore
import com.wada811.ghblog.domain.model.*
import com.wada811.ghblog.domain.repository.GitHubRepository
import rx.Observable

class GitHubDataRepository : GitHubRepository {
    override fun getRepositoryList(user: User): Observable<List<Repository>> {
        return CloudGitHubDataStore(user).getAllRepository()
    }

    override fun getContents(user: User, repository: Repository, path: String): Observable<List<RepositoryContentInfo>> {
        return CloudGitHubDataStore(user).getContents(repository, path)
    }

    override fun getContent(user: User, repository: Repository, path: String): Observable<RepositoryContent> {
        return CloudGitHubDataStore(user).getContent(repository, path)
    }

    override fun createContent(user: User, repository: Repository, commit: GitCommit): Observable<GitHubCommit> {
        return CloudGitHubDataStore(user).createContent(repository, commit)
    }

    override fun updateContent(user: User, repository: Repository, commit: GitCommit): Observable<GitHubCommit> {
        return CloudGitHubDataStore(user).updateContent(repository, commit)
    }

    override fun deleteContent(user: User, repository: Repository, commit: GitCommit): Observable<GitHubCommit> {
        return CloudGitHubDataStore(user).deleteContent(repository, commit)
    }

    override fun renameContent(user: User, repository: Repository, commit: GitCommit): Observable<GitHubCommit> {
        return CloudGitHubDataStore(user).renameContent(repository, commit)
    }

    override fun getTree(user: User, repository: Repository): Observable<GitHubTree> {
        return CloudGitHubDataStore(user).getTree(repository)
    }

    override fun createTree(user: User, repository: Repository, gitTree: GitTree): Observable<GitHubTree> {
        return CloudGitHubDataStore(user).createGitTree(repository, gitTree)
    }
}