package com.wada811.ghblog.data.entity.request.github.repos.contents

data class CreateContentRequest(
        val owner: String,
        val repo: String,
        val path: String,
        val commit: CreateContentCommitRequest
) {
    data class CreateContentCommitRequest(
            val path: String,
            val message: String,
            val content: String,
            var branch: String = "master"
    )
}