package com.wada811.ghblog.domain.model

import com.wada811.ghblog.domain.GHBlogContext
import com.wada811.ghblog.domain.util.Base64
import com.wada811.notifypropertychanged.INotifyPropertyChanged
import com.wada811.notifypropertychanged.PropertyChangedDelegate
import rx.Observable

class RepositoryContent(
    user: User,
    repository: Repository,
    name: String,
    path: String,
    sha: String,
    size: Int,
    url: String,
    htmlUrl: String,
    gitUrl: String,
    downloadUrl: String,
    type: String,
    contentLink: ContentLink,
    encoding: String = "",
    encodedContent: String = ""
) : RepositoryContentInfo(
    user,
    repository,
    name,
    path,
    sha,
    size,
    url,
    htmlUrl,
    gitUrl,
    downloadUrl,
    type,
    contentLink
), INotifyPropertyChanged {
    var encoding: String by PropertyChangedDelegate(encoding)
    var encodedContent: String by PropertyChangedDelegate(encodedContent)
    var content: String by PropertyChangedDelegate(String(Base64.decode(encodedContent, Base64.NO_WRAP)))

    constructor(repositoryContentInfo: RepositoryContentInfo) : this(
        repositoryContentInfo.user,
        repositoryContentInfo.repository,
        repositoryContentInfo.name,
        repositoryContentInfo.path,
        repositoryContentInfo.sha,
        repositoryContentInfo.size,
        repositoryContentInfo.url,
        repositoryContentInfo.htmlUrl,
        repositoryContentInfo.gitUrl,
        repositoryContentInfo.downloadUrl,
        repositoryContentInfo.type,
        repositoryContentInfo.contentLink
    )

    fun update(newPath: String, message: String, content: String): Observable<GitHubCommit> {
        val commit = GitCommit(newPath, message, content, sha, path)
        if (path == newPath) {
            return GHBlogContext.gitHubRepository.updateContent(user, repository, commit)
        } else {
            return GHBlogContext.gitHubRepository.renameContent(user, repository, commit)
        }
    }

    fun delete(message: String, content: String): Observable<GitHubCommit> {
        val commit = GitCommit(path, message, content, sha)
        return GHBlogContext.gitHubRepository.deleteContent(user, repository, commit)
    }
}