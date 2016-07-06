package com.wada811.ghblog.data.entity.response.github.repos.contents

import org.threeten.bp.ZonedDateTime

data class CreateContentResponse(
        val content: CreateContentContentResponse,
        val commit: CreateContentCommitResponse
) {
    data class CreateContentContentResponse(
            val name: String,
            val path: String,
            val sha: String,
            val size: Int,
            val url: String,
            val html_url: String,
            val git_url: String,
            val download_url: String,
            val type: String,
            val _links: CreateContentContentLinkResponse
    ) {
        data class CreateContentContentLinkResponse(
                val self: String,
                val git: String,
                val html: String
        )
    }

    data class CreateContentCommitResponse(
            val sha: String,
            val url: String,
            val html_url: String,
            val author: CreateContentCommitAuthorResponse,
            val committer: CreateContentCommitAuthorResponse,
            val message: String,
            val tree: CreateContentCommitTreeResponse,
            val parents: List<CreateContentCommitGitReferenceResponse>
    ) {
        data class CreateContentCommitAuthorResponse(
                val date: ZonedDateTime,
                val name: String,
                val email: String
        )

        data class CreateContentCommitTreeResponse(
                val sha: String,
                val url: String
        )

        data class CreateContentCommitGitReferenceResponse(
                val sha: String,
                val html_url: String,
                val url: String
        )
    }
}