package com.wada811.ghblog.domain.model

import com.wada811.notifypropertychanged.INotifyPropertyChanged
import com.wada811.notifypropertychanged.PropertyChangedDelegate

class GitHubCommit(
        content: RepositoryContentInfo?,
        commit: Commit
) : INotifyPropertyChanged {
    var content: RepositoryContentInfo? by PropertyChangedDelegate(content)
    var commit: Commit by PropertyChangedDelegate(commit)

    class Commit(
            sha: String,
            url: String,
            htmlUrl: String,
            author: Author,
            committer: Author,
            message: String,
            tree: GitHubTree,
            parents: List<GitHubReference>
    ) : INotifyPropertyChanged {
        var sha: String by PropertyChangedDelegate(sha)
        var url: String by PropertyChangedDelegate(url)
        var htmlUrl: String by PropertyChangedDelegate(htmlUrl)
        var author: Author by PropertyChangedDelegate(author)
        var committer: Author by PropertyChangedDelegate(committer)
        var message: String by PropertyChangedDelegate(message)
        var tree: GitHubTree by PropertyChangedDelegate(tree)
        var parents: List<GitHubReference> by PropertyChangedDelegate(parents)

        class Author(
                date: String,
                name: String,
                email: String
        ) : INotifyPropertyChanged {
            var date: String by PropertyChangedDelegate(date)
            var name: String by PropertyChangedDelegate(name)
            var email: String by PropertyChangedDelegate(email)
        }
    }
}