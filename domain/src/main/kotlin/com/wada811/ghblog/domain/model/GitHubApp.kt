package com.wada811.ghblog.domain.model

import com.wada811.ghblog.domain.util.Base64

class GitHubApp(val clientId: String, val clientSecret: String) {
    val scopes = listOf("user", "repo").reduce { scopes, scope -> "$scopes $scope" }
    val state: String = Base64.decode("$this", Base64.DEFAULT).toString()
}