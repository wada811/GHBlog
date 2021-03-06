package com.wada811.ghblog.domain.model

import com.wada811.ghblog.domain.GHBlogContext
import com.wada811.ghblog.domain.util.decodeBase64
import com.wada811.observablemodel.events.property.INotifyPropertyChanged
import com.wada811.observablemodel.events.property.PropertyChangedDelegate
import rx.Observable
import rx.schedulers.Schedulers

class RepositoryContent(
    val user: User,
    val repository: Repository,
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
    encoding: String? = null,
    encodedContent: String? = null
) : INotifyPropertyChanged {
    var name: String by PropertyChangedDelegate(name)
    var path: String  by PropertyChangedDelegate(path)
    var sha: String  by PropertyChangedDelegate(sha)
    var size: Int  by PropertyChangedDelegate(size)
    var url: String  by PropertyChangedDelegate(url)
    var htmlUrl: String  by PropertyChangedDelegate(htmlUrl)
    var gitUrl: String  by PropertyChangedDelegate(gitUrl)
    var downloadUrl: String  by PropertyChangedDelegate(downloadUrl)
    var type: String by PropertyChangedDelegate(type)
    var contentLink: ContentLink by PropertyChangedDelegate(contentLink)
    var isLoaded: Boolean by PropertyChangedDelegate(false)
    var encoding: String? by PropertyChangedDelegate(encoding)
    var encodedContent: String? by PropertyChangedDelegate(encodedContent)
    var content: String? by PropertyChangedDelegate(encodedContent?.decodeBase64())

    class ContentLink(
        self: String,
        git: String,
        html: String
    ) : INotifyPropertyChanged {
        var self: String by PropertyChangedDelegate(self)
        var git: String by PropertyChangedDelegate(git)
        var html: String by PropertyChangedDelegate(html)
    }

    fun loadContent(): Observable<RepositoryContent> {
        if (isLoaded) {
            return Observable.just(this)
        } else {
            return GHBlogContext.gitHubRepository.getContent(user, repository, path)
                .subscribeOn(Schedulers.io())
                .doOnNext {
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