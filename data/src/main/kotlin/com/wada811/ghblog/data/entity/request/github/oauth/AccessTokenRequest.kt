package com.wada811.ghblog.data.entity.request.github.oauth

data class AccessTokenRequest(
    val client_id: String,
    val client_secret: String,
    val code: String,
    val state: String
)