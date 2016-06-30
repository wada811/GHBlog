package com.wada811.ghblog.model.domain

class GitHubCommit(
        var content: RepositoryContentInfo,
        var commit: Commit
) {
    class Commit(
            var sha: String,
            var url: String,
            var htmlUrl: String,
            var author: Author,
            var committer: Author,
            var message: String,
            var tree: GitHubTree,
            var parents: List<GitHubReference>
    ) {
        class Author(
                var date: String,
                var name: String,
                var email: String
        )
    }
}