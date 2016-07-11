package com.wada811.ghblog.data.entity.request.github.repos.contents

data class GetContentRequest(
    val owner: String,
    val repo: String,
    val path: String
)