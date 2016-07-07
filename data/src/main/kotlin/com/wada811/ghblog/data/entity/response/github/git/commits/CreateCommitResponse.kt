package com.wada811.ghblog.data.entity.response.github.git.commits

import org.threeten.bp.ZonedDateTime

data class CreateCommitResponse(
    val sha: String,
    val url: String,
    val author: CreateCommitAuthorResponse,
    val committer: CreateCommitAuthorResponse,
    val message: String,
    val tree: CreateCommitTreeResponse,
    val parents: List<CreateCommitGitReferenceResponse>
) {
    data class CreateCommitAuthorResponse(
        val date: ZonedDateTime,
        val name: String,
        val email: String
    )

    data class CreateCommitTreeResponse(
        val sha: String,
        val url: String
    )

    data class CreateCommitGitReferenceResponse(
        val sha: String,
        val html_url: String,
        val url: String
    )
}