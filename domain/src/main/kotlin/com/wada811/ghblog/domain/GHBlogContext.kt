package com.wada811.ghblog.domain

import com.wada811.ghblog.domain.model.User
import com.wada811.ghblog.domain.repository.GitHubRepository
import com.wada811.ghblog.domain.repository.UserRepository
import rx.Scheduler

object GHBlogContext {
    lateinit var uiThreadScheduler: Scheduler
    lateinit var userRepository: UserRepository
    lateinit var gitHubRepository: GitHubRepository
    lateinit var currentUser: User
    fun init(uiThreadScheduler: Scheduler, userRepository: UserRepository, gitHubRepository: GitHubRepository){
        this.uiThreadScheduler = uiThreadScheduler
        this.userRepository = userRepository
        this.gitHubRepository = gitHubRepository
        // FIXME: 認証まわりちゃんと作ったら消す
        currentUser = userRepository.user().toBlocking().single()
    }
}