package com.wada811.ghblog.domain.model

import com.wada811.ghblog.domain.util.encodeBase64
import com.wada811.observablemodel.events.property.INotifyPropertyChanged
import com.wada811.observablemodel.events.property.PropertyChangedDelegate

class GitCommit(
    path: String,
    message: String,
    content: String,
    sha: String? = null,
    oldPath: String? = null
) : INotifyPropertyChanged {
    var path: String by PropertyChangedDelegate(path)
    var message: String by PropertyChangedDelegate(message)
    var content: String by PropertyChangedDelegate(content)
    var sha: String? by PropertyChangedDelegate(sha)
    var oldPath: String? by PropertyChangedDelegate(oldPath)
    fun encodedContent(): String = content.encodeBase64()
}