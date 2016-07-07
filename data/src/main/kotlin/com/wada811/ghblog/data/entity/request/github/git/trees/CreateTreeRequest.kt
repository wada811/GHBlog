package com.wada811.ghblog.data.entity.request.github.git.trees

data class CreateTreeRequest(
    val owner: String,
    val repo: String,
    val tree: List<CreateTreeTreeRequest>,
    val base_tree: String
) {
    data class CreateTreeTreeRequest(
        val path: String,
        val mode: String,
        val type: String,
        val sha: String,
        val content: String
    )
}