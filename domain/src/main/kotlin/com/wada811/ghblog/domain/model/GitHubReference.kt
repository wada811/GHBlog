package com.wada811.ghblog.domain.model

import com.wada811.observablemodel.events.property.INotifyPropertyChanged
import com.wada811.observablemodel.events.property.PropertyChangedDelegate

class GitHubReference(
        sha: String,
        url: String,
        htmlUrl: String
) : INotifyPropertyChanged {
    val sha: String by PropertyChangedDelegate(sha)
    val url: String by PropertyChangedDelegate(url)
    val htmlUrl: String by PropertyChangedDelegate(htmlUrl)
}