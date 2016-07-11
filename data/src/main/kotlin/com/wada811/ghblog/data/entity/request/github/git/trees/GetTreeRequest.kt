package com.wada811.ghblog.data.entity.request.github.git.trees

data class GetTreeRequest(
    val owner: String,
    val repo: String,
    val sha: String
)