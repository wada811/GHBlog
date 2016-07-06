package com.wada811.ghblog.data.entity.request.github.repos.contents

data class DeleteContentRequest(
        val owner: String,
        val repo: String,
        val path: String,
        val commit: DeleteContentCommitRequest
) {
    data class DeleteContentCommitRequest(
            val path: String,
            val message: String,
            val sha: String,
            val branch: String = "master"
    ) {
        fun getQueryMap() = mapOf(
                Pair("path", path),
                Pair("message", message),
                Pair("sha", sha),
                Pair("branch", branch)
        )
    }
}