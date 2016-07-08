package com.wada811.ghblog.data.entity.response.github.git.refs

data class UpdateReferenceResponse(
    val ref: String,
    val url: String,
    val `object`: UpdateReferenceObjectResponse
) {
    data class UpdateReferenceObjectResponse(
        val type: String,
        val sha: String,
        val url: String
    )
}