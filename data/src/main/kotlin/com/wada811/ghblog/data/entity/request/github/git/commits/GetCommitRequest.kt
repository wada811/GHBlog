package com.wada811.ghblog.data.entity.request.github.git.commits

data class GetCommitRequest(
    val owner: String,
    val repo: String,
    val sha: String
)