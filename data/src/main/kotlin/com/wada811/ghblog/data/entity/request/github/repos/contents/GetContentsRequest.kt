package com.wada811.ghblog.data.entity.request.github.repos.contents

data class GetContentsRequest(
    val owner: String,
    val repo: String,
    val path: String
)