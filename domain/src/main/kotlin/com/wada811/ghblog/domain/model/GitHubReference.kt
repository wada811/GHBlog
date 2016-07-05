package com.wada811.ghblog.domain.model

import com.wada811.notifypropertychanged.INotifyPropertyChanged
import com.wada811.notifypropertychanged.PropertyChangedDelegate

class GitHubReference(
        sha: String,
        url: String,
        htmlUrl: String
) : INotifyPropertyChanged {
    val sha: String by PropertyChangedDelegate(sha)
    val url: String by PropertyChangedDelegate(url)
    val htmlUrl: String by PropertyChangedDelegate(htmlUrl)
}