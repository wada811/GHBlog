package com.wada811.ghblog.data.entity

import com.wada811.ghblog.data.entity.response.github.git.trees.GetTreeResponse
import com.wada811.ghblog.data.entity.response.github.repos.contents.GetContentsResponse
import org.threeten.bp.ZonedDateTime

data class GitHubCommitEntity(
    var content: GetContentsResponse,
    var commit: CommitEntity
) {
    data class CommitEntity(
        var sha: String,
        var url: String,
        var html_url: String,
        var author: AuthorEntity,
        var committer: AuthorEntity,
        var message: String,
        var tree: GetTreeResponse,
        var parents: List<CommitEntity>
    ) {
        data class AuthorEntity(
                var date: ZonedDateTime,
                var name: String,
                var email: String
        )
    }
}