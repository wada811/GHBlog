package com.wada811.ghblog.domain.model

import com.wada811.ghblog.domain.GHBlogContext
import rx.Observable

class User(var userName: String, var accessToken: String) {
    var repositoryList: Observable<List<Repository>> = GHBlogContext.gitHubRepository.getRepositoryList(this)
}