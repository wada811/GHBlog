package com.wada811.ghblog.data.entity.response.github.git.commits

import org.threeten.bp.ZonedDateTime

data class GetCommitResponse(
    val sha: String,
    val url: String,
    val author: GetCommitAuthorResponse,
    val committer: GetCommitAuthorResponse,
    val message: String,
    val tree: GetCommitTreeResponse,
    val parents: List<GetCommitGitReferenceResponse>

){
    data class GetCommitAuthorResponse(
        val date: ZonedDateTime,
        val name: String,
        val email: String
    )

    data class GetCommitTreeResponse(
        val sha: String,
        val url: String
    )

    data class GetCommitGitReferenceResponse(
        val sha: String,
        val html_url: String,
        val url: String
    )
}