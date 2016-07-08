package com.wada811.ghblog.data.entity.response.github.git.refs

data class GetReferenceResponse(
    val ref: String,
    val url: String,
    val `object`: GetReferenceObjectResponse
) {
    data class GetReferenceObjectResponse(
        val type: String,
        val sha: String,
        val url: String
    )
}