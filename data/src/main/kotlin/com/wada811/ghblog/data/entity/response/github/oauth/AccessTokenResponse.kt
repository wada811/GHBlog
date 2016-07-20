package com.wada811.ghblog.data.entity.response.github.oauth

data class AccessTokenResponse(
    val access_token: String,
    val scope: String,
    val token_type: String
)