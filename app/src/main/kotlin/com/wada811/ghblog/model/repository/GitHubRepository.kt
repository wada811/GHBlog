package com.wada811.ghblog.model.repository

import com.wada811.ghblog.model.domain.Repository
import com.wada811.ghblog.model.domain.User
import rx.Observable

interface GitHubRepository {
    fun getRepositoryList(user: User): Observable<out List<Repository>>
}