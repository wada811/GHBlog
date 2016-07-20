package com.wada811.ghblog.domain

import com.wada811.ghblog.domain.model.GitHubApp
import com.wada811.ghblog.domain.model.User
import com.wada811.ghblog.domain.repository.GitHubRepository
import com.wada811.ghblog.domain.repository.UserRepository

object GHBlogContext {
    lateinit var gitHubApp: GitHubApp
    lateinit var userRepository: UserRepository
    lateinit var gitHubRepository: GitHubRepository
    lateinit var currentUser: User
    fun init(gitHubApp: GitHubApp, userRepository: UserRepository, gitHubRepository: GitHubRepository) {
        this.gitHubApp = gitHubApp
        this.userRepository = userRepository
        this.gitHubRepository = gitHubRepository
    }
}