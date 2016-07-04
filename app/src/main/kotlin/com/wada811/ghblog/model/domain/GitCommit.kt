package com.wada811.ghblog.model.domain

import com.wada811.ghblog.util.Base64
import com.wada811.notifypropertychanged.INotifyPropertyChanged
import com.wada811.notifypropertychanged.PropertyChangedDelegate

class GitCommit(path: String, message: String, content: String, sha: String? = null) : INotifyPropertyChanged {
    var path: String by PropertyChangedDelegate(path)
    var message: String by PropertyChangedDelegate(message)
    var content: String by PropertyChangedDelegate(content)
    var sha: String? by PropertyChangedDelegate(sha)
    fun encodedContent(): String = Base64.encodeToString(content.toByteArray(), Base64.NO_WRAP)
}