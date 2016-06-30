package com.wada811.ghblog.data.entity.request.github.repos.contents

data class UpdateContentRequest(
        val path: String,
        val message: String,
        val content: String,
        val sha: String,
        val branch: String = "master"
) {
}