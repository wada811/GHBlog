package com.wada811.ghblog.domain.model

import com.wada811.ghblog.domain.GHBlogContext
import com.wada811.ghblog.domain.util.Base64
import com.wada811.observablemodel.events.property.INotifyPropertyChanged
import com.wada811.observablemodel.events.property.PropertyChangedDelegate
import rx.schedulers.Schedulers

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

    fun loadContent() {
        GHBlogContext.gitHubRepository.getContent(user, repository, path)
            .subscribeOn(Schedulers.io())
            .subscribe {
                this.name = it.name
                this.path = it.path
                this.sha = it.sha
                this.size = it.size
                this.url = it.url
                this.htmlUrl = it.htmlUrl
                this.gitUrl = it.gitUrl
                this.downloadUrl = it.downloadUrl
                this.type = it.type
                this.contentLink.self = it.contentLink.self
                this.contentLink.git = it.contentLink.git
                this.contentLink.html = it.contentLink.html
                this.encoding = it.encoding
                this.encodedContent = it.encodedContent
                this.content = it.content
            }
    }

    fun update(newPath: String, message: String, content: String) {
        val commit = GitCommit(newPath, message, content, sha, path)
        val observable = if (path == newPath) {
            GHBlogContext.gitHubRepository.updateContent(user, repository, commit)
        } else {
            GHBlogContext.gitHubRepository.renameContent(user, repository, commit)
        }
        observable.subscribeOn(Schedulers.io())
            .subscribe {
                this.name = it.content!!.name
                this.path = it.content!!.path
                this.sha = it.content!!.sha
                this.size = it.content!!.size
                this.url = it.content!!.url
                this.htmlUrl = it.content!!.htmlUrl
                this.gitUrl = it.content!!.gitUrl
                this.downloadUrl = it.content!!.downloadUrl
                this.type = it.content!!.type
                this.contentLink.self = it.content!!.contentLink.self
                this.contentLink.git = it.content!!.contentLink.git
                this.contentLink.html = it.content!!.contentLink.html
                this.content = content
            }
    }

    fun delete(message: String, content: String) {
        val commit = GitCommit(path, message, content, sha)
        GHBlogContext.gitHubRepository.deleteContent(user, repository, commit)
            .subscribeOn(Schedulers.io())
            .subscribe {
                this.name = it.content!!.name
                this.path = it.content!!.path
                this.sha = it.content!!.sha
                this.size = it.content!!.size
                this.url = it.content!!.url
                this.htmlUrl = it.content!!.htmlUrl
                this.gitUrl = it.content!!.gitUrl
                this.downloadUrl = it.content!!.downloadUrl
                this.type = it.content!!.type
                this.contentLink.self = it.content!!.contentLink.self
                this.contentLink.git = it.content!!.contentLink.git
                this.contentLink.html = it.content!!.contentLink.html
                this.content = content
                repository.repositoryContents.remove(this)
            }
    }
}