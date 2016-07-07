package com.wada811.ghblog.data.entity.request.github.git.commits

data class CreateCommitRequest(
    val owner: String,
    val repo: String,
    val commit: CreateCommitCommitRequest
) {
    data class CreateCommitCommitRequest(
        val message: String,
        val tree: String,
        val parents: List<String>
    )
}