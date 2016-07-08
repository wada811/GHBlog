package com.wada811.ghblog.data.entity.request.github.git.refs

data class GetReferenceRequest(
    val owner: String,
    val repo: String,
    val ref: String = "heads/master"
)