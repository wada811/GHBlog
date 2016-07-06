package com.wada811.ghblog.data.entity.response.github.repos.contents

import org.threeten.bp.ZonedDateTime

data class DeleteContentResponse(
        val content: DeleteContentContentResponse,
        val commit: DeleteContentCommitResponse
) {
    data class DeleteContentContentResponse(
            val name: String,
            val path: String,
            val sha: String,
            val size: Int,
            val url: String,
            val html_url: String,
            val git_url: String,
            val download_url: String,
            val type: String,
            val _links: DeleteContentContentLinkResponse
    ) {
        data class DeleteContentContentLinkResponse(
                val self: String,
                val git: String,
                val html: String
        )
    }

    data class DeleteContentCommitResponse(
            val sha: String,
            val url: String,
            val html_url: String,
            val author: DeleteContentCommitAuthorResponse,
            val committer: DeleteContentCommitAuthorResponse,
            val message: String,
            val tree: DeleteContentCommitTreeResponse,
            val parents: List<DeleteContentCommitGitReferenceResponse>
    ) {
        data class DeleteContentCommitAuthorResponse(
                val date: ZonedDateTime,
                val name: String,
                val email: String
        )

        data class DeleteContentCommitTreeResponse(
                val sha: String,
                val url: String
        )

        data class DeleteContentCommitGitReferenceResponse(
                val sha: String,
                val html_url: String,
                val url: String
        )
    }
}