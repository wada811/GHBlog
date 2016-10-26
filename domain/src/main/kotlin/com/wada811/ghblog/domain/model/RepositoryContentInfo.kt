package com.wada811.ghblog.domain.model

import com.wada811.observablemodel.events.property.INotifyPropertyChanged
import com.wada811.observablemodel.events.property.PropertyChangedDelegate

class RepositoryContentInfo(
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
    contentLink: ContentLink
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

    class ContentLink(
        self: String,
        git: String,
        html: String
    ) : INotifyPropertyChanged {
        var self: String by PropertyChangedDelegate(self)
        var git: String by PropertyChangedDelegate(git)
        var html: String by PropertyChangedDelegate(html)
    }

    fun asRepositoryContent() = RepositoryContent(
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
        RepositoryContent.ContentLink(contentLink.self, contentLink.git, contentLink.html)
    )
}