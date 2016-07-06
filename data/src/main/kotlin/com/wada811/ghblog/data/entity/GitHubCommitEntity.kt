package com.wada811.ghblog.data.entity

import org.threeten.bp.ZonedDateTime

data class GitHubCommitEntity(
        var content: RepositoryContentInfoEntity,
        var commit: CommitEntity
) {
    data class CommitEntity(
            var sha: String,
            var url: String,
            var html_url: String,
            var author: AuthorEntity,
            var committer: AuthorEntity,
            var message: String,
            var tree: GitTreeEntity,
            var parents: List<CommitEntity>
    ) {
        data class AuthorEntity(
                var date: ZonedDateTime,
                var name: String,
                var email: String
        )
    }
}