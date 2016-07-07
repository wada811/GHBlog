package com.wada811.ghblog.data.entity.response.github.git.trees

data class CreateTreeResponse(
    val sha: String,
    val url: String,
    val tree: CreateTreeTreeResponse
) {
    data class CreateTreeTreeResponse(
        val path: String,
        val mode: String,
        val type: String,
        val size: Int,
        val sha: String,
        val url: String
    )
}