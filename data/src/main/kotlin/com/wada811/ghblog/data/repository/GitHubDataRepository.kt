package com.wada811.ghblog.data.repository

import com.wada811.ghblog.data.datasource.github.GitHubDataSource
import com.wada811.ghblog.data.datasource.github.RemoteGitHubDataSource
import com.wada811.ghblog.domain.model.*
import com.wada811.ghblog.domain.repository.GitHubRepository
import rx.Observable

class GitHubDataRepository(private val localDataSource: GitHubDataSource, private val remoteDataSource: GitHubDataSource) : GitHubRepository {
    override fun getRepositories(user: User): Observable<Repository> {
        return Observable.merge(localDataSource.getRepositories(user), remoteDataSource.getRepositories(user)).distinct { it.id }
    }

    override fun getContents(user: User, repository: Repository, path: String): Observable<List<RepositoryContentInfo>> {
        return RemoteGitHubDataSource().getContents(user, repository, path)
    }

    override fun getContent(user: User, repository: Repository, path: String): Observable<RepositoryContent> {
        return RemoteGitHubDataSource().getContent(user, repository, path)
    }

    override fun createContent(user: User, repository: Repository, commit: GitCommit): Observable<GitHubCommit> {
        return RemoteGitHubDataSource().createContent(user, repository, commit)
    }

    override fun updateContent(user: User, repository: Repository, commit: GitCommit): Observable<GitHubCommit> {
        return RemoteGitHubDataSource().updateContent(user, repository, commit)
    }

    override fun deleteContent(user: User, repository: Repository, commit: GitCommit): Observable<GitHubCommit> {
        return RemoteGitHubDataSource().deleteContent(user, repository, commit)
    }

    override fun renameContent(user: User, repository: Repository, commit: GitCommit): Observable<GitHubCommit> {
        return RemoteGitHubDataSource().renameContent(user, repository, commit)
    }

    override fun getTree(user: User, repository: Repository): Observable<GitHubTree> {
        return RemoteGitHubDataSource().getTree(user, repository)
    }

    override fun createTree(user: User, repository: Repository, gitTree: GitTree): Observable<GitHubTree> {
        return RemoteGitHubDataSource().createGitTree(user, repository, gitTree)
    }
}