package com.wada811.ghblog.data.entity.response.github.repos.contents

import org.threeten.bp.ZonedDateTime

data class UpdateContentResponse(
        val content: UpdateContentContentResponse,
        val commit: UpdateContentCommitResponse
) {
    data class UpdateContentContentResponse(
            val name: String,
            val path: String,
            val sha: String,
            val size: Int,
            val url: String,
            val html_url: String,
            val git_url: String,
            val download_url: String,
            val type: String,
            val _links: UpdateContentContentLinkResponse
    ) {
        data class UpdateContentContentLinkResponse(
                val self: String,
                val git: String,
                val html: String
        )
    }

    data class UpdateContentCommitResponse(
            val sha: String,
            val url: String,
            val html_url: String,
            val author: UpdateContentCommitAuthorResponse,
            val committer: UpdateContentCommitAuthorResponse,
            val message: String,
            val tree: UpdateContentCommitTreeResponse,
            val parents: List<UpdateContentCommitGitReferenceResponse>
    ) {
        data class UpdateContentCommitAuthorResponse(
                val date: ZonedDateTime,
                val name: String,
                val email: String
        )

        data class UpdateContentCommitTreeResponse(
                val sha: String,
                val url: String
        )

        data class UpdateContentCommitGitReferenceResponse(
                val sha: String,
                val html_url: String,
                val url: String
        )
    }
}