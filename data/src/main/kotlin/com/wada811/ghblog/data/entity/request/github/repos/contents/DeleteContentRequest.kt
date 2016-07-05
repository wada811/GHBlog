package com.wada811.ghblog.data.entity.request.github.repos.contents

data class DeleteContentRequest(
        val path: String,
        val message: String,
        val sha: String,
        val branch: String = "master"
) {
}