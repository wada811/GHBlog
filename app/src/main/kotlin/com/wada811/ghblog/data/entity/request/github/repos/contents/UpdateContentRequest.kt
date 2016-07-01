package com.wada811.ghblog.data.entity.request.github.repos.contents

data class UpdateContentRequest(
        val owner: String,
        val repo: String,
        val path: String,
        val commit: UpdateContentCommitRequest
) {
    class UpdateContentCommitRequest(
            val path: String,
            val message: String,
            val content: String,
            val sha: String,
            val branch: String = "master"
    ) {

    }
}