package com.wada811.ghblog.data.entity.request.github.git.refs

data class UpdateReferenceRequest(
    val owner: String,
    val repo: String,
    val ref: String,
    val reference: UpdateReferenceReferenceRequest
) {
    data class UpdateReferenceReferenceRequest(
        val sha: String,
        val force: Boolean = false
    )
}