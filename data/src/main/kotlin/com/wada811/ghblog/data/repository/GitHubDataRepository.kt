package com.wada811.ghblog.data.repository

import com.wada811.ghblog.data.datasource.github.CloudGitHubDataSource
import com.wada811.ghblog.domain.model.*
import com.wada811.ghblog.domain.repository.GitHubRepository
import rx.Observable

class GitHubDataRepository() : GitHubRepository {
    override fun getRepositoryList(user: User): Observable<List<Repository>> {
        return CloudGitHubDataSource(user).getAllRepository()
    }

    override fun getContents(user: User, repository: Repository, path: String): Observable<List<RepositoryContentInfo>> {
        return CloudGitHubDataSource(user).getContents(repository, path)
    }

    override fun getContent(user: User, repository: Repository, path: String): Observable<RepositoryContent> {
        return CloudGitHubDataSource(user).getContent(repository, path)
    }

    override fun createContent(user: User, repository: Repository, commit: GitCommit): Observable<GitHubCommit> {
        return CloudGitHubDataSource(user).createContent(repository, commit)
    }

    override fun updateContent(user: User, repository: Repository, commit: GitCommit): Observable<GitHubCommit> {
        return CloudGitHubDataSource(user).updateContent(repository, commit)
    }

    override fun deleteContent(user: User, repository: Repository, commit: GitCommit): Observable<GitHubCommit> {
        return CloudGitHubDataSource(user).deleteContent(repository, commit)
    }

    override fun renameContent(user: User, repository: Repository, commit: GitCommit): Observable<GitHubCommit> {
        return CloudGitHubDataSource(user).renameContent(repository, commit)
    }

    override fun getTree(user: User, repository: Repository): Observable<GitHubTree> {
        return CloudGitHubDataSource(user).getTree(repository)
    }

    override fun createTree(user: User, repository: Repository, gitTree: GitTree): Observable<GitHubTree> {
        return CloudGitHubDataSource(user).createGitTree(repository, gitTree)
    }
}