package com.wada811.ghblog.model.repository

import com.wada811.ghblog.model.domain.*
import rx.Observable

interface GitHubRepository {
    fun getRepositoryList(user: User): Observable<List<Repository>>
    fun getContents(user: User, repository: Repository, path: String): Observable<List<RepositoryContentInfo>>
    fun getContent(user: User, repository: Repository, path: String): Observable<RepositoryContent>
    fun getTree(user: User, repository: Repository): Observable<GitTree>
}