package com.wada811.ghblog.model.repository

import com.wada811.ghblog.model.domain.Content
import com.wada811.ghblog.model.domain.GitTree
import com.wada811.ghblog.model.domain.Repository
import com.wada811.ghblog.model.domain.User
import rx.Observable

interface GitHubRepository {
    fun getRepositoryList(user: User): Observable<List<Repository>>
    fun getContents(user: User, repository: Repository, path: String): Observable<List<Content>>
    fun getTree(user: User, repository: Repository): Observable<GitTree>
}