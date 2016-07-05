package com.wada811.ghblog.domain

import com.wada811.ghblog.domain.repository.GitHubRepository
import com.wada811.ghblog.domain.repository.UserRepository

object GHBlogContext {
    lateinit var userRepository: UserRepository
    lateinit var gitHubRepository: GitHubRepository
}