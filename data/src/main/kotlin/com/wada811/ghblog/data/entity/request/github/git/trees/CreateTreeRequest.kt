package com.wada811.ghblog.data.entity.request.github.git.trees

data class CreateTreeRequest(
    val owner: String,
    val repo: String,
    val body: CreateTreeBodyRequest
) {
    data class CreateTreeBodyRequest(
        val tree: List<CreateTreeTreeRequest>,
        val base_tree: String? = null
    ) {
        data class CreateTreeTreeRequest(
            val path: String,
            val mode: String,
            val type: String,
            val sha: String? = null,
            val content: String? = null
        )
    }
}