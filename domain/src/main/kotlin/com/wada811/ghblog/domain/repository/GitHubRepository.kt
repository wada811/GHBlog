package com.wada811.ghblog.domain.repository

import com.wada811.ghblog.domain.model.*
import rx.Observable

interface GitHubRepository {
    fun getRepositoryList(user: User): Observable<List<Repository>>
    fun getContents(user: User, repository: Repository, path: String): Observable<List<RepositoryContentInfo>>
    fun getContent(user: User, repository: Repository, path: String): Observable<RepositoryContent>
    fun createContent(user: User, repository: Repository, commit: GitCommit): Observable<GitHubCommit>
    fun updateContent(user: User, repository: Repository, commit: GitCommit): Observable<GitHubCommit>
    fun deleteContent(user: User, repository: Repository, commit: GitCommit): Observable<GitHubCommit>
    fun getTree(user: User, repository: Repository): Observable<GitHubTree>
}