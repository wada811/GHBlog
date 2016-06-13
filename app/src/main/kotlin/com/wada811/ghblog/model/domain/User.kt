package com.wada811.ghblog.model.domain

import com.wada811.ghblog.data.repository.GitHubDataRepository
import rx.Observable

class User(var userName: String, var accessToken: String) {
    var repositoryList: Observable<List<Repository>> = GitHubDataRepository.getRepositoryList(this)
}